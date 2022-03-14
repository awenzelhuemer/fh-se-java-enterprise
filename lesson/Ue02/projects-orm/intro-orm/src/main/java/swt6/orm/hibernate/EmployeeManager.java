package swt6.orm.hibernate;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;
import swt6.orm.domain.Employee;
import swt6.util.HibernateUtil;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class EmployeeManager {

    static String promptFor(BufferedReader in, String p) {
        System.out.print(p + "> ");
        System.out.flush();
        try {
            return in.readLine();
        } catch (Exception e) {
            return promptFor(in, p);
        }
    }

    private static void saveEmployee1(Employee employee) {
        SessionFactory sessionFactory = new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();

        Session session = sessionFactory.openSession();

        Transaction tx = session.beginTransaction();
        session.save(employee);

        tx.commit();

        session.close();

        sessionFactory.close();
    }

    private static void saveEmployee2(Employee employee) {
        SessionFactory sessionFactory = new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();

        // Session is bound to one thread only
        // Close is not needed
        Session session = sessionFactory.getCurrentSession();

        Transaction tx = session.beginTransaction();
        session.save(employee);
        tx.commit();

        sessionFactory.close();
    }

    private static void saveEmployee(Employee employee) {
        Session session = HibernateUtil.getCurrentSession();
        Transaction tx = session.beginTransaction();

        session.save(employee);

        System.out.println(employee.getId());

        tx.commit();
    }

    private static List<Employee> getAllEmployees() {
        Session session = HibernateUtil.getCurrentSession();
        Transaction tx = session.beginTransaction();

        List<Employee> employees = session.createQuery("select e from Employee e", Employee.class).getResultList();

        tx.commit();

        return employees;
    }

    private static boolean updateEmployee(long employeeId, String firstName, String lastName, LocalDate dob) {
        Session session = HibernateUtil.getCurrentSession();
        Transaction tx = session.beginTransaction();

        Employee employee = session.find(Employee.class, employeeId);

        if (employee != null) {
            employee.setFirstName(firstName);
            employee.setLastName(lastName);
            employee.setDateOfBirth(dob);
        }

        tx.commit();

        return employee != null;
    }

    private static boolean deleteEmployee(long employeeId) {
        Session session = HibernateUtil.getCurrentSession();
        Transaction tx = session.beginTransaction();

//        var employee = session.find(Employee.class, employeeId);
//
//        if(employee != null) {
//            session.remove(employee);
//        }

        Query<?> deleteQuery = session.createQuery("delete from Employee e where e.id = :id");
        deleteQuery.setParameter("id", employeeId);
        boolean deleted = deleteQuery.executeUpdate() > 0;

        tx.commit();

        return deleted;
    }

    private static Employee findEmployeeById(long employeeId) {
        Session session = HibernateUtil.getCurrentSession();
        Transaction tx = session.beginTransaction();

        Employee employee = session.find(Employee.class, employeeId);

        tx.commit();

        return employee;
    }

    private static List<Employee> findEmployeeByLastName(String lastName) {
        Session session = HibernateUtil.getCurrentSession();
        Transaction tx = session.beginTransaction();

        Query<Employee> query = session.createQuery("select e from Employee e where e.lastName like :lastName", Employee.class);
        query.setParameter("lastName", "%" + lastName + "%");

        var employees = query.getResultList();

        tx.commit();

        return employees;
    }

    public static void main(String[] args) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d.M.yyyy");
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        String availCmds = "commands: quit, list, findById, findByLastName, insert, update, delete";

        System.out.println("Hibernate Employee Admin");
        System.out.println(availCmds);
        String userCmd = promptFor(in, "");

        try {
            HibernateUtil.getSessionFactory();
            while (!userCmd.equals("quit")) {

                switch (userCmd) {
                    case "list":
                        getAllEmployees().forEach(System.out::println);
                        break;
                    case "findById":
                        var employee = findEmployeeById(Long.parseLong(promptFor(in, "id")));
                        System.out.println(employee);
                        break;
                    case "findByLastName":
                        findEmployeeByLastName(promptFor(in, "lastName")).forEach(System.out::println);
                        break;
                    case "insert":
                        saveEmployee(
                                new Employee(
                                        promptFor(in, "firstName"),
                                        promptFor(in, "lastName"),
                                        LocalDate.parse(promptFor(in, "dob (dd.mm.yyyy)"), formatter)));
                        break;
                    case "update":
                        boolean success = updateEmployee(Integer.parseInt(promptFor(in, "id")),
                                promptFor(in, "firstName"),
                                promptFor(in, "lastName"),
                                LocalDate.parse(promptFor(in, "dob (dd.mm.yyyy)"), formatter));
                        System.out.println(success ? "updated" : "employee not found");
                        break;
                    case "delete":
                        success = deleteEmployee(Integer.parseInt(promptFor(in, "id")));
                        System.out.println(success ? "deleted" : "employee not found");
                        break;
                    default:
                        System.out.println("ERROR: invalid command");
                        break;
                }

                System.out.println(availCmds);
                userCmd = promptFor(in, "");
            } // while

        } // try
        catch (Exception ex) {
            ex.printStackTrace();
        } // catch
        finally {
            HibernateUtil.closeSessionFactory();
        }
    }
}
