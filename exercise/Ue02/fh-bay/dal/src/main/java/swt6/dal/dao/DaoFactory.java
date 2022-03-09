package swt6.dal.dao;

import swt6.dal.dao.impl.PaymentDaoImpl;

public class DaoFactory {

    private static PaymentDao customerDao;

    public static PaymentDao getCustomerDao() {
        if(customerDao == null) {
            return new PaymentDaoImpl();
        }
        return customerDao;
    }
}
