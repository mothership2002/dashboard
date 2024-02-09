package hyun.post.dashboard.model.entity;

import hyun.post.dashboard.model.common.HttpMethod;
import hyun.post.dashboard.model.relation.RoleAndResource;
import io.swagger.v3.oas.models.PathItem;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "RESOURCE")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Resource extends BaseColumn {

    @Id
    @Column(name = "RESOURCE_ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Comment("식별자")
    private Long id;

    @Comment("URL")
    @Column(name = "URL")
    private String url;

    @Comment("메소드")
    @Column(name = "METHOD")
    private HttpMethod method;

    @OneToMany(mappedBy = "resource")
    private List<RoleAndResource> roles = new ArrayList<>();

    public Resource(String url, HttpMethod method) {
        this.url = url;
        this.method = method;
    }
}
