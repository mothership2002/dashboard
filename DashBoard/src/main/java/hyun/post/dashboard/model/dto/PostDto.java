package hyun.post.dashboard.model.dto;

import hyun.post.dashboard.model.entity.Post;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PostDto {

    private Long postId;
    private String title;
    private String content;
    private String categoryName;

    public PostDto(Post post, String categoryName) {
        this.postId = post.getId();
        this.title = post.getTitle();
        this.content = post.getContent();
        this.categoryName = categoryName;
    }
}
