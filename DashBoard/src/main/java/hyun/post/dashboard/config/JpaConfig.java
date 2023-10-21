package hyun.post.dashboard.config;

import jakarta.persistence.EntityManagerFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

@EnableJpaAuditing
@EnableJpaRepositories(basePackages = "hyun.post.dashboard.repository")
@RequiredArgsConstructor
public class JpaConfig {

    private final EntityManagerFactory emf;

    @Bean
    public PlatformTransactionManager transactionManager() {
        return new JpaTransactionManager(emf);
    }
}
