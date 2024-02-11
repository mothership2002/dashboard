package hyun.post.dashboard.repository.rdbms;

import hyun.post.dashboard.model.relation.RoleAndResource;

import java.util.List;

public interface RoleAndResourceRepositoryCustom {

    public List<RoleAndResource> findAllWithJoin();
}
