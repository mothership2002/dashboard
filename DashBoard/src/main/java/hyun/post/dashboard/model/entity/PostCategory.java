package hyun.post.dashboard.model.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "POST_CATEGORY",
        indexes = @Index(name = "CATEGORY_NAME", columnList = "POST_CATEGORY_NAME"))
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "POST_CATEGORY_ID")
    @Comment("카테고리 pk")
    private Long id;

    @Column(name = "POST_CATEGORY_NAME", unique = true, nullable = false)
    @Comment("카테고리명")
    private String name;

    @OneToMany(mappedBy = "postCategory")
    private List<Post> posts = new ArrayList<>();

    public PostCategory(String name) {
        this.name = name;
    }
}
