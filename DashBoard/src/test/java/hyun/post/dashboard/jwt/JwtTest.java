package hyun.post.dashboard.jwt;

import hyun.post.dashboard.repository.redis.AccessTokenRepository;
import hyun.post.dashboard.repository.redis.SyncLoginRepository;
import lombok.extern.slf4j.Slf4j;
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


}