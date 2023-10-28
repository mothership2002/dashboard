package hyun.post.dashboard.security.jwt;

import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@RedisHash("AccessToken")
@Getter
public class AccessToken extends BaseTokenInfo {

    @Id
    private String accessToken;

    public AccessToken(String accessToken, String account, Long ttl) {
        super(account, ttl);
        this.accessToken = accessToken;
    }
}
