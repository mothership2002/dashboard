package hyun.post.dashboard.model.relation;

import hyun.post.dashboard.model.entity.Resource;
import hyun.post.dashboard.model.entity.Role;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "ROLE_AND_RESOURCE",
        indexes = {
                @Index(name = "ROLE", columnList = "ROLE_ID"),
                @Index(name = "RESOURCE", columnList = "RESOURCE_ID")})
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RoleAndResource {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ROLE_RESOURCE_ID")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ROLE_ID", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private Role role;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "RESOURCE_ID", foreignKey = @ForeignKey(ConstraintMode.CONSTRAINT))
    private Resource resource;

    public RoleAndResource(Role role, Resource resource) {
        this.role = role;
        this.resource = resource;
    }
}
