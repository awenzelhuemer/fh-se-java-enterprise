package swt6.spring.worklog.test;

import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import swt6.spring.worklog.domain.Employee;
import swt6.spring.worklog.domain.LogbookEntry;
import swt6.spring.worklog.ui.WorkLogViewModel;
import swt6.util.PrintUtil;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class UiTest {

    private static void uiTest(String configFile) {
        Employee empl1 = new Employee("Sepp", "Forcher", LocalDate.of(1935, 12, 12));
        Employee empl2 = new Employee("Alfred", "Kunz", LocalDate.of(1944, 8, 10));
        Employee empl3 = new Employee("Sigfried", "Hinz", LocalDate.of(1954, 5, 3));

        LogbookEntry entry1 = new LogbookEntry("Analyse",
                LocalDateTime.of(2018, 3, 1, 10, 0), LocalDateTime.of(2018, 3, 1, 11, 30));
        LogbookEntry entry2 = new LogbookEntry("Implementierung",
                LocalDateTime.of(2018, 3, 1, 11, 30), LocalDateTime.of(2018, 3, 1, 16, 30));
        LogbookEntry entry3 = new LogbookEntry("Testen",
                LocalDateTime.of(2018, 3, 1, 10, 15), LocalDateTime.of(2018, 3, 1, 14, 30));

        try (AbstractApplicationContext context = new ClassPathXmlApplicationContext(configFile)) {
            var vm = context.getBean("workLogViewModel", WorkLogViewModel.class);

            PrintUtil.printTitle("saveEmployees", 60, '-');

            vm.saveEmployees(empl1, empl2, empl3);

            empl1.addLogbookEntry(entry1);
            empl1.addLogbookEntry(entry2);
            empl2.addLogbookEntry(entry3);
            vm.saveEmployees(empl1, empl2, empl3);
            PrintUtil.printTitle("saveEmployees", 60, '-');

            PrintUtil.printTitle("findAll", 60, '-');
            vm.findAll();
        }
    }

    public static void main(String[] args) {
        uiTest("swt6/spring/worklog/test/applicationContext-jpa1.xml");
    }
}
