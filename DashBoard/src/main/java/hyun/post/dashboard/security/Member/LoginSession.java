package hyun.post.dashboard.security.member;

import org.springframework.data.annotation.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.redis.core.RedisHash;

@RedisHash("LoginSession")
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class LoginSession {

    @Id
    private String account;

    private String accessToken;
    private String refreshToken;

    private String sessionCode;

}
