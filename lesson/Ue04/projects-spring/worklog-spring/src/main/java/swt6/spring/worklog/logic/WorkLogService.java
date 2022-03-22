package swt6.spring.worklog.logic;

import swt6.spring.worklog.domain.Employee;
import swt6.spring.worklog.domain.LogbookEntry;

import java.util.List;
import java.util.Optional;

public interface WorkLogService {
    Employee syncEmployee(Employee employee);

    Optional<Employee> findEmployeeById(Long id);

    List<Employee> findAllEmployees();

    LogbookEntry syncLogbookEntry(LogbookEntry entry);

    Optional<LogbookEntry> findLogbookEntryById(Long id);
}
