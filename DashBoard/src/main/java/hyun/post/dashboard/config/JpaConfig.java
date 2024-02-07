package hyun.post.dashboard.config;

import com.querydsl.jpa.impl.JPAQueryFactory;
import hyun.post.dashboard.model.entity.Member;
import hyun.post.dashboard.repository.rdbms.MemberRepository;
import jakarta.persistence.EntityManagerFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.PlatformTransactionManager;

import java.util.Optional;

@Configuration
@EnableJpaRepositories(basePackageClasses = MemberRepository.class)
@EnableJpaAuditing
@RequiredArgsConstructor
public class JpaConfig {

    private final EntityManagerFactory emf;

    @Bean
    public PlatformTransactionManager transactionManager() {
        return new JpaTransactionManager(emf);
    }

    @Bean
    public JPAQueryFactory jpaQueryFactory() {
        return new JPAQueryFactory(emf.createEntityManager());
    }

    @Bean
    public AuditorAware<Long> auditorAware() {
        return () -> {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication instanceof UsernamePasswordAuthenticationToken) {
                return Optional.ofNullable(((Member) authentication.getPrincipal()).getId());
            }
            return Optional.empty();
        };
    }

}
