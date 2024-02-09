package hyun.post.dashboard.repository.rdbms;

import hyun.post.dashboard.model.entity.PostCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PostCategoryRepository extends JpaRepository<PostCategory, Long> {

    Optional<PostCategory> findOneByName(String categoryName);
}
