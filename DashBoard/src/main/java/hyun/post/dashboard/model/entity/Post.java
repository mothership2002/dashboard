package hyun.post.dashboard.model.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

import java.util.ArrayList;
import java.util.List;

@Entity(name = "POST")
@Table(name = "POST", indexes = {
        @Index(name = "POST_MEMBER_CREATED", columnList = "CREATED_BY"),
        @Index(name = "POST_MEMBER_UPDATED", columnList = "MODIFIED_BY")})
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Post extends BaseColumn {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "POST_ID")
    @Comment("식별자")
    private Long id;

    @Column(name = "POST_TITLE")
    @Comment("제목")
    private String title;

    @Column(name = "POST_CONTENT")
    @Comment("내용")
    private String content;

    @OneToMany(mappedBy = "post")
    private List<Reply> reply = new ArrayList<>();

    public Post(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public void update(Post post) {
        this.title = post.title;
        this.content = post.content;
    }
}
