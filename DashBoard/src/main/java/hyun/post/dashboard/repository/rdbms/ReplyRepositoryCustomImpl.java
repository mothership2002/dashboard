package hyun.post.dashboard.repository.rdbms;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ReplyRepositoryCustomImpl implements ReplyRepositoryCustom {

    private final JPAQueryFactory qf;
}
