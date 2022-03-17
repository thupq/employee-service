package eureka.employee.dto;

import org.joda.time.LocalDateTime;

import java.io.Serializable;


public class BaseObjectDto implements Serializable {
    protected Long id;
    protected LocalDateTime createDate;
    protected String createdBy;
    protected LocalDateTime modifyDate;
    protected String modifiedBy;
    public BaseObjectDto() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public LocalDateTime getModifyDate() {
        return modifyDate;
    }

    public void setModifyDate(LocalDateTime modifyDate) {
        this.modifyDate = modifyDate;
    }

    public String getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public BaseObjectDto(BaseObjectDto dto) {
        if(dto!=null) {
            this.id = dto.getId();
            this.createDate = dto.getCreateDate();
            this.createdBy = dto.getCreatedBy();
            this.modifyDate = dto.modifyDate;
            this.modifiedBy = dto.getModifiedBy();
        }
    }
}
