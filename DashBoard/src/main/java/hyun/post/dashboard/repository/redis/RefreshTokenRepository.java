package hyun.post.dashboard.repository.redis;

import hyun.post.dashboard.security.jwt.RefreshToken;
import org.springframework.data.repository.CrudRepository;

public interface RefreshTokenRepository extends CrudRepository<RefreshToken, String> {

}
