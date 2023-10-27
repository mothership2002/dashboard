package hyun.post.dashboard.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import org.hibernate.annotations.Comment;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseColumn extends BaseDateColumn {

    @CreatedBy
    @Comment("생성자")
    @Column(name = "CREATED_BY", updatable = false)
    private Long createdBy;

    @LastModifiedBy
    @Comment("수정자")
    @Column(name = "MODIFIED_BY")
    private Long modifiedBy;
    
    @Comment("삭제일")
    @Column(name = "DELETED_BY")
    private Long deletedBy;
}
