package swt6.orm.domain.jpa;

import swt6.orm.domain.*;
import swt6.util.JpaUtil;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.ParameterExpression;
import javax.persistence.criteria.Root;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public class WorkLogManager {

    private static void insertEmployee1(Employee employee) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("WorkLogPU");
        EntityManager em = null;
        EntityTransaction tx = null;

        try {
            em = emf.createEntityManager();
            tx = em.getTransaction();
            tx.begin();

            em.persist(employee);

            tx.commit();
        } catch (Exception ex) {
            if (tx != null && tx.isActive()) {
                tx.rollback();
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
            emf.close();
        }
    }

    private static void insertEmployee(Employee employee) {
        try {
            EntityManager em = JpaUtil.getTransactedEntityManager();
            System.out.println(employee.getId());
            em.persist(employee);
            System.out.println(employee.getId());
            JpaUtil.commit();
        } catch (Exception ex) {
            JpaUtil.rollback();
            throw ex;
        }
    }

    private static <T> void insertEntity(T entity) {
        try {
            EntityManager em = JpaUtil.getTransactedEntityManager();

            em.persist(entity);

            JpaUtil.commit();
        } catch (Exception ex) {
            JpaUtil.rollback();
            throw ex;
        }
    }

    private static <T> T saveEntity(T entity) {
        try {
            EntityManager em = JpaUtil.getTransactedEntityManager();

            entity = em.merge(entity);

            JpaUtil.commit();

            return entity;
        } catch (Exception ex) {
            JpaUtil.rollback();
            throw ex;
        }
    }

    private static <T> T updateEntity(Serializable id, T entity, Class<T> entityType) {
        T storedEntity = null;

        try {
            EntityManager em = JpaUtil.getTransactedEntityManager();

            if (id == null) {
                throw new IllegalArgumentException("Id needs to be specified.");
            }

            storedEntity = em.find(entityType, id);
            if (storedEntity == null) {
                throw new RuntimeException("Entity does not exist.");
            }

            storedEntity = em.merge(entity);

            JpaUtil.commit();
        } catch (Exception ex) {
            JpaUtil.rollback();
            throw ex;
        }
        return storedEntity;
    }

    private static void listEmployees() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        try {
            EntityManager em = JpaUtil.getTransactedEntityManager();

            List<Employee> employees = em.createQuery("select e from Employee e", Employee.class)
                    .getResultList();

            employees.forEach(e -> {
                System.out.println(e);
                if (e.getLogbookEntries().size() > 0) {
                    System.out.println("logbookEntries: ");
                    e.getLogbookEntries().forEach(System.out::println);
                }

                if(e.getProjects().size() > 0) {
                    System.out.println("projects: ");
                    e.getProjects().forEach(System.out::println);
                }
            });

            JpaUtil.commit();
        } catch (Exception ex) {
            JpaUtil.rollback();
            throw ex;
        }
    }

    private static Employee addLogbookEntries(Employee employee, LogbookEntry... entries) {
        try {
            EntityManager em = JpaUtil.getTransactedEntityManager();

            employee = em.merge(employee);

            for (LogbookEntry entry : entries) {
                entry.attachEmployee(employee);
                // employee.addLogbookEntry(entry);
            }

            // employee = em.merge(employee);

            JpaUtil.commit();
        } catch (Exception ex) {
            JpaUtil.rollback();
            throw ex;
        }
        return employee;
    }

    private static void deleteEmployee(Employee employee) {
        try {
            EntityManager em = JpaUtil.getTransactedEntityManager();

            employee = em.merge(employee);

            for (LogbookEntry entry : new ArrayList<>(employee.getLogbookEntries())) {
                // em.remove(entry);
                employee.removeLogbookEntry(entry);
            }
            em.remove(employee);

        } catch (Exception ex) {
            JpaUtil.rollback();
            throw ex;
        }
    }

    private static void testFetchingStrategies() {

        // prepare: fetch valid ids for employee and logbookentry
        Long entryId = null;
        Long employeeId = null;

        try {
            EntityManager em = JpaUtil.getTransactedEntityManager();

            Optional<LogbookEntry> entry =
                    em.createQuery("select le from LogbookEntry le", LogbookEntry.class)
                            .setMaxResults(1)
                            .getResultList().stream().findAny();
            if (entry.isEmpty()) return;
            entryId = entry.get().getId();

            Optional<Employee> empl =
                    em.createQuery("select e from Employee e", Employee.class)
                            .setMaxResults(1)
                            .getResultList().stream().findAny();
            if (empl.isEmpty()) return;
            employeeId = empl.get().getId();

            JpaUtil.commit();
        } catch (Exception e) {
            JpaUtil.rollback();
            throw e;
        }

        System.out.println("############################################");

        try {
            EntityManager em = JpaUtil.getTransactedEntityManager();

            System.out.println("###> Fetching LogbookEntry ...");
            LogbookEntry entry = em.find(LogbookEntry.class, entryId);
            System.out.println("###> Fetched LogbookEntry");
            Employee employee1 = entry.getEmployee();
            System.out.println("###> Fetched associated Employee");
            System.out.println(employee1);
            System.out.println("###> Access associated Employee");

            JpaUtil.commit();
        } catch (Exception e) {
            JpaUtil.rollback();
            throw e;
        }

        System.out.println("############################################");

        try {
            EntityManager em = JpaUtil.getTransactedEntityManager();

            System.out.println("###> Fetching Employee ...");
            Employee employee2 = em.find(Employee.class, employeeId);
            System.out.println("###> Fetched Employee ...");
            Set<LogbookEntry> entries = employee2.getLogbookEntries();
            System.out.println("###> Fetched associated entries");

            entries.forEach(System.out::println);
            System.out.println("###> Accessed associated entries");

            JpaUtil.commit();
        } catch (Exception e) {
            JpaUtil.rollback();
            throw e;
        }

        System.out.println("############################################");
    }

    private static void listEntriesOfEmployee(Employee employee) {
        try {
            EntityManager em = JpaUtil.getTransactedEntityManager();

            System.out.printf("logbook entries of employee: %s (%d)%n", employee.getLastName(), employee.getId());

            // TypedQuery<LogbookEntry> query =
            //         em.createQuery("from LogbookEntry where employee.id = " + employee.getId(), LogbookEntry.class);

            // TypedQuery<LogbookEntry> query =
            //         em.createQuery("from LogbookEntry where employee.id = ?1", LogbookEntry.class);
            // query.setParameter(1, employee.getId());

            // TypedQuery<LogbookEntry> query =
            //         em.createQuery("from LogbookEntry where employee.id = :id", LogbookEntry.class);
            // query.setParameter("id", employee.getId());

            TypedQuery<LogbookEntry> query =
                    em.createQuery("from LogbookEntry where employee = :employee", LogbookEntry.class);
            query.setParameter("employee", employee);

            query.getResultList().forEach(System.out::println);

            JpaUtil.commit();
        } catch (Exception ex) {
            JpaUtil.rollback();
            throw ex;
        }
    }

    private static void listEmployeesResidingIn(String zipCode) {
        try {
            EntityManager em = JpaUtil.getTransactedEntityManager();

            TypedQuery<Employee> query = em.createQuery("select e from Employee e where e.address.zipCode = :zipCode", Employee.class);
            query.setParameter("zipCode", zipCode);

            query.getResultList().forEach(System.out::println);

            JpaUtil.commit();
        } catch (Exception ex) {
            JpaUtil.rollback();
            throw ex;
        }
    }

    private static void loadEmployeesWithEntries() {
        try {
            EntityManager em = JpaUtil.getTransactedEntityManager();

            TypedQuery<Employee> query = em.createQuery("select distinct e from Employee e " +
                    "join e.logbookEntries le where le.activity = :activity", Employee.class);
            query.setParameter("activity", "Analysis");

            query.getResultList().forEach(System.out::println);

            query = em.createQuery("select distinct e from Employee e join fetch e.logbookEntries", Employee.class);
            List<Employee> employees = query.getResultList();

            JpaUtil.commit();

            employees.forEach(e -> e.getLogbookEntries().forEach(System.out::println));
        } catch (Exception ex) {
            JpaUtil.rollback();
            throw ex;
        }
    }

    private static Employee addPhones(Employee employee, String... phones) {
        try {
            EntityManager em = JpaUtil.getTransactedEntityManager();

            employee = em.merge(employee);
            for (String phone : phones) {
                employee.addPhone(phone);
            }

            JpaUtil.commit();
        } catch (Exception ex) {
            JpaUtil.rollback();
            throw ex;
        }
        return employee;
    }

    private static void listEntriesOfEmployeesCQ(Employee employee) {
        try {
            EntityManager em = JpaUtil.getTransactedEntityManager();

            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<LogbookEntry> entryCQ = cb.createQuery(LogbookEntry.class);
            Root<LogbookEntry> entry = entryCQ.from(LogbookEntry.class);
            ParameterExpression<Employee> p = cb.parameter(Employee.class);

            // entryCQ.where(cb.equal(entry.get("employee"), p)).select(entry);
            entryCQ.where(cb.equal(entry.get(LogbookEntry_.employee), p)).select(entry);

            TypedQuery<LogbookEntry> entriesOfEmployeeQuery = em.createQuery(entryCQ);
            entriesOfEmployeeQuery.setParameter(p, employee);
            entriesOfEmployeeQuery.getResultList().forEach(System.out::println);

            JpaUtil.commit();
        } catch (Exception ex) {
            JpaUtil.rollback();
            throw ex;
        }
    }

    private static Employee assignToProject(Employee employee, Project project) {
        try {
            EntityManager em = JpaUtil.getTransactedEntityManager();

            employee = em.merge(employee);
            employee.assignToProject(project);

            JpaUtil.commit();
        } catch (Exception ex) {
            JpaUtil.rollback();
            throw ex;
        }
        return employee;
    }

    public static void main(String[] args) {
        try {
            System.out.println("---- create schema ----");
            JpaUtil.getEntityManagerFactory();

            PermanentEmployee pe = new PermanentEmployee("Max", "Mustermann", LocalDate.of(2001, 1, 1));
            pe.setSalary(5000);

            TemporaryEmployee te = new TemporaryEmployee("Anna", "Musterfrau", LocalDate.of(2002, 2, 2));
            te.setRenter("Microsoft");
            te.setHourlyRate(80.0);
            te.setStartDate(LocalDate.of(2022, 1, 1));
            te.setEndDate(LocalDate.of(2022, 2, 1));
            Employee employee1 = pe;
            Employee employee2 = te;
            employee1.setAddress(new Address("4040", "Linz", "Hauptstraße 1"));
            employee2.setAddress(new Address("4040", "Linz", "Hauptstraße 2"));

            LogbookEntry entry1 = new LogbookEntry("Analysis",
                    LocalDateTime.of(2022, 2, 1, 13, 0),
                    LocalDateTime.of(2022, 2, 1, 15, 0));

            LogbookEntry entry2 = new LogbookEntry("Design",
                    LocalDateTime.of(2022, 2, 2, 9, 0),
                    LocalDateTime.of(2022, 2, 2, 15, 0));

            LogbookEntry entry3 = new LogbookEntry("Implementation",
                    LocalDateTime.of(2022, 2, 3, 8, 0),
                    LocalDateTime.of(2022, 2, 3, 14, 0));

            System.out.println("---- insertEmployee ----");
            employee1 = saveEntity(employee1);
            employee1.setFirstName("Maximilian");
            saveEntity(employee1);
            employee2 = saveEntity(employee2);

            System.out.println("---- listEmployees ----");
            listEmployees();

            System.out.println("---- updateEntity ----");
            employee1.setLastName("Testnachname");
            employee1 = updateEntity(employee1.getId(), employee1, Employee.class);

            System.out.println("---- listEmployees ----");
            listEmployees();

            System.out.println("---- addLogbookEntries ----");
            // entry1 = saveEntity(entry1);
            // entry2 = saveEntity(entry2);
            // entry3 = saveEntity(entry3);
            employee1 = addLogbookEntries(employee1, entry1, entry2);
            employee2 = addLogbookEntries(employee2, entry3);

            System.out.println("---- listEmployees ----");
            listEmployees();

            // System.out.println("---- deleteEmployees ----");
            // deleteEmployee(employee2);

            System.out.println("---- listEmployees ----");
            listEmployees();

            System.out.println("---- testFetchingStrategies ----");
            testFetchingStrategies();

            System.out.println("---- listEntriesOfEmployee ----");
            listEntriesOfEmployee(employee1);

            System.out.println("---- listEmployeesResidingIn ----");
            listEmployeesResidingIn("4040");

            System.out.println("---- loadEmployeesWithEntries ----");
            loadEmployeesWithEntries();

            System.out.println("---- listEntriesOfEmployeesCQ ----");
            listEntriesOfEmployeesCQ(employee1);

            System.out.println("---- addPhones ----");
            employee1 = addPhones(employee1, "12423", "+43 688 8657899");

            System.out.println("---- assignProjects ----");
            Project p1 = new Project("SWT6");
            employee1 = assignToProject(employee1, p1);

            System.out.println("---- listEmployees ----");
            listEmployees();
        } finally {
            JpaUtil.closeEntityManagerFactory();
        }
    }
}
