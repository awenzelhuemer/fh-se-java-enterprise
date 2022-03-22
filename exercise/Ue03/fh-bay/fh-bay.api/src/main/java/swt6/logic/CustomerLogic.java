package swt6.logic;

import swt6.dal.domain.Customer;

import java.util.List;
import java.util.Optional;

public interface CustomerLogic {

    Optional<Customer> findById(long id);

    Customer insert(Customer customer);

    List<Customer> findAll();

    Optional<Customer> findByFirstnameAndLastname(String firstName, String lastName);
}
