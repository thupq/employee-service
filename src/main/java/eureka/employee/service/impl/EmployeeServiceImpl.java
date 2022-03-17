package eureka.employee.service.impl;

import eureka.employee.domain.Department;
import eureka.employee.domain.Employee;
import eureka.employee.dto.DepartmentDto;
import eureka.employee.dto.EmployeeDto;
import eureka.employee.dto.search.SearchDto;
import eureka.employee.exception.ResourceNotFoundException;
import eureka.employee.repository.DepartmentRepository;
import eureka.employee.repository.EmployeeRepository;
import eureka.employee.service.EmployeeService;
import eureka.employee.service.core.impl.GenericServiceImpl;
import eureka.employee.ulti.ExceptionResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.persistence.Query;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class EmployeeServiceImpl extends GenericServiceImpl<Employee, Long> implements EmployeeService {
    @Autowired
    EmployeeRepository repo;

    @Autowired
    DepartmentRepository derepo;

    @Override
    public EmployeeDto saveOrUpdate(Long id, EmployeeDto dto) {
        if (dto != null) {
            Employee entity = null;
            if (dto.getId() != null) {
                if (dto.getId() != null && !dto.getId().equals(id)) {
                    return null;
                }
                entity = repo.getOne(dto.getId());
//                entity =repo.findById(id).orElseThrow(()-> new
//                        ResourceNotFoundException("Not found Tutorial with id = " + id));
            }
            if (entity == null) {
                entity = new Employee();
            }
            entity.setName(dto.getName());
            entity.setCode(dto.getCode());
            entity.setEmail(dto.getEmail());
            entity.setUsername(dto.getUsername());
            entity.setPassword(dto.getPassword());
            entity.setPosition(dto.getPosition());

            Set<Department> setDep = new HashSet<>();
            if(dto.getLikedDepartmentDto().size()>0){
                for(DepartmentDto d:dto.getLikedDepartmentDto()){
                    DepartmentDto depDto = null;
                    Department dep = null;
//                    if(d.getId()!=null){
//                        dep = derepo.getById(depDto.getId());
//                    }
                    if(d.getCode()!=null){
                        String code =  d.getCode();
                        depDto = derepo.getDepByCode(code);
                    }
                    dep = derepo.getOne(depDto.getId());
                    setDep.add(dep);
                }
            }
            entity.setLikedDepartment(setDep);

            entity = repo.save(entity);
            if (entity != null) {
                return new EmployeeDto(entity);
            }

        }
        return null;
    }

//    @Override
//    public EmployeeDto update(Long id, EmployeeDto dto) {
//        if (dto != null) {
//            Employee entity = null;
//            if (dto.getId() != null) {
//                if (dto.getId() != null && !dto.getId().equals(id)) {
//                    return null;
//                }
//                entity = repo.getOne(dto.getId());
//            }
//            if (entity == null) {
//                throw new ResourceNotFoundException("Not found with id = " + id);
//            }
//            entity.setName(dto.getName());
//            entity.setCode(dto.getCode());
//            entity.setEmail(dto.getEmail());
//            entity.setUsername(dto.getUsername());
//            entity.setPassword(dto.getPassword());
//            entity.setPosition(dto.getPosition());
//
//            Set<Department> setDep = new HashSet<>();
//            if(dto.getLikedDepartmentDto().size()>0){
//                for(DepartmentDto d:dto.getLikedDepartmentDto()){
//                    DepartmentDto depDto = null;
//                    Department dep = null;
//
//                    if(d.getCode()!=null){
//                        String code =  d.getCode();
//                        depDto = derepo.getDepByCode(code);
//                    }
//                    dep = derepo.getOne(depDto.getId());
//                    setDep.add(dep);
//                }
//            }
//            entity.setLikedDepartment(setDep);
//
//            entity = repo.save(entity);
//            if (entity != null) {
//                return new EmployeeDto(entity);
//            }
//        }
//        return null;
//    }

    @Override
    public EmployeeDto getEmployeeByCode(String code) {
        return repo.getEmpByCode(code);
    }

    @Override
    public EmployeeDto getEmployeeById(Long id) {
        Employee tutorial = repo.findById(id).orElseThrow(()-> new
                ResourceNotFoundException("Not found Tutorial with id = " + id));
        return new EmployeeDto(tutorial);
    }

    @Override
    public List<EmployeeDto> getAll() {
        return repo.getListEmployee();
    }

    @Override
    public String delEmployee(Long id) {
        Employee tutorial = repo.findById(id).orElseThrow(()-> new
                ResourceNotFoundException("Not found Tutorial with id = " + id));
        repo.deleteById(id);
        return "success";
    }

    @Override
    public Page<EmployeeDto> searchByPage(SearchDto dto) {
        if (dto == null) {
            return null;
        }

        int pageIndex = dto.getPageIndex();
        int pageSize = dto.getPageSize();

        if (pageIndex > 0) {
            pageIndex--;
        } else {
            pageIndex = 0;
        }

        String whereClause = "";

        String orderBy = " ORDER BY entity.createDate DESC";

        String sqlCount = "select count(entity.id) from  Employee as entity where (1=1)   ";
        String sql = "select new  eureka.employee.dto.EmployeeDto(entity) from  Employee as entity where (1=1)  ";

        if (dto.getKeyword() != null && StringUtils.hasText(dto.getKeyword())) {
            whereClause += " AND ( entity.name LIKE :text OR entity.code LIKE :text )";
        }

        sql += whereClause + orderBy;
        sqlCount += whereClause;

        Query q = manager.createQuery(sql, EmployeeDto.class);
        Query qCount = manager.createQuery(sqlCount);

        if (dto.getKeyword() != null && StringUtils.hasText(dto.getKeyword())) {
            q.setParameter("text", '%' + dto.getKeyword() + '%');
            qCount.setParameter("text", '%' + dto.getKeyword() + '%');
        }

        int startPosition = pageIndex * pageSize;
        q.setFirstResult(startPosition);
        q.setMaxResults(pageSize);
        List<EmployeeDto> entities = q.getResultList();
        long count = (long) qCount.getSingleResult();

        Pageable pageable = PageRequest.of(pageIndex, pageSize);
        Page<EmployeeDto> result = new PageImpl<EmployeeDto>(entities, pageable, count);
        return result;
    }
}
