package swt6.spring.worklog.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import swt6.spring.worklog.dto.EmployeeDto;
import swt6.spring.worklog.exceptions.EmployeeNotFoundException;
import swt6.spring.worklog.logic.WorkLogService;

import java.lang.reflect.Type;
import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping(value = "/workLog", produces = MediaType.APPLICATION_JSON_VALUE)
public class EmployeeRestController {

    private final Logger logger = LoggerFactory.getLogger(EmployeeRestController.class);

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private WorkLogService workLog;

    public EmployeeRestController() {
        logger.info("EmployeeRestController created");
    }

    @GetMapping(value = "hello", produces = MediaType.TEXT_PLAIN_VALUE)
    public String hello() {
        logger.info("EmployeeRestController.hello()");
        return "Hello from EmployeeRestController";
    }

    @GetMapping("/employees")
    @Operation(summary = "Employee list", description = "Returns a list of all stored employees.")
    @ApiResponse(responseCode = "200", description = "Success")
    public List<EmployeeDto> getAllEmployees() {
        var employees = workLog.findAllEmployees();
        Type listDtoType = new TypeToken<Collection<EmployeeDto>>() {
        }.getType();
        return mapper.map(employees, listDtoType);
    }

    @GetMapping("/employees/{id}")
    @Operation(summary = "Employee data", description = "Returns detailed data for a given employee.")
    @ApiResponse(responseCode = "200", description = "Success")
    @ApiResponse(responseCode = "404", description = "Employee entry not found")
    public EmployeeDto getEmployeeById(@PathVariable("id") Long id) {
        var employee = workLog.findEmployeeById(id);
        if (employee.isEmpty()) {
            throw new EmployeeNotFoundException(id);
        }

        return mapper.map(employee.get(), EmployeeDto.class);
    }
}
