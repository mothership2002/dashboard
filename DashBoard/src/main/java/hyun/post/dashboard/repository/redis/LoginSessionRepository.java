package hyun.post.dashboard.repository.redis;

import hyun.post.dashboard.security.member.LoginSession;
import org.springframework.data.repository.CrudRepository;

public interface LoginSessionRepository extends CrudRepository<LoginSession, String> {
}
