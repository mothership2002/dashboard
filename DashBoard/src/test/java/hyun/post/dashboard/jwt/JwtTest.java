package hyun.post.dashboard.jwt;

import hyun.post.dashboard.repository.redis.AccessTokenRepository;
import hyun.post.dashboard.repository.redis.SyncLoginRepository;
import hyun.post.dashboard.security.jwt.LoginToken;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Slf4j
@Transactional
public class JwtTest {

    @Autowired
    AccessTokenRepository tokenRepository;

    @Autowired
    SyncLoginRepository loginTokenRepository;


    @Test
    void test() throws InterruptedException {
        loginTokenRepository.save(new LoginToken("hello"));
        Thread.sleep(1000l);
        Assertions.assertThat(loginTokenRepository.findById("hello").isPresent()).isFalse();
    }
}
