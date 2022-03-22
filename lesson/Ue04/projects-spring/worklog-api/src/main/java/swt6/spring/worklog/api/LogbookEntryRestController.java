package swt6.spring.worklog.api;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import swt6.spring.worklog.domain.Employee;
import swt6.spring.worklog.domain.LogbookEntry;
import swt6.spring.worklog.dto.LogbookEntryDto;
import swt6.spring.worklog.exceptions.EmployeeNotFoundException;
import swt6.spring.worklog.logic.WorkLogService;

import java.lang.reflect.Type;
import java.util.*;


@RestController
@RequestMapping(value = "/worklog", produces = MediaType.APPLICATION_JSON_VALUE)
public class LogbookEntryRestController {

    @Autowired
    private WorkLogService workLog;

    @Autowired
    private ModelMapper mapper;

    @GetMapping("/entries")
    public List<LogbookEntryDto> getLogbookEntriesForEmployee(@RequestParam Long id) {

        Optional<Employee> employee = workLog.findEmployeeById(id);

        if(employee.isEmpty()) {
            throw new EmployeeNotFoundException(id);
        }

        var entries = new ArrayList<>(employee.get().getLogbookEntries());

        entries.sort(Comparator.comparing(LogbookEntry::getStartTime));

        Type listDtoType = new TypeToken<Collection<LogbookEntryDto>>() {
        }.getType();
        return mapper.map(entries, listDtoType);
    }
}
