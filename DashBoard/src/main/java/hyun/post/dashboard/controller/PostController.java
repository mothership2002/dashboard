package hyun.post.dashboard.controller;

import hyun.post.dashboard.model.dto.PostDto;
import hyun.post.dashboard.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/post")
@RestController
@RequiredArgsConstructor
public class PostController {

    private PostService postService;

    @PostMapping
    public ResponseEntity<Void> createPost(PostDto post) {

        return null;
    }

}
