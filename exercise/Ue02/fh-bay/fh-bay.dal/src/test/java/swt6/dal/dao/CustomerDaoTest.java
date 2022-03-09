package swt6.dal.dao;

import org.junit.jupiter.api.Test;
import swt6.dal.domain.*;

import static org.junit.jupiter.api.Assertions.*;

public class CustomerDaoTest extends BaseTest {

    @Test
    public void insert_withValidData_insertsCustomer() {

        CustomerDao customerDao = DaoFactory.getCustomerDao();

        Customer customer = new Customer("Andreas", "Wenzelhuemer",
                new Address("4070", "Pupping", "Waschpoint 10"),
                new Address("4070", "Pupping", "Waschpoint 10"));

        customer = customerDao.insert(customer);

        assertNotNull(customerDao.findById(customer.getId()));
    }

    @Test
    public void delete_withExistingCustomer_deletesCustomer() {
        CustomerDao customerDao = DaoFactory.getCustomerDao();

        Customer customer = new Customer("Andreas", "Wenzelhuemer",
                new Address("4070", "Pupping", "Waschpoint 10"),
                new Address("4070", "Pupping", "Waschpoint 10"));

        customer = customerDao.insert(customer);

        customerDao.delete(customer.getId());

        var result = customerDao.findById(customer.getId());
        assertNull(result);
    }

    @Test
    public void delete_withNoExistingCustomer_throwsException() {
        CustomerDao dao = DaoFactory.getCustomerDao();
        assertThrows(Exception.class, () -> dao.delete(1));
    }

    @Test
    public void findById_withNoExistingCustomer_returnsNull() {
        CustomerDao dao = DaoFactory.getCustomerDao();
        assertNull(dao.findById(1));
    }

    @Test
    public void findById_withExistingCustomer_returnsCustomer() {

        CustomerDao customerDao = DaoFactory.getCustomerDao();

        Customer customer = new Customer("Max", "Mustermann",
                new Address("4070", "Pupping", "Waschpoint 10"),
                new Address("4070", "Pupping", "Waschpoint 10")
        );

        customer = customerDao.insert(customer);

        assertNotNull(customerDao.findById(customer.getId()));
    }
}
