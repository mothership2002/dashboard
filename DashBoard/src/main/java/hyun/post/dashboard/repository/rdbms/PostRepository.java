package hyun.post.dashboard.repository.rdbms;

import hyun.post.dashboard.model.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long>, PostRepositoryCustom {

    Optional<Post> findByIdAndPostCategoryId(Long id, Long postCategoryId);
}
