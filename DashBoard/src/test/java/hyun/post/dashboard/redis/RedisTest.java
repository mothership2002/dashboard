package hyun.post.dashboard.redis;

import hyun.post.dashboard.repository.redis.AccessTokenRepository;
import hyun.post.dashboard.security.jwt.AccessToken;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
public class RedisTest {

    @Autowired
    AccessTokenRepository accessTokenRepository;

    @BeforeEach
    void init() {
        accessTokenRepository.save(new AccessToken("1","hello", 20L));
    }

    @Test
    void test() {
        assertThat(accessTokenRepository.findById("1").isPresent()).isTrue();

    }
}
