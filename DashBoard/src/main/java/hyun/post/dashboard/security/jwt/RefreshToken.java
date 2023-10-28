package hyun.post.dashboard.security.jwt;

import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@RedisHash("RefreshToken")
@Getter
public class RefreshToken extends BaseTokenInfo {

    @Id
    private String refreshToken;

    public RefreshToken(String refreshToken, String account, Long ttl) {
        super(account, ttl);
        this.refreshToken = refreshToken;
    }
}
