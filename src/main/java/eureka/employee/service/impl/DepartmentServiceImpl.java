package eureka.employee.service.impl;

import eureka.employee.domain.Department;
import eureka.employee.domain.Employee;
import eureka.employee.dto.DepartmentDto;
import eureka.employee.dto.EmployeeDto;
import eureka.employee.dto.search.SearchDto;
import eureka.employee.repository.DepartmentRepository;
import eureka.employee.repository.EmployeeRepository;
import eureka.employee.service.DepartmentService;
import eureka.employee.service.core.impl.GenericServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.persistence.Query;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class DepartmentServiceImpl extends GenericServiceImpl<Department, Long> implements DepartmentService {
    @Autowired
    DepartmentRepository repo;

    @Autowired
    EmployeeRepository emrepo;

    @Override
    public DepartmentDto saveOrUpdate(Long id, DepartmentDto dto) {
        if (dto != null) {
            Department entity = null;
            if (dto.getId() != null) {
                if (dto.getId() != null && !dto.getId().equals(id)) {
                    return null;
                }
                entity = repo.getOne(dto.getId());
            }
            if (entity == null) {
                entity = new Department();
            }
            entity.setName(dto.getName());
            entity.setCode(dto.getCode());

            Set<Employee> setEmp = new HashSet<>();
            if(dto.getLikedEmployeeDto().size()>0){
                for(EmployeeDto d:dto.getLikedEmployeeDto()){
                    Employee emp = null;
                    if(d.getId()!=null){
                        emp = emrepo.getOne(d.getId());
                        setEmp.add(emp);
                    }
                }
            }
            entity.setLikedEmployee(setEmp);

            entity = repo.save(entity);
            if (entity != null) {
                return new DepartmentDto(entity);
            }

        }
        return null;
    }

    @Override
    public DepartmentDto getDepartmentByCode(String code) {
        return repo.getDepByCode(code);
    }

    @Override
    public List<DepartmentDto> getAll() {
        return repo.getListDepartment();
    }

    @Override
    public String delDepartment(Long id) {
        if (id != null) {
            repo.deleteById(id);
            return "success";
        }
        return null;
    }

    @Override
    public Page<DepartmentDto> searchByPage(SearchDto dto) {
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

        String sqlCount = "select count(entity.id) from  Department as entity where (1=1)   ";
        String sql = "select new eureka.employee.dto.DepartmentDto(entity) from  Department as entity where (1=1)  ";

        if (dto.getKeyword() != null && StringUtils.hasText(dto.getKeyword())) {
            whereClause += " AND ( entity.name LIKE :text OR entity.code LIKE :text )";
        }

        sql += whereClause + orderBy;
        sqlCount += whereClause;

        Query q = manager.createQuery(sql, DepartmentDto.class);
        Query qCount = manager.createQuery(sqlCount);

        if (dto.getKeyword() != null && StringUtils.hasText(dto.getKeyword())) {
            q.setParameter("text", '%' + dto.getKeyword() + '%');
            qCount.setParameter("text", '%' + dto.getKeyword() + '%');
        }

        int startPosition = pageIndex * pageSize;
        q.setFirstResult(startPosition);
        q.setMaxResults(pageSize);
        List<DepartmentDto> entities = q.getResultList();
        long count = (long) qCount.getSingleResult();

        Pageable pageable = PageRequest.of(pageIndex, pageSize);
        Page<DepartmentDto> result = new PageImpl<DepartmentDto>(entities, pageable, count);
        return result;
    }
}
