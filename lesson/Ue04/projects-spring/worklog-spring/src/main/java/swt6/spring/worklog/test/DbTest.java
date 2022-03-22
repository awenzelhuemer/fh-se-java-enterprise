package swt6.spring.worklog.test;

import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import swt6.spring.worklog.dao.EmployeeDao;
import swt6.spring.worklog.dao.EmployeeRepository;
import swt6.spring.worklog.domain.Employee;
import swt6.util.DbScriptRunner;
import swt6.util.JpaUtil;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import javax.swing.text.html.Option;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Optional;

import static swt6.util.PrintUtil.printSeparator;
import static swt6.util.PrintUtil.printTitle;

public class DbTest {

    private static void createSchema(DataSource ds, String ddlScript) {
        try {
            DbScriptRunner scriptRunner = new DbScriptRunner(ds.getConnection());
            InputStream is = DbTest.class.getClassLoader().getResourceAsStream(ddlScript);
            if (is == null)
                throw new IllegalArgumentException(String.format("File %s not found in classpath.", ddlScript));
            scriptRunner.runScript(new InputStreamReader(is));
        } catch (SQLException | IOException e) {
            e.printStackTrace();
            return;
        }
    }

    private static void testJdbc() {

        try (AbstractApplicationContext factory =
                     new ClassPathXmlApplicationContext(
                             "swt6/spring/worklog/test/applicationContext-jdbc.xml")) {

            printTitle("create schema", 60, '-');
            createSchema(factory.getBean("dataSource", DataSource.class),
                    "swt6/spring/worklog/test/CreateWorklogDbSchema.sql");

            EmployeeDao employeeDao = factory.getBean("employeeDaoJdbc", EmployeeDao.class);

            printTitle("insert employee", 60, '-');
            Employee employee1 = new Employee("Max", "Muster", LocalDate.of(2020, 1, 1));
            // employeeDao.insert(employee1);
            employee1 = employeeDao.merge(employee1);

            System.out.println("employee1 = " + employee1);

            printTitle("update employee", 60, '-');
            employee1.setFirstName("Anna");
            employee1 = employeeDao.merge(employee1);
            System.out.println("employee1 = " + employee1);

            printTitle("find all employees", 60, '-');
            employeeDao.findAll().forEach(System.out::println);

            printTitle("find employee by id", 60, '-');
            Optional<Employee> employee = employeeDao.findById(1L);
            System.out.println("employee = " + employee);

            employee = employeeDao.findById(10L);
            System.out.println("employee = " + employee);
        }
    }

    @SuppressWarnings("unused")
    private static void testJpa() {
        try (AbstractApplicationContext factory = new ClassPathXmlApplicationContext(
                "swt6/spring/worklog/test/applicationContext-jpa1.xml")) {

            EntityManagerFactory emFactory = factory.getBean(EntityManagerFactory.class);
            EmployeeDao employeeDao = factory.getBean("employeeDao", EmployeeDao.class);
            Employee employee1 = new Employee("Test", "Test", LocalDate.of(1990, 1, 1));
            try {
                JpaUtil.beginTransaction(emFactory);
                employeeDao.insert(employee1);
                employee1.setFirstName("Max");
                employee1 = employeeDao.merge(employee1);
                System.out.println(employee1);
            } finally {
                JpaUtil.commitTransaction(emFactory);
            }

            // JpaUtil.executeInTransaction(emFactory, () -> employeeDao.insert(employee1));

            printTitle("find employeee", 60, '-');
            final long employee1Id = employee1.getId();
            JpaUtil.executeInTransaction(emFactory, () -> {
                Optional<Employee> employee = employeeDao.findById(employee1Id);
                System.out.println("employee=" + employee.orElse(null));
                employee = employeeDao.findById(100L);
                System.out.println("employee=" + employee.orElse(null));
            });


            JpaUtil.executeInTransaction(emFactory, () -> {
                printTitle("find all employees", 60, '-');
                employeeDao.findAll().forEach(System.out::println);
            });


        }
    }

    @SuppressWarnings("unused")
    private static void testSpringData() {
        try (AbstractApplicationContext factory = new ClassPathXmlApplicationContext(
                "swt6/spring/worklog/test/applicationContext-jpa1.xml")) {

            EntityManagerFactory emFactory = factory.getBean(EntityManagerFactory.class);

            JpaUtil.executeInTransaction(emFactory, () -> {
                EmployeeRepository employeeRepository = JpaUtil.getJpaRepository(emFactory, EmployeeRepository.class);

                Employee employee1 = new Employee("Anna", "Musterfrau", LocalDate.of(1980, 1, 1));
                printTitle("insert employee", 60, '-');
                employee1 = employeeRepository.save(employee1);
                employeeRepository.flush();

                printTitle("update employee", 60, '-');
                employee1 = employeeRepository.save(employee1);
            });

            printSeparator(60, '-');
            JpaUtil.executeInTransaction(emFactory, () -> {
                EmployeeRepository employeeRepository = JpaUtil.getJpaRepository(emFactory, EmployeeRepository.class);
                printTitle("findById", 60, '-');
                Optional<Employee> employee1 = employeeRepository.findById(1L);
                System.out.println("employee=" + employee1.orElse(null));
                printTitle("findAll", 60, '-');
                employeeRepository.findAll().forEach(System.out::println);
            });

            printSeparator(60, '-');
            JpaUtil.executeInTransaction(emFactory, () -> {
                EmployeeRepository employeeRepository = JpaUtil.getJpaRepository(emFactory, EmployeeRepository.class);

                printTitle("findByLastName", 60, '-');
                var employee2 = employeeRepository.findByLastName("Test");
                System.out.println("employee=" + employee2.orElse(null));


                printTitle("findOlderThan", 60, '-');
                var employees = employeeRepository.findOlderThan(LocalDate.of(1990, 1, 1));
                System.out.println(employees);
            });
        }
    }

    public static void main(String[] args) {

        // printSeparator(60);
        // printTitle("testJDBC", 60);
        // printSeparator(60);
        // testJdbc();

        // printSeparator(60);
        // printTitle("testJpa", 60);
        // printSeparator(60);
        // testJpa();

        printSeparator(60);
        printTitle("testSpringData", 60);
        printSeparator(60);
        testSpringData();
    }
}
