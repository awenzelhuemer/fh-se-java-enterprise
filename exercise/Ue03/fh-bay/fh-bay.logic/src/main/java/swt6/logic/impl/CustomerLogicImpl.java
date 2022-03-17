package swt6.logic.impl;

import swt6.dal.dao.CustomerDao;
import swt6.dal.dao.DaoFactory;
import swt6.dal.domain.Customer;
import swt6.dal.util.JpaUtil;
import swt6.logic.CustomerLogic;

import java.util.List;
import java.util.Optional;

public class CustomerLogicImpl implements CustomerLogic {

    // TODO Use spring di
    private CustomerDao getCustomerDao() {
        return DaoFactory.getCustomerDao();
    }

    @Override
    public Optional<Customer> findById(long id) {
        var customer = getCustomerDao().findById(id);

        JpaUtil.commit();

        return customer;
    }

    @Override
    public Customer insert(Customer customer) {
        customer = getCustomerDao().insert(customer);

        JpaUtil.commit();

        return customer;
    }

    @Override
    public List<Customer> findAll() {
        var customers = getCustomerDao().findAll();

        JpaUtil.commit();

        return customers;
    }
}
