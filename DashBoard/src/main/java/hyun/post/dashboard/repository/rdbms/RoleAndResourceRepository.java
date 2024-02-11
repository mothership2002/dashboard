package hyun.post.dashboard.repository.rdbms;

import hyun.post.dashboard.model.relation.RoleAndResource;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleAndResourceRepository extends JpaRepository<RoleAndResource, Long>, RoleAndResourceRepositoryCustom {
}
