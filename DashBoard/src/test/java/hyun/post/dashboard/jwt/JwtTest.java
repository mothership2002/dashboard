package hyun.post.dashboard.jwt;

import hyun.post.dashboard.security.jwt.AccountTokenSet;
import hyun.post.dashboard.security.jwt.repository.TokenRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.IntStream;

@SpringBootTest
@Slf4j
@Transactional
public class JwtTest {

    @Autowired
    TokenRepository tokenRepository;

    @Test
    void test() {
        AccountTokenSet token = tokenRepository.save(new AccountTokenSet("hello", "world", "test"));
        log.info("{}, {}, {}", token.getAccount(), token.getAccessToken(), token.getRefreshToken());
        AccountTokenSet hello = tokenRepository.findById("hello").orElseThrow(RuntimeException::new);
        log.info("{}, {}, {}", hello.getAccount(), hello.getAccessToken(), hello.getRefreshToken());
        tokenRepository.save(new AccountTokenSet("hello", "update", "update"));
        hello = tokenRepository.findById("hello").orElseThrow(RuntimeException::new);
        log.info("{}, {}, {}", hello.getAccount(), hello.getAccessToken(), hello.getRefreshToken());


        log.info("{}", token.equals(hello));
    }

    @Test
    void test2() {
        IntStream.range(0, 20).forEach(e ->
            tokenRepository.save(new AccountTokenSet("hello" + e,
                    "world" + e,
                    "test" + e))
        );
    }

}
