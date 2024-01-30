package hyun.post.dashboard.service;

import hyun.post.dashboard.component.XssConverter;
import hyun.post.dashboard.dao.PostDao;
import hyun.post.dashboard.model.entity.Post;
import io.swagger.v3.oas.annotations.servers.Server;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Server
@RequiredArgsConstructor
public class PostService {

    private final PostDao postDao;
    private final XssConverter xssConverter;

    // TODO pagination 객체로 받아야함.
    public List<Post> findByParam() {
        return postDao.findAllByPagination();
    }
}
