package hyun.post.dashboard.security.jwt.repository;

import hyun.post.dashboard.security.jwt.AccountTokenSet;
import org.springframework.data.repository.CrudRepository;

public interface TokenRepository extends CrudRepository<AccountTokenSet, String> {
}
