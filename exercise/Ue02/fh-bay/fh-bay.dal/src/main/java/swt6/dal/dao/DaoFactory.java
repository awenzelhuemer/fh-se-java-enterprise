package swt6.dal.dao;

import swt6.dal.dao.impl.*;

public class DaoFactory {

    private static CustomerDao customerDao;
    private static BidDao bidDao;
    private static ArticleDao articleDao;
    private static PaymentDao paymentDao;
    private static  CategoryDao categoryDao;

    public static PaymentDao getPaymentDao() {
        if(paymentDao == null) {
            paymentDao = new PaymentDaoImpl();
        }
        return paymentDao;
    }

    public static CustomerDao getCustomerDao() {
        if(customerDao == null) {
            customerDao = new CustomerDaoImpl();
        }
        return customerDao;
    }

    public static BidDao getBidDao() {
        if(bidDao == null) {
            bidDao = new BidDaoImpl();
        }
        return bidDao;
    }

    public static ArticleDao getArticleDao() {
        if(articleDao == null) {
            articleDao = new ArticleDaoImpl();
        }
        return articleDao;
    }

    public static CategoryDao getCategoryDao() {
        if(categoryDao == null) {
            categoryDao = new CategoryDaoImpl();
        }
        return categoryDao;
    }
}
