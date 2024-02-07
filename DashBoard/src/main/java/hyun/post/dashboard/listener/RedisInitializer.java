package hyun.post.dashboard.listener;

import hyun.post.dashboard.repository.redis.AccessTokenRepository;
import hyun.post.dashboard.repository.redis.LoginSessionRepository;
import hyun.post.dashboard.repository.redis.LoginTransactionRepository;
import hyun.post.dashboard.repository.redis.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class RedisInitializer {

    private final AccessTokenRepository accessTokenRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final LoginSessionRepository loginSessionRepository;
    private final LoginTransactionRepository loginTransactionRepository;

    @EventListener(ApplicationReadyEvent.class)
    public void init() {
        accessTokenRepository.deleteAll();
        refreshTokenRepository.deleteAll();
        loginTransactionRepository.deleteAll();
        loginSessionRepository.deleteAll();
        log.info("Redis Repository Initialized Success");
    }
}
