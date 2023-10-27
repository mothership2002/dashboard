package hyun.post.dashboard.property;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties("jwt")
@Getter
@Setter
public class JwtProperty {

    private String secretKey;
    private String accessTokenHeader;
    private String refreshTokenHeader;
    private int accessTokenExpired;
    private int refreshTokenExpired;

}
