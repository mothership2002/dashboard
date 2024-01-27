package hyun.post.dashboard.dao;

import hyun.post.dashboard.repository.rdbms.ReplyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ReplyDao {

    private final ReplyRepository replyRepository;
}
