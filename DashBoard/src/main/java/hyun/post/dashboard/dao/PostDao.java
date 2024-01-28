package hyun.post.dashboard.dao;

import hyun.post.dashboard.model.entity.Post;
import hyun.post.dashboard.repository.rdbms.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class PostDao {

    private final PostRepository postRepository;

    /**
     * 수정 및 저장 용
     * @param post
     * @return 저장된 post pk
     */
    public Long save(Post post) {
        return postRepository.save(post).getId();
    }

    public List<Post> findAllByPagination() {
        postRepository.findAll();
        return null;
    }
    

}
