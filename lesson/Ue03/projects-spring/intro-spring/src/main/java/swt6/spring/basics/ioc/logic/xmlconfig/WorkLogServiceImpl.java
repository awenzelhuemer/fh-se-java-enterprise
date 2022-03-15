package swt6.spring.basics.ioc.logic.xmlconfig;

import swt6.spring.basics.ioc.domain.Employee;
import swt6.spring.basics.ioc.logic.WorkLogService;
import swt6.spring.basics.ioc.util.Logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WorkLogServiceImpl implements WorkLogService {
    private Map<Long, Employee> employees = new HashMap<Long, Employee>();
    private Logger logger;

    public WorkLogServiceImpl() {
        init();
    }

    public WorkLogServiceImpl(Logger logger) {
        init();
        this.logger = logger;
    }

    public void setLogger(Logger logger) {
        this.logger = logger;
    }

    private void init() {
        employees.put(1L, new Employee(1L, "Bill", "Gates"));
        employees.put(2L, new Employee(2L, "James", "Goslin"));
        employees.put(3L, new Employee(3L, "Bjarne", "Stroustrup"));
    }

    @Override
    public Employee findEmployeeById(Long id) {
        Employee empl = employees.get(id);
        logger.log(String.format("findEmployeeById(%d) --> %s", id, (empl != null) ? empl.toString() : "<null>"));
        return empl;
    }

    @Override
    public List<Employee> findAllEmployees() {
        logger.log("findAllEmployees()");
        return new ArrayList<Employee>(employees.values());
    }
}
