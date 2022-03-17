package eureka.employee.controller;

import eureka.employee.dto.DepartmentDto;
import eureka.employee.dto.search.SearchDto;
import eureka.employee.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/department")
public class DepartmentController {
    @Autowired
    DepartmentService service;

    // them
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<?> save(@RequestBody DepartmentDto dto) {
//        Department checkCode = null;
//        if (dto.getCode() != null) {
//            checkCode = service.getDepartmentByCode(dto.getCode());
//        }
//        if (checkCode != null) {
//            return new ResponseEntity<String>("Code already exists", HttpStatus.BAD_REQUEST);
//        }
        DepartmentDto result = service.saveOrUpdate(null, dto);
        return new ResponseEntity<DepartmentDto>(result, HttpStatus.OK);
    }

    // sua
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<DepartmentDto> save(@RequestBody DepartmentDto dto, @PathVariable Long id) {
        DepartmentDto result = service.saveOrUpdate(id, dto);
        return new ResponseEntity<DepartmentDto>(result, HttpStatus.OK);
    }

    //get all
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<DepartmentDto>> listEmployee() {
        List<DepartmentDto> empls = null;
        empls = service.getAll();
        return new ResponseEntity<List<DepartmentDto>>(empls, HttpStatus.OK);
    }

    //search by page
    @RequestMapping(value = "/searchByPage", method = RequestMethod.POST)
    public ResponseEntity<Page<DepartmentDto>> searchByPage(@RequestBody SearchDto searchDto) {
        Page<DepartmentDto> page = service.searchByPage(searchDto);
        return new ResponseEntity<Page<DepartmentDto>>(page, HttpStatus.OK);
    }

    //xoa
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<String> delEmployee(@PathVariable Long id) {

        String mgs = service.delDepartment(id);
        if (mgs != null) {
            return new ResponseEntity<String>(mgs, HttpStatus.OK);
        }
        return new ResponseEntity<String>("Id khong ton tai", HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
