package hyun.post.dashboard.repository.rdbms;

import hyun.post.dashboard.model.entity.Reply;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReplyRepository extends JpaRepository<Reply, Long> {
}
