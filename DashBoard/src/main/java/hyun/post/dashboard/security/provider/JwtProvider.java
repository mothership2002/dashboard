package hyun.post.dashboard.security.provider;

import hyun.post.dashboard.dao.MemberDao;
import hyun.post.dashboard.exception.CustomAssert;
import hyun.post.dashboard.exception.auth.ExpiredAccessTokenException;
import hyun.post.dashboard.model.dto.JwtDto;
import hyun.post.dashboard.security.jwt.JsonWebToken;
import hyun.post.dashboard.model.entity.Member;
import hyun.post.dashboard.property.JwtProperty;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
public class JwtProvider {

    private final MemberDao memberDao;
    private final JwtParser parser;
    private final SecretKey secretKey;
    private final Long accessTokenTimeToLive;
    private final Long refreshTokenTimeToLive;
    private final String accessTokenHeader;
    private final String refreshTokenHeader;

    public JwtProvider(MemberDao memberDao, JwtProperty jwtProperty) {
        this.memberDao = memberDao;
        this.accessTokenTimeToLive = jwtProperty.getAccessTokenExpired() * 60L * 1000;
        this.refreshTokenTimeToLive = jwtProperty.getRefreshTokenExpired() * 24 * 60L * 1000;
        this.accessTokenHeader = jwtProperty.getAccessTokenHeader();
        this.refreshTokenHeader = jwtProperty.getRefreshTokenHeader();
        this.secretKey = Keys.hmacShaKeyFor(jwtProperty.getSecretKey().getBytes(StandardCharsets.UTF_8));
        this.parser = Jwts.parserBuilder().setSigningKey(secretKey).build();
    }

    public JsonWebToken saveToken(Member member) {
        String accessToken = createToken(member.getAccount(), member.getId(), accessTokenTimeToLive);
        String refreshToken = createToken(member.getAccount(), member.getId(), refreshTokenTimeToLive);
        memberDao.saveSession(member.getAccount(), accessToken, refreshToken, refreshTokenTimeToLive);
        return memberDao.saveToken(member, accessToken, refreshToken , accessTokenTimeToLive, refreshTokenTimeToLive);
    }

    private String createToken(String account, Long memberId, Long milliSecond) {
        return Jwts.builder()
                .claim("memberId", memberId)
                .claim("account", account)
//                .claim("role", member.getRole())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + milliSecond))
                .signWith(secretKey)
                .compact();
    }

    public String getAccessTokenHeaderName() {
        return accessTokenHeader;
    }

    public String getRefreshTokenHeaderName() {
        return refreshTokenHeader;
    }

    public String extractToken(String responseHeader) {
        return responseHeader.split(" ")[1];
    }

    public Claims extractBody(String token) throws JwtException {
        return parser.parseClaimsJws(token).getBody();
    }

    public Jwt extractJwt(String token) {
        return parser.parse(token);
    }

    public Boolean accessTokenValidate(String token) {
        return memberDao.findOneByAccessToken(token);
    }

    public Member findMemberByToken(String accessToken) {
        CustomAssert.isTrue(accessTokenValidate(accessToken),
                "Expired Access Token",
                        ExpiredAccessTokenException.class);

        Claims claims = extractBody(accessToken);
        Long memberId = claims.get("memberId", Long.class);
        String account = claims.get("account", String.class);
        return memberDao.findByMemberIdAndAccount(memberId, account);
    }

    public JwtDto renewAccessToken(String account, Long memberId) {
        String renewedToken = createToken(account, memberId, accessTokenTimeToLive);
        memberDao.renewAccessToken(account, accessTokenTimeToLive, renewedToken);
        return new JwtDto(renewedToken, null);
    }

}
