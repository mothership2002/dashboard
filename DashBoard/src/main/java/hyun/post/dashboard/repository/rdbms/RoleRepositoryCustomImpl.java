package hyun.post.dashboard.repository.rdbms;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class RoleRepositoryCustomImpl implements RoleRepositoryCustom {

    private final JPAQueryFactory qf;
}
