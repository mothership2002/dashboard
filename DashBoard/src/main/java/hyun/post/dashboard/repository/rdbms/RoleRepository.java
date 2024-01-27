package hyun.post.dashboard.repository.rdbms;

import hyun.post.dashboard.model.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long>, RoleRepositoryCustom {

    Optional<Role> findOneByRoleName(String roleName);
}
