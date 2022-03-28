package swt6.spring.worklog.logic;

import org.springframework.transaction.annotation.Transactional;
import swt6.spring.worklog.dao.EmployeeDao;
import swt6.spring.worklog.domain.Employee;
import swt6.spring.worklog.domain.LogbookEntry;

import java.util.List;
import java.util.Optional;

public class WorkLogServiceImpl1 implements WorkLogService {

    private EmployeeDao employeeDao;

    public void setEmployeeDao(EmployeeDao employeeDao) {
        this.employeeDao = employeeDao;
    }

    @Override
    public Employee syncEmployee(Employee employee) {
        return employeeDao.merge(employee);
    }

    @Override
    public Optional<Employee> findEmployeeById(Long id) {
        return employeeDao.findById(id);
    }

    @Override
    public List<Employee> findAllEmployees() {
        return employeeDao.findAll();
    }

    @Override
    public LogbookEntry syncLogbookEntry(LogbookEntry entry) {
        return null;
    }

    @Override
    public Optional<LogbookEntry> findLogbookEntryById(Long id) {
        return Optional.empty();
    }
}
