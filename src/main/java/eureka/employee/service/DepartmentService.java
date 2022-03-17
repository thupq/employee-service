package eureka.employee.service;

import eureka.employee.dto.DepartmentDto;
import eureka.employee.dto.search.SearchDto;
import org.springframework.data.domain.Page;

import java.util.List;

public interface DepartmentService {
    public DepartmentDto saveOrUpdate(Long id, DepartmentDto dto);
    public DepartmentDto getDepartmentByCode(String code);
    public List<DepartmentDto> getAll();
    public String delDepartment(Long id);
    public Page<DepartmentDto> searchByPage(SearchDto searchDto);
}
