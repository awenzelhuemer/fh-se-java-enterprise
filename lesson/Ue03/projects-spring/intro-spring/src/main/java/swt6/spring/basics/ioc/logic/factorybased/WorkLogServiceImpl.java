package swt6.spring.basics.ioc.logic.factorybased;

import swt6.spring.basics.ioc.domain.Employee;
import swt6.spring.basics.ioc.util.Logger;
import swt6.spring.basics.ioc.util.LoggerFactory;

import java.io.IOException;
import java.util.*;

public class WorkLogServiceImpl {
    private Map<Long, Employee> employees = new HashMap<Long, Employee>();
    private Logger logger;

    private void initLogger() {
        Properties props = new Properties();

        try {
            ClassLoader cl = this.getClass().getClassLoader();
            props.load(cl.getResourceAsStream(
                    "swt6/spring/basics/ioc/logic/factorybased/worklog.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        String type = props.getProperty("loggerType", "console");
        logger = LoggerFactory.getLogger(type);
    }

    private void init() {
        employees.put(1L, new Employee(1L, "Bill", "Gates"));
        employees.put(2L, new Employee(2L, "James", "Goslin"));
        employees.put(3L, new Employee(3L, "Bjarne", "Stroustrup"));
    }

    public WorkLogServiceImpl() {
        initLogger();
        init();
    }


    public Employee findEmployeeById(Long id) {
        Employee empl = employees.get(id);
        logger.log(String.format("findEmployeeById(%d) --> %s", id, (empl != null) ? empl.toString() : "<null>"));
        return empl;
    }

    public List<Employee> findAllEmployees() {
        logger.log("findAllEmployees()");
        return new ArrayList<Employee>(employees.values());
    }
}
