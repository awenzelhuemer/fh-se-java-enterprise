package swt6.dal.dao;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import swt6.dal.domain.Address;
import swt6.dal.domain.Customer;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@Rollback
public class CustomerDaoTest {

    @Autowired
    private CustomerDao customerDao;

    @Test
    public void insert_withValidData_insertsCustomer() {
        Customer customer = new Customer("Andreas", "Wenzelhuemer",
                new Address("4070", "Pupping", "Waschpoint 10"),
                new Address("4070", "Pupping", "Waschpoint 10"));

        customer = customerDao.save(customer);

        assertNotNull(customerDao.findById(customer.getId()).orElse(null));
    }

    @Test
    public void delete_withExistingCustomer_deletesCustomer() {
        Customer customer = new Customer("Andreas", "Wenzelhuemer",
                new Address("4070", "Pupping", "Waschpoint 10"),
                new Address("4070", "Pupping", "Waschpoint 10"));

        customer = customerDao.save(customer);

        customerDao.delete(customer);

        assertNull(customerDao.findById(customer.getId()).orElse(null));
    }

    @Test
    public void delete_withNoExistingCustomer_throwsException() {
        assertThrows(Exception.class, () -> customerDao.deleteById(1L));
    }

    @Test
    public void findById_withNoExistingCustomer_returnsNull() {
        assertNull(customerDao.findById(1L).orElse(null));
    }

    @Test
    public void findById_withExistingCustomer_returnsCustomer() {
        Customer customer = new Customer("Max", "Mustermann",
                new Address("4070", "Pupping", "Waschpoint 10"),
                new Address("4070", "Pupping", "Waschpoint 10")
        );

        customer = customerDao.save(customer);

        assertNotNull(customerDao.findById(customer.getId()));
    }
}
