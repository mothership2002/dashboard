package hyun.post.dashboard.service;

import hyun.post.dashboard.dao.PostDao;
import io.swagger.v3.oas.annotations.servers.Server;
import lombok.RequiredArgsConstructor;

@Server
@RequiredArgsConstructor
public class PostService {

    private PostDao postDao;
}
