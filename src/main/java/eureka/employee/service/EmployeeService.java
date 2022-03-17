package eureka.employee.service;

import eureka.employee.domain.Employee;
import eureka.employee.dto.EmployeeDto;
import eureka.employee.dto.search.SearchDto;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

public interface EmployeeService {
    public EmployeeDto saveOrUpdate(Long id, EmployeeDto dto);
    //  public EmployeeDto update(Long id, EmployeeDto dto);
    public EmployeeDto getEmployeeByCode(String code);
    public EmployeeDto getEmployeeById(Long id);
    public List<EmployeeDto> getAll();
    public String delEmployee(Long id);
    public Page<EmployeeDto> searchByPage(SearchDto searchDto);
}
