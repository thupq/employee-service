package eureka.employee.service.core.impl;

import eureka.employee.service.core.GenericService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.io.Serializable;

@Transactional
public class GenericServiceImpl<T, Idt extends Serializable> implements GenericService<T, Idt> {
    @Autowired
    public JpaRepository<T, Idt> repository;
    @Autowired
    public EntityManager manager;

    public GenericServiceImpl() {
    }

    public T delete(Idt id) {
        T result = this.repository.getOne(id);
        if (result != null) {
            this.repository.deleteById(id);
        }

        return result;
    }

    public T save(T t) {
        T result = this.repository.save(t);
        return result;
    }

    public Page<T> getList(int pageIndex, int pageSize) {
        Pageable pageable = PageRequest.of(pageIndex - 1, pageSize);
        return this.repository.findAll(pageable);
    }

    public T findById(Idt id) {
        return this.repository.getOne(id);
    }
}