package eureka.employee.service.core.auditing;

import eureka.employee.domain.BaseEntity;
import org.joda.time.LocalDateTime;

import javax.persistence.PostRemove;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

public class EntityAuditListener {
    public EntityAuditListener() {
    }

    @PostRemove
    public void postRemove(BaseEntity auditableEntity) {
        LocalDateTime now = LocalDateTime.now();
        auditableEntity.setModifyDate(now);
    }

    @PrePersist
    public void beforePersit(BaseEntity auditableEntity) {
        LocalDateTime now = LocalDateTime.now();
        auditableEntity.setCreateDate(now);
        auditableEntity.setModifyDate(now);
        auditableEntity.setCreatedBy("admin");
    }

    @PreUpdate
    public void beforeMerge(BaseEntity auditableEntity) {
        LocalDateTime now = LocalDateTime.now();
        auditableEntity.setModifyDate(now);
        auditableEntity.setCreatedBy("admin");
        auditableEntity.setModifiedBy("admin");
    }
}
