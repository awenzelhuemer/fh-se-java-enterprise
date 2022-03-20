package swt6.logic.impl;

import swt6.dal.dao.CustomerDao;
import swt6.dal.domain.Customer;
import swt6.dal.util.JpaUtil;
import swt6.logic.CustomerLogic;

import javax.inject.Inject;
import java.util.List;
import java.util.Optional;

public class CustomerLogicImpl implements CustomerLogic {

    @Inject
    private CustomerDao customerDao;

    public CustomerLogicImpl() {
    }

    @Override
    public Optional<Customer> findById(long id) {
        var customer = customerDao.findById(id);

        JpaUtil.commit();

        return customer;
    }

    @Override
    public Customer insert(Customer customer) {
        customer = customerDao.insert(customer);

        JpaUtil.commit();

        return customer;
    }

    @Override
    public List<Customer> findAll() {
        var customers = customerDao.findAll();

        JpaUtil.commit();

        return customers;
    }
}
