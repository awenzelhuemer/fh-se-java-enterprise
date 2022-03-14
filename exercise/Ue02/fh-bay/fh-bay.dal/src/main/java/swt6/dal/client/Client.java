package swt6.dal.client;

import swt6.dal.dao.DaoFactory;
import swt6.dal.domain.*;
import swt6.dal.util.JpaUtil;

import java.time.LocalDateTime;

public class Client {
    public static void main(String[] args) {
        System.out.println("---- create schema ----");
        JpaUtil.getEntityManagerFactory();
        Customer customer1;
        Customer customer2;
        try {
            System.out.println("---- Add two customer ----");
            customer1 = new Customer(
                                "Andreas",
                                "Wenzelhuemer",
                                new Address("4070", "Pupping", "Waschpoint 10"),
                                new Address("4070", "Pupping", "Waschpoint 10"));

            customer2 = new Customer(
                    "Alex",
                    "Händel",
                    new Address("4070", "Pupping", "Waschpoint 32"),
                    new Address("4070", "Pupping", "Waschpoint 32"));

            var customerDao = DaoFactory.getCustomerDao();

            customer1 = customerDao.insert(customer1);
            System.out.println("Customer 1: " + customer1);
            customer2 = customerDao.insert(customer2);
            System.out.println("Customer 1: " + customer2);

            JpaUtil.commit();
        } catch (Exception ex) {
            JpaUtil.rollback();
            throw ex;
        }

        try {
            System.out.println("---- Add payment (credit card) ----");
            var customerDao = DaoFactory.getCustomerDao();
            var payment =  new CreditCardPayment("Andreas Wenzelhuemer", "32121", 2, 24);
            customer1.addPayment(payment);
            customerDao.update(customer1);
            System.out.println(payment);

            JpaUtil.commit();
        } catch (Exception ex) {
            JpaUtil.rollback();
            throw ex;
        }

        Category category;

        try {
            System.out.println("---- Add categories ----");
            var categoryDao = DaoFactory.getCategoryDao();
            category = new Category("Jackets", new Category("Clothes", null));
            category = categoryDao.insert(category);
            System.out.println(category);

            JpaUtil.commit();
        } catch (Exception ex) {
            JpaUtil.rollback();
            throw ex;
        }

        Article article;

        try {
            System.out.println("---- Add article and assign category ----");
            var articleDao = DaoFactory.getArticleDao();
            article = new Article("Softshell-Jacket", "Softshell-Jacket in size L", 69.99, 0, LocalDateTime.now(), LocalDateTime.now().plusHours(4), customer1, null, null, ArticleStatus.Offered);
            article.assignCategory(category);
            article = articleDao.insert(article);
            System.out.println(article);
            System.out.println("Categories: ");
            article.getCategories().forEach(System.out::println);

            JpaUtil.commit();
        } catch (Exception ex) {
            JpaUtil.rollback();
            throw ex;
        }

        try {
            System.out.println("---- Add two bids ----");

            var bidDao = DaoFactory.getBidDao();

            Bid bid1 = new Bid(100.0, LocalDateTime.now(), customer1, article);
            Bid bid2 = new Bid(150.0, LocalDateTime.now(), customer2, article);

            bidDao.insert(bid1);
            bidDao.insert(bid2);
            System.out.println(bid1);
            System.out.println(bid2);

            JpaUtil.commit();
        } catch (Exception ex) {
            JpaUtil.rollback();
            throw ex;
        }

        try {
            System.out.println("---- Finalize article bet ----");

            var articleDao = DaoFactory.getArticleDao();
            article = articleDao.findById(article.getId());

            var finalBid = DaoFactory.getBidDao().findHighestBidByArticle(article);

            article.setBidder(finalBid.getBidder());
            article.setBuyer(finalBid.getBidder());
            article.setFinalPrice(100.01);
            article.setStatus(ArticleStatus.Sold);

            article = articleDao.update(article);

            System.out.println(article);
            System.out.println("Categories: ");
            article.getCategories().forEach(System.out::println);

            JpaUtil.commit();
        } catch (Exception ex) {
            JpaUtil.rollback();
            throw ex;
        }

        JpaUtil.closeEntityManagerFactory();
    }
}