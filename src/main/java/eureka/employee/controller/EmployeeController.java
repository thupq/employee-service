package eureka.employee.controller;

import eureka.employee.dto.EmployeeDto;
import eureka.employee.dto.LogDto;
import eureka.employee.dto.search.SearchDto;
import eureka.employee.exception.ResourceNotFoundException;
import eureka.employee.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
//import org.springframework.web.client.RestTemplate;

import javax.validation.Valid;
import java.util.List;

/**
 *
 */
@RestController
@RequestMapping("/api/employee")
public class EmployeeController {
//    @Autowired
//    private RestTemplate restTemplate;

    @Autowired
    private Environment environment;

    @Autowired
    EmployeeService empService;

//    @Autowired
//    EmployeeRepository repo;

    // sua

    // them
//    @ExceptionHandler(value = CustomizedResponseEntityExceptionHandler.class)

    /**
     * Dung de luu thong tin nhan vien
     * @param dto
     * @return EmployeeDto
     */
    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<?> save(@Valid @RequestBody EmployeeDto dto) {

        /*
        * xử lý
        * bắn data để lưu log-service:
        *
        */

        /*
        * b1: Khởi tạo Log
        * b2: check code
        * b3: get infor employee
        * b4: set các thuộc tính cho log
        * b5: return
        * */
        LogDto log = new LogDto();
        String urlLog = "http://log-service/api/log/"; //ban data truc tiep sang log-service
//        String urlLog = "http://log/api/log/";

        EmployeeDto checkCode = null;
        if (dto.getCode() != null) {
            checkCode = empService.getEmployeeByCode(dto.getCode());
        }
//        String.format("Code %s already exists", dto.getCode(), dto.getEmail());
        if (checkCode != null) {
            throw new ResourceNotFoundException("Code: " + dto.getCode() + " already exists!");
        }
        EmployeeDto result = empService.saveOrUpdate(null, dto);

        ResponseEntity res = new ResponseEntity<EmployeeDto>(result, HttpStatus.CREATED);
        log.setResponse(res.toString());
        log.setRequest(dto.toString());
        log.setServicename(environment.getProperty("spring.application.name"));
        log.setAction("CREATE");
        log.setFunction("save");

//        LogDto lg = restTemplate.postForObject(urlLog, log, LogDto.class);

        return new ResponseEntity<EmployeeDto>(result, HttpStatus.CREATED);
    }

    @RequestMapping("/greeting")
    public String greeting() {
        return "Welcome !";
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<EmployeeDto> save(@Valid @RequestBody EmployeeDto dto, @PathVariable Long id) {
        LogDto log = new LogDto();
        String urlLog = "http://log-service/api/log/"; //ban data truc tiep sang log-service
       // String urlLog = "http://log/api/log/";

        EmployeeDto tutorial = empService.getEmployeeById(id);
        EmployeeDto result = empService.saveOrUpdate(id, dto);

        ResponseEntity res = new ResponseEntity<EmployeeDto>(result, HttpStatus.OK);
        log.setResponse(res.toString());

        log.setRequest(dto.toString());
        log.setServicename(environment.getProperty("spring.application.name"));
        log.setAction("UPDATE");
        log.setFunction("save");

//        LogDto lg = restTemplate.postForObject(urlLog, log, LogDto.class);

        return new ResponseEntity<EmployeeDto>(result, HttpStatus.OK);
    }

    //get all
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<EmployeeDto>> listEmployee() {
        List<EmployeeDto> empls = null;
        empls = empService.getAll();

        ResponseEntity res = new ResponseEntity<List<EmployeeDto>>(empls, HttpStatus.OK);
        String os = res.toString();
        System.out.println(os);

        return new ResponseEntity<List<EmployeeDto>>(empls, HttpStatus.OK);
    }

    //search by page
    @RequestMapping(value = "/searchByPage", method = RequestMethod.POST)
    public ResponseEntity<Page<EmployeeDto>> searchByPage(@RequestBody SearchDto searchDto) {
        Page<EmployeeDto> page = empService.searchByPage(searchDto);
        return new ResponseEntity<Page<EmployeeDto>>(page, HttpStatus.OK);
    }

    //xoa
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<String> delEmployee(@PathVariable Long id) {
        LogDto log = new LogDto();
        String urlLog = "http://log-service/api/log/"; //ban data truc tiep sang log-service
//        String urlLog = "http://log/api/log/";
        String mgs = empService.delEmployee(id);

        ResponseEntity res = new ResponseEntity<String>(mgs, HttpStatus.OK);

        log.setResponse(res.toString());
        log.setRequest("id:"+id);
        log.setServicename(environment.getProperty("spring.application.name"));
        log.setAction("DELETE");
        log.setFunction("delEmployee");

//        LogDto lg = restTemplate.postForObject(urlLog, log, LogDto.class);

        return new ResponseEntity<String>(mgs, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmployeeDto> getTutorialById(@PathVariable("id") long id) {
        EmployeeDto tutorial = empService.getEmployeeById(id);
        return new ResponseEntity<EmployeeDto>(tutorial, HttpStatus.OK);
    }
}
