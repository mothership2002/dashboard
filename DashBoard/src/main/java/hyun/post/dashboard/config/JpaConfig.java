package hyun.post.dashboard.config;

import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaAuditing
@EnableJpaRepositories(basePackages = "hyun.post.dashboard.repository")
public class JpaConfig {
}
