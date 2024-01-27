package hyun.post.dashboard.dao;

import hyun.post.dashboard.repository.rdbms.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class PostDao {

    private final PostRepository repository;
}
