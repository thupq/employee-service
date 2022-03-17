package eureka.employee.domain;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Set;

@Entity
@Table(name = "tbl_department")
public class Department extends BaseEntity {
    public static final long serialVersionUID = 1L;

    @NotBlank
    @Column(name = "code")
    private String code;

    @NotBlank
    @Column(name = "name")
    private String name;

    @ManyToMany(mappedBy = "likedDepartment",fetch = FetchType.LAZY)
    Set<Employee> likedEmployee;

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

    public Set<Employee> getLikedEmployee() {
        return likedEmployee;
    }

    public void setLikedEmployee(Set<Employee> likedEmployee) {
        this.likedEmployee = likedEmployee;
    }
}
