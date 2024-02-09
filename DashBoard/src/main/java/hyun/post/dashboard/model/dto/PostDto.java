package hyun.post.dashboard.model.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PostDto {

    private Long postId;
    private String title;
    private String content;
    private String categoryName;

}
