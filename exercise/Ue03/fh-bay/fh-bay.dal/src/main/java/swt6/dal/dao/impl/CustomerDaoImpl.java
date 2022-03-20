package swt6.dal.dao.impl;

import org.springframework.stereotype.Component;
import swt6.dal.dao.CustomerDao;
import swt6.dal.domain.Customer;

@Component
public class CustomerDaoImpl extends BaseDaoImpl<Customer> implements CustomerDao {

    @Override
    protected Class<Customer> getEntityType() {
        return Customer.class;
    }
}
