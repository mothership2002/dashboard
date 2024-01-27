package hyun.post.dashboard.model.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

@Entity
@Table(name = "REPLY", indexes = {
        @Index(name = "REPLY_POST", columnList = "POST_ID"),
        @Index(name = "REPLY_MEMBER_UPDATE", columnList = "MODIFIED_BY"),
        @Index(name = "REPLY_MEMBER_CREATE", columnList = "CREATED_BY")
})
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Reply extends BaseColumn {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "REPLY_ID")
    @Comment("식별자")
    private Long id;

    @Comment("내용")
    @Column(name = "REPLY_CONTENT", nullable = false)
    private String content;

    @Comment("게시글 PK")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "POST_ID", nullable = false,
            foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private Post post;
}
