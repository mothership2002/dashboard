package hyun.post.dashboard.controller;

import hyun.post.dashboard.aop.annotation.InboundContent;
import hyun.post.dashboard.exception.NotMatchArgumentException;
import hyun.post.dashboard.model.common.CommonResponse;
import hyun.post.dashboard.model.dto.PostDto;
import hyun.post.dashboard.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/v1/post")
@RestController
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @PostMapping("/{categoryName}")
    @InboundContent(PostDto.class)
    public ResponseEntity<?> createPost(@RequestBody PostDto postDto,
                                        @PathVariable String categoryName) {
        if (!categoryName.equals(postDto.getCategoryName())) {
            throw new NotMatchArgumentException();
        }
        return new ResponseEntity<>(new CommonResponse<>(
                "success", postService.create(postDto)),
                HttpStatus.CREATED);
    }

}
