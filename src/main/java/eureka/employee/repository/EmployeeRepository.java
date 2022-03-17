package eureka.employee.repository;

import eureka.employee.domain.Employee;
import eureka.employee.dto.EmployeeDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    @Query("select new eureka.employee.dto.EmployeeDto(em) from Employee em")
    List<EmployeeDto> getListEmployee();

    @Query("select new eureka.employee.dto.EmployeeDto(em) from Employee em where em.code = ?1")
    EmployeeDto getEmpByCode(String code);

    @Query("select new eureka.employee.dto.EmployeeDto(em) from Employee em where em.id = ?1")
    EmployeeDto getEmpById(Long id);
}
