package eureka.employee.repository;

import eureka.employee.domain.Department;
import eureka.employee.dto.DepartmentDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Long> {
    @Query("select new eureka.employee.dto.DepartmentDto(de) from Department de")
    List<DepartmentDto> getListDepartment();

    @Query("select new eureka.employee.dto.DepartmentDto(de) from Department de where de.code = ?1")
    DepartmentDto getDepByCode(String code);
}
