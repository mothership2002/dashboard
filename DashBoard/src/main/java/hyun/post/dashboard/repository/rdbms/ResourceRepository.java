package hyun.post.dashboard.repository.rdbms;

import hyun.post.dashboard.model.entity.Resource;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ResourceRepository extends JpaRepository<Resource, Long> {
}
