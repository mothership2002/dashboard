package hyun.post.dashboard.service;

import hyun.post.dashboard.dao.PostDao;
import hyun.post.dashboard.model.dto.PostDto;
import hyun.post.dashboard.model.entity.Member;
import hyun.post.dashboard.model.entity.Post;
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
        Post post = new Post(postDto.getTitle(), postDto.getContent());
        return postDao.save(post);
    }
}
