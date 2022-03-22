package swt6.spring.worklog.dao.jpa;

import org.springframework.stereotype.Repository;
import swt6.spring.worklog.dao.EmployeeDao;
import swt6.spring.worklog.domain.Employee;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Repository
public class EmployeeDaoJpa implements EmployeeDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Optional<Employee> findById(Long id) {
        return Optional.ofNullable(em.find(Employee.class, id));
    }

    @Override
    public List<Employee> findAll() {
        return em.createQuery("select e from Employee e", Employee.class).getResultList();
    }

    @Override
    public void insert(Employee entity) {
        em.persist(entity);
    }

    @Override
    public Employee merge(Employee entity) {
        return em.merge(entity);
    }
}
