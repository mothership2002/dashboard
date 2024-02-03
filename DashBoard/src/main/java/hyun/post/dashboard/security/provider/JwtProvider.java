package hyun.post.dashboard.security.provider;

import hyun.post.dashboard.dao.MemberDao;
import hyun.post.dashboard.model.dto.JsonWebToken;
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
        this.accessTokenTimeToLive = jwtProperty.getAccessTokenExpired() * 60L * 2;
        this.refreshTokenTimeToLive = jwtProperty.getRefreshTokenExpired() * 24 * 60L * 2;
        this.accessTokenHeader = jwtProperty.getAccessTokenHeader();
        this.refreshTokenHeader = jwtProperty.getRefreshTokenHeader();
        this.secretKey = Keys.hmacShaKeyFor(jwtProperty.getSecretKey().getBytes(StandardCharsets.UTF_8));
        this.parser = Jwts.parserBuilder().setSigningKey(secretKey).build();
    }

    public JsonWebToken saveToken(Member member) {
        String accessToken = createToken(member, accessTokenTimeToLive / 2L);
        String refreshToken = createToken(member, refreshTokenTimeToLive / 2L);
        memberDao.saveSession(member.getAccount());
        return memberDao.saveToken(member, accessToken, refreshToken , accessTokenTimeToLive, refreshTokenTimeToLive);
    }

    private String createToken(Member member, Long milliSecond) {
        return Jwts.builder()
                .claim("memberId", member.getId())
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

}
