package hyun.post.dashboard.security.jwt;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@RedisHash(value = "LoginToken", timeToLive = 1L)
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class LoginToken {
    @Id
    private String account;
    public LoginToken(String account) {
        this.account = account;
    }
}
