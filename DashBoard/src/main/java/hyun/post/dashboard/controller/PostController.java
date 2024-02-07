package hyun.post.dashboard.controller;

import hyun.post.dashboard.aop.annotation.InboundContent;
import hyun.post.dashboard.model.common.CommonResponse;
import hyun.post.dashboard.model.dto.PostDto;
import hyun.post.dashboard.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/v1/post")
@RestController
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @PostMapping
    @InboundContent(PostDto.class)
    public ResponseEntity<?> createPost(@RequestBody PostDto postDto) {
        return new ResponseEntity<>(new CommonResponse<>(
                "success", postService.create(postDto)),
                HttpStatus.CREATED);
    }

}
