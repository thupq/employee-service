package eureka.employee.dto;

import eureka.employee.domain.Employee;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

public class EmployeeDto extends BaseObjectDto {
    @NotEmpty(message = "code khong de trong")
    @Size(min = 6, max = 10, message = "Do dai nam trong khoang 6-10")
    private String code;

    @NotEmpty(message = "name khong de trong")
    private String name;

    @NotEmpty(message = "username khong de trong")
    private String username;

    @NotEmpty(message = "password khong de trong")
    private String password;

    @Email(message = "Email khong hop le")
    private String email;

    @NotEmpty(message = "position khong de trong")
    private String position;

    Set<DepartmentDto> likedDepartmentDto;

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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public Set<DepartmentDto> getLikedDepartmentDto() {
        return likedDepartmentDto;
    }

    public void setLikedDepartmentDto(Set<DepartmentDto> likedDepartmentDto) {
        this.likedDepartmentDto = likedDepartmentDto;
    }

    public EmployeeDto() {
    }

    public EmployeeDto(Employee entity, Boolean check) {
        if (entity != null) {
            this.setId(entity.getId());
            this.code = entity.getCode();
            this.name = entity.getName();
            this.email = entity.getEmail();
            this.username = entity.getUsername();
            this.password = entity.getPassword();
            this.position = entity.getPosition();
            this.likedDepartmentDto =  new HashSet<DepartmentDto>();
            if (check && entity.getLikedDepartment() != null) {
                entity.getLikedDepartment().forEach(e -> this.likedDepartmentDto.add(new DepartmentDto(e,false)));
            }

        }
    }
    public EmployeeDto(Employee entity) {
        this(entity, true);
    }

    @Override
    public String toString() {
        return "EmployeeDto{" +
                "code='" + code + '\'' +
                ", name='" + name + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", position='" + position + '\'' +
                ", likedDepartmentDto=" + likedDepartmentDto +
                ", id=" + id +
                ", createDate=" + createDate +
                ", createdBy='" + createdBy + '\'' +
                ", modifyDate=" + modifyDate +
                ", modifiedBy='" + modifiedBy + '\'' +
                '}';
    }
}
