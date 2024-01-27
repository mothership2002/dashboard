package hyun.post.dashboard.repository.rdbms;

import com.querydsl.jpa.impl.JPAQueryFactory;
import hyun.post.dashboard.model.entity.QPost;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class PostRepositoryCustomImpl implements PostRepositoryCustom {

    private final JPAQueryFactory qf;
    private final QPost post = QPost.post;
}
