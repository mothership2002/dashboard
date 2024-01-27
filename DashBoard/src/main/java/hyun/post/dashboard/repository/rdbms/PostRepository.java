package hyun.post.dashboard.repository.rdbms;

import hyun.post.dashboard.model.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long>, PostRepositoryCustom {
}
