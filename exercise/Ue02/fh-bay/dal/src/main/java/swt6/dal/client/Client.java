package swt6.dal.client;

import swt6.dal.dao.DaoFactory;
import swt6.dal.util.JpaUtil;

public class Client {
    public static void main(String[] args) {

        System.out.println("---- create schema ----");
        JpaUtil.getEntityManagerFactory();

        try {
            var customerDao = DaoFactory.getCustomerDao();
            customerDao.getById(1);
            JpaUtil.commit();
        } catch (Exception ex) {
            JpaUtil.rollback();
            throw ex;
        }
    }
}