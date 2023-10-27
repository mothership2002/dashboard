package hyun.post.dashboard.repository.redis;

import hyun.post.dashboard.security.jwt.AccessToken;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface AccessTokenRepository extends CrudRepository<AccessToken, String> {
}
