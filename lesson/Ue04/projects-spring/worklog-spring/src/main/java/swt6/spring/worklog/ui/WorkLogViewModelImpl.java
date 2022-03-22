package swt6.spring.worklog.ui;

import swt6.spring.worklog.domain.Employee;
import swt6.spring.worklog.logic.WorkLogService;

// WorkLogViewModelImpl surrounds all of its methods with a SessionInterceptor
// that keeps the session open. This is important because lazy loading only works
// when an object's session is open. Otherwise you would get a LazyLoadingException
// when child elements are loaded lazily.
public class WorkLogViewModelImpl implements WorkLogViewModel {

    private WorkLogService workLog;

    public void setWorkLog(WorkLogService workLog) {
        this.workLog = workLog;
    }

    @Override
    public void saveEmployees(Employee... employees) {
        for (Employee e : employees)
            workLog.syncEmployee(e);
    }

    @Override
    public void findAll() {
        for (Employee e : workLog.findAllEmployees()) {
            System.out.println(e);
            e.getLogbookEntries().forEach(entry -> System.out.println("   " + entry));
        }
    }
}
