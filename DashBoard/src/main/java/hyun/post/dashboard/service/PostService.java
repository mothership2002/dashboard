package hyun.post.dashboard.service;

import hyun.post.dashboard.dao.PostDao;
import hyun.post.dashboard.exception.entity.NotFoundPostException;
import hyun.post.dashboard.model.dto.PostDto;
import hyun.post.dashboard.model.entity.Member;
import hyun.post.dashboard.model.entity.Post;
import hyun.post.dashboard.model.entity.PostCategory;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostDao postDao;

    // TODO pagination 객체로 받아야함.
    public List<Post> findByParam() {
        return postDao.findAllByPagination();
    }

    public Long create(PostDto postDto) {
        Member member = (Member) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Post post = new Post(postDto.getTitle(), postDto.getContent(), getCategory(postDto.getCategoryName()));
        return postDao.save(post);
    }

    public PostDto findByIdAndCategoryName(Long postId, String categoryName) {
        PostCategory category = getCategory(categoryName);
        Post post = postDao.findByIdAndCategoryId(postId, category.getId()).orElseThrow(NotFoundPostException::new);
        return new PostDto(post, category.getName());
    }

    private PostCategory getCategory(String categoryName) {
        // TODO 예외 만들어야함.
        return postDao.findCategoryByCategoryName(categoryName).orElseThrow(RuntimeException::new);
    }
}
