package hyun.post.dashboard.security.provider;

import hyun.post.dashboard.dao.MemberDao;
import hyun.post.dashboard.property.JwtProperty;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;

@Component
public class JwtProvider {

    private final MemberDao memberDao;
    private final JwtParser parser;
    private final SecretKey secretKey;
    private final Long accessTokenTimeToLive;
    private final Long refreshTokenTimeToLive;

    public JwtProvider(MemberDao memberDao, JwtProperty jwtProperty) {
        this.memberDao = memberDao;
        this.accessTokenTimeToLive = jwtProperty.getAccessTokenExpired() * 60L; // min
        this.refreshTokenTimeToLive = jwtProperty.getRefreshTokenExpired() * 24 * 60L;
        this.secretKey = Keys.hmacShaKeyFor(jwtProperty.getSecretKey().getBytes(StandardCharsets.UTF_8));
        this.parser = Jwts.parserBuilder().setSigningKey(secretKey).build();
    }
}
