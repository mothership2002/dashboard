package hyun.post.dashboard.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import org.hibernate.annotations.Comment;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseDateColumn {

    @CreatedDate
    @Comment("생성일")
    @Column(name = "CREATED_AT", updatable = false)
    private LocalDateTime createdAt;
    
    @LastModifiedDate
    @Comment("수정일")
    @Column(name = "MODIFIED_AT")
    private LocalDateTime modifiedAt;
}
