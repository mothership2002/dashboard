package hyun.post.dashboard.repository.redis;

import hyun.post.dashboard.security.jwt.LoginToken;
import org.springframework.data.repository.CrudRepository;

public interface SyncLoginRepository extends CrudRepository<LoginToken, String> {
}
