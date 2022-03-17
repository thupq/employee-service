package eureka.employee.domain;

import eureka.employee.service.core.auditing.EntityAuditListener;
import org.hibernate.annotations.Type;
import org.joda.time.LocalDateTime;

import javax.persistence.*;
import java.io.Serializable;

@MappedSuperclass
@EntityListeners({EntityAuditListener.class})
public class BaseEntity implements Serializable {
    @Transient
    private static final long serialVersionUID = 4559994432567537044L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(
            name = "create_date",
            nullable = true
    )
    @Type(
            type = "org.jadira.usertype.dateandtime.joda.PersistentLocalDateTime"
    )
    private LocalDateTime createDate;

    @Column(
            name = "created_by",
            length = 100,
            nullable = true
    )
    private String createdBy;
    @Column(
            name = "modify_date",
            nullable = true
    )
    @Type(
            type = "org.jadira.usertype.dateandtime.joda.PersistentLocalDateTime"
    )
    private LocalDateTime modifyDate;

    @Column(
            name = "modified_by",
            length = 100,
            nullable = true
    )
    private String modifiedBy;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public LocalDateTime getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }

    public LocalDateTime getModifyDate() {
        return modifyDate;
    }

    public void setModifyDate(LocalDateTime modifyDate) {
        this.modifyDate = modifyDate;
    }

    public BaseEntity() {
    }

    public BaseEntity(BaseEntity entity) {
        if(entity!=null){
            this.id = entity.getId();
            this.createDate = entity.getCreateDate();
            this.createdBy = entity.getCreatedBy();
            this.modifyDate = entity.getModifyDate();
            this.modifiedBy = entity.getModifiedBy();
        }


    }
}
