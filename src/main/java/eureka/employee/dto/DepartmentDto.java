package eureka.employee.dto;

import eureka.employee.domain.Department;

import java.util.Set;

public class DepartmentDto extends BaseObjectDto {
    private String code;
    private String name;
    Set<EmployeeDto> likedEmployeeDto;

    public DepartmentDto() {
    }

    public DepartmentDto(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<EmployeeDto> getLikedEmployeeDto() {
        return likedEmployeeDto;
    }

    public void setLikedEmployeeDto(Set<EmployeeDto> likedEmployeeDto) {
        this.likedEmployeeDto = likedEmployeeDto;
    }

    public DepartmentDto(Department entity, Boolean check) {
        if (entity != null) {
            this.setId(entity.getId());
            this.code = entity.getCode();
            this.name = entity.getName();
        }
    }
    public DepartmentDto(Department entity) {
        this(entity, true);
    }
}
