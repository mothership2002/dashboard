package hyun.post.dashboard.model.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "ROLE")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Role extends BaseColumn {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ROLE_ID")
    @Comment("식별자")
    private Long id;

    @Column(name = "ROLE_NAME", unique = true, updatable = false, nullable = false)
    @Comment("접근 권한 이름")
    private String roleName;

    @Column(name = "PRIORITY")
    @Comment("우선 순위")
    private Integer priority;

    @OneToMany(mappedBy = "role")
    private List<Member> membersList = new ArrayList<>();


}
