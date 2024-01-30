package hyun.post.dashboard.repository.rdbms;

import hyun.post.dashboard.model.entity.Reply;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReplyRepository extends JpaRepository<Reply, Long>, ReplyRepositoryCustom {

    Long deleteByPostId(Long postId);
    List<Reply> findByPostId(Long postId);
}
