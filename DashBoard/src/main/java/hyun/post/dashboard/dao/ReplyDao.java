package hyun.post.dashboard.dao;

import hyun.post.dashboard.model.entity.Reply;
import hyun.post.dashboard.repository.rdbms.ReplyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ReplyDao {

    private final ReplyRepository replyRepository;

    @Transactional(readOnly = true)
    public List<Reply> findByPostId(Long postId) {
        return replyRepository.findByPostId(postId);
    }

    @Transactional
    public Long save(Reply reply) {
        return replyRepository.save(reply).getId();
    }

}
