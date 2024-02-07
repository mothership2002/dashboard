package hyun.post.dashboard.repository.redis;

import hyun.post.dashboard.security.jwt.LoginTransaction;
import org.springframework.data.repository.CrudRepository;

public interface LoginTransactionRepository extends CrudRepository<LoginTransaction, String> {
}
