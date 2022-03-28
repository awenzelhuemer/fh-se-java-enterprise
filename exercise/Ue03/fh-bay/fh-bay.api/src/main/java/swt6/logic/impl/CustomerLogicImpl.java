package swt6.logic.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import swt6.dal.dao.CustomerDao;
import swt6.dal.domain.Customer;
import swt6.logic.CustomerLogic;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CustomerLogicImpl implements CustomerLogic {

    private final CustomerDao customerDao;

    public CustomerLogicImpl(CustomerDao customerDao) {
        this.customerDao = customerDao;
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<Customer> findById(long id) {
        return customerDao.findById(id);
    }

    @Override
    public Customer insert(Customer customer) {
        return customerDao.save(customer);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Customer> findAll() {
        return customerDao.findAll();
    }

    @Override
    public Optional<Customer> findByFirstnameAndLastname(String firstName, String lastName) {
        return customerDao.findByFirstNameAndLastName(firstName, lastName);
    }
}
