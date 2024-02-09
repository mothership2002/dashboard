package hyun.post.dashboard.dao;

import hyun.post.dashboard.model.entity.Post;
import hyun.post.dashboard.model.entity.PostCategory;
import hyun.post.dashboard.repository.rdbms.PostCategoryRepository;
import hyun.post.dashboard.repository.rdbms.PostRepository;
import hyun.post.dashboard.repository.rdbms.ReplyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class PostDao {

    private final PostRepository postRepository;
    private final ReplyRepository replyRepository;
    private final PostCategoryRepository postCategoryRepository;

    /**
     * 수정 및 저장 용
     * @param post
     * @return 저장된 post pk
     */
    @Transactional
    public Long save(Post post) {
        return postRepository.save(post).getId();
    }

    // TODO 쿼리파람을 통한 조건 조회 및 페이징 필요
    @Transactional(readOnly = true)
    public List<Post> findAllByPagination() {
        postRepository.findAll();
        return null;
    }

    @Transactional
    public void deleteById(Long postId) {
        Long deletedReplyCount = replyRepository.deleteByPostId(postId);
        postRepository.deleteById(postId);
    }

    public List<PostCategory> findAllCategory() {
        return postCategoryRepository.findAll();
    }

    public PostCategory findCategoryByCategoryName(String categoryName) {
        // TODO 예외 만들어야함.
        return postCategoryRepository.findOneByName(categoryName).orElseThrow(RuntimeException::new);
    }
}
