package hyun.post.dashboard.repository.rdbms;

import com.querydsl.jpa.impl.JPAQueryFactory;
import hyun.post.dashboard.model.entity.QResource;
import hyun.post.dashboard.model.entity.QRole;
import hyun.post.dashboard.model.relation.QRoleAndResource;
import hyun.post.dashboard.model.relation.RoleAndResource;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class RoleAndResourceRepositoryCustomImpl implements RoleAndResourceRepositoryCustom {

    private final JPAQueryFactory qf;
    private final QRole role = QRole.role;
    private final QRoleAndResource roleAndResource = QRoleAndResource.roleAndResource;
    private final QResource resource = QResource.resource;

    public List<RoleAndResource> findAllWithJoin() {
        return qf.select(roleAndResource)
                .from(roleAndResource)
                .join(roleAndResource.role, role).fetchJoin()
                .join(roleAndResource.resource, resource).fetchJoin()
                .fetch();
    }

}
