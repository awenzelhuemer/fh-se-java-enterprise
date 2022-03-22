package swt6.spring.worklog.logic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import swt6.spring.worklog.dao.EmployeeRepository;
import swt6.spring.worklog.dao.LogbookEntryRepository;
import swt6.spring.worklog.domain.Employee;
import swt6.spring.worklog.domain.LogbookEntry;

import java.util.List;
import java.util.Optional;

@Service("workLog")
@Transactional
public class WorkLogServiceImpl2 implements WorkLogService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private LogbookEntryRepository logbookEntryRepository;

    @Override
    public Employee syncEmployee(Employee employee) {
        return employeeRepository.saveAndFlush(employee);
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<Employee> findEmployeeById(Long id) {
        return employeeRepository.findById(id);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Employee> findAllEmployees() {
        return employeeRepository.findAll();
    }

    @Override
    public LogbookEntry syncLogbookEntry(LogbookEntry entry) {
        return logbookEntryRepository.saveAndFlush(entry);
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<LogbookEntry> findLogbookEntryById(Long id) {
        return logbookEntryRepository.findById(id);
    }
}
