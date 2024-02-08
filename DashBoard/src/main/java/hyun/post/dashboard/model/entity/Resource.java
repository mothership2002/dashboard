package hyun.post.dashboard.model.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.Comment;

@Entity
@Table(name = "RESOURCE")
public class Resource extends BaseColumn {

    @Id
    @Column(name = "RESOURCE_ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Comment("식별자")
    private Long resourceId;

    @Comment("URL")
    @Column(name = "URL")
    private String url;

    
}
