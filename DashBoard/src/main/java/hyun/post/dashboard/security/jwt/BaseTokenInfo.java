package hyun.post.dashboard.security.jwt;

import jakarta.persistence.Id;
import lombok.Getter;
import org.springframework.data.redis.core.TimeToLive;

@Getter
public abstract class BaseTokenInfo {

    private String account;
    @TimeToLive
    private Long ttl;

    public BaseTokenInfo(String account, Long ttl) {
        this.account = account;
        this.ttl = ttl;
    }
}
