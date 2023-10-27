package hyun.post.dashboard.property;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties("jwt")
@Getter
@Setter
public class JwtProperty extends BaseProperty {

    private String secretKey;
    private String accessTokenHeader;
    private String refreshTokenHeader;
    private int accessTokenExpired;
    private int refreshTokenExpired;

//    @PreDestroy
//    public void destroy() {
//        log.info("{} is destroyed", this.getClass());
//    }

}
