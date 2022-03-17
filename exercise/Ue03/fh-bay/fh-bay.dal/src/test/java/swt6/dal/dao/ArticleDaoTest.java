package swt6.dal.dao;

import org.junit.jupiter.api.Test;
import swt6.dal.domain.Address;
import swt6.dal.domain.Article;
import swt6.dal.domain.ArticleStatus;
import swt6.dal.domain.Customer;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;


public class ArticleDaoTest extends BaseTest {

    @Test
    public void insert_withValidData_insertsArticle() {

        CustomerDao customerDao = DaoFactory.getCustomerDao();

        Customer seller = new Customer("Andreas", "Wenzelhuemer",
                new Address("4070", "Pupping", "Waschpoint 10"),
                new Address("4070", "Pupping", "Waschpoint 10"));

        Customer buyer = new Customer("Max", "Mustermann",
                new Address("4070", "Pupping", "Waschpoint 10"),
                new Address("4070", "Pupping", "Waschpoint 10")
        );

        seller = customerDao.insert(seller);
        buyer = customerDao.insert(buyer);

        ArticleDao dao = DaoFactory.getArticleDao();

        Article article = new Article(
                "Testname",
                "Testdescription",
                23.0,
                40.23,
                LocalDateTime.of(2021, 3, 10, 10, 0),
                LocalDateTime.of(2021, 3, 10, 15, 0),
                seller,
                buyer,
                buyer,
                ArticleStatus.Sold
        );

        article = dao.insert(article);

        var result = dao.findById(article.getId());
        assertNotNull(result.orElse(null));
    }

    @Test
    public void insert_withoutBuyerAndBidder_insertsArticle() {

        CustomerDao customerDao = DaoFactory.getCustomerDao();

        Customer seller = new Customer("Andreas", "Wenzelhuemer",
                new Address("4070", "Pupping", "Waschpoint 10"),
                new Address("4070", "Pupping", "Waschpoint 10"));

        seller = customerDao.insert(seller);

        ArticleDao dao = DaoFactory.getArticleDao();

        Article article = new Article(
                "Testname",
                "Testdescription",
                23.0,
                40.23,
                LocalDateTime.of(2021, 3, 10, 10, 0),
                LocalDateTime.of(2021, 3, 10, 15, 0),
                seller,
                null,
                null,
                ArticleStatus.Sold
        );

        article = dao.insert(article);

        var result = dao.findById(article.getId());
        assertNotNull(result);
    }

    @Test
    public void delete_withExistingArticle_deletesArticle() {
        ArticleDao dao = DaoFactory.getArticleDao();

        Article article = new Article(
                "Testname",
                "Testdescription",
                23.0,
                40.23,
                LocalDateTime.of(2021, 3, 10, 10, 0),
                LocalDateTime.of(2021, 3, 10, 15, 0),
                null,
                null,
                null,
                ArticleStatus.Sold
        );

        article = dao.insert(article);
        dao.delete(article.getId());

        var result = dao.findById(article.getId());
        assertNotNull(result.orElse(null));
    }

    @Test
    public void delete_withNoExistingArticle_throwsException() {
        ArticleDao dao = DaoFactory.getArticleDao();
        assertThrows(Exception.class, () -> dao.delete(1));
    }

    @Test
    public void findById_withNoExistingArticle_returnsNull() {
        ArticleDao dao = DaoFactory.getArticleDao();
        assertNull(dao.findById(1));
    }

    @Test
    public void findById_withExistingArticle_returnsArticle() {
        ArticleDao dao = DaoFactory.getArticleDao();

        Article article = new Article(
                "Testname",
                "Testdescription",
                23.0,
                40.23,
                LocalDateTime.of(2021, 3, 10, 10, 0),
                LocalDateTime.of(2021, 3, 10, 15, 0),
                null,
                null,
                null,
                ArticleStatus.Sold
        );

        article = dao.insert(article);

        assertNotNull(dao.findById(article.getId()).orElse(null));
    }

    @Test
    public void findByTerm_returnsListOfArticles() {

        Article article1 = new Article(
                "65 Zoll Sony TV",
                "New modern OLED TV.",
                23.0,
                40.23,
                LocalDateTime.of(2021, 3, 10, 10, 0),
                LocalDateTime.of(2021, 3, 10, 15, 0),
                null,
                null,
                null,
                ArticleStatus.Sold
        );

        Article article2 = new Article(
                "Samsung TV 55 Zoll",
                "New modern OLED TV.",
                23.0,
                40.23,
                LocalDateTime.of(2021, 3, 10, 10, 0),
                LocalDateTime.of(2021, 3, 10, 15, 0),
                null,
                null,
                null,
                ArticleStatus.Sold
        );

        Article article3 = new Article(
                "24 Zoll Monitor",
                "Good pc monitor with two hdmi ports.",
                23.0,
                40.23,
                LocalDateTime.of(2021, 3, 10, 10, 0),
                LocalDateTime.of(2021, 3, 10, 15, 0),
                null,
                null,
                null,
                ArticleStatus.Sold
        );


        ArticleDao dao = DaoFactory.getArticleDao();
        article1 = dao.insert(article1);
        article2 = dao.insert(article2);
        article3 = dao.insert(article3);

        var result = dao.findByNameAndDescription("TV");
        assertEquals(2, result.size());
    }

    @Test
    public void findByTerm_withoutMatch_returnsEmptyList() {

        Article article1 = new Article(
                "65 Zoll Sony TV",
                "New modern OLED TV.",
                23.0,
                40.23,
                LocalDateTime.of(2021, 3, 10, 10, 0),
                LocalDateTime.of(2021, 3, 10, 15, 0),
                null,
                null,
                null,
                ArticleStatus.Sold
        );

        Article article2 = new Article(
                "Samsung TV 55 Zoll",
                "New modern OLED TV.",
                23.0,
                40.23,
                LocalDateTime.of(2021, 3, 10, 10, 0),
                LocalDateTime.of(2021, 3, 10, 15, 0),
                null,
                null,
                null,
                ArticleStatus.Sold
        );

        Article article3 = new Article(
                "24 Zoll Monitor",
                "Good pc monitor with two hdmi ports.",
                23.0,
                40.23,
                LocalDateTime.of(2021, 3, 10, 10, 0),
                LocalDateTime.of(2021, 3, 10, 15, 0),
                null,
                null,
                null,
                ArticleStatus.Sold
        );


        ArticleDao dao = DaoFactory.getArticleDao();
        article1 = dao.insert(article1);
        article2 = dao.insert(article2);
        article3 = dao.insert(article3);

        var result = dao.findByNameAndDescription("Random search string");
        assertEquals(0, result.size());
    }

    @Test
    public void findBySeller_withData_returnsArticles() {

        Customer customer = new Customer("Andreas", "Wenzelhuemer",
                new Address("4070", "Pupping", "Waschpoint 10"),
                new Address("4070", "Pupping", "Waschpoint 10"));

        customer = DaoFactory.getCustomerDao().insert(customer);

        Article article = new Article(
                "65 Zoll Sony TV",
                "New modern OLED TV.",
                23.0,
                40.23,
                LocalDateTime.of(2021, 3, 10, 10, 0),
                LocalDateTime.of(2021, 3, 10, 15, 0),
                customer,
                null,
                null,
                ArticleStatus.Sold
        );
        var articleDao = DaoFactory.getArticleDao();
        article = articleDao.insert(article);


        assertEquals(1, articleDao.findBySeller(customer).size());
    }

    @Test
    public void findByBidder_withData_returnsArticles() {

        Customer customer = new Customer("Andreas", "Wenzelhuemer",
                new Address("4070", "Pupping", "Waschpoint 10"),
                new Address("4070", "Pupping", "Waschpoint 10"));

        customer = DaoFactory.getCustomerDao().insert(customer);

        Article article = new Article(
                "65 Zoll Sony TV",
                "New modern OLED TV.",
                23.0,
                40.23,
                LocalDateTime.of(2021, 3, 10, 10, 0),
                LocalDateTime.of(2021, 3, 10, 15, 0),
                null,
                null,
                customer,
                ArticleStatus.Sold
        );
        var articleDao = DaoFactory.getArticleDao();
        article = articleDao.insert(article);

        assertEquals(1, articleDao.findByBidder(customer).size());
    }

    @Test
    public void findByBuyer_withData_returnsArticles() {

        Customer customer = new Customer("Andreas", "Wenzelhuemer",
                new Address("4070", "Pupping", "Waschpoint 10"),
                new Address("4070", "Pupping", "Waschpoint 10"));

        customer = DaoFactory.getCustomerDao().insert(customer);

        Article article = new Article(
                "65 Zoll Sony TV",
                "New modern OLED TV.",
                23.0,
                40.23,
                LocalDateTime.of(2021, 3, 10, 10, 0),
                LocalDateTime.of(2021, 3, 10, 15, 0),
                null,
                customer,
                null,
                ArticleStatus.Sold
        );

        var articleDao = DaoFactory.getArticleDao();
        article = articleDao.insert(article);

        assertEquals(1, articleDao.findByBuyer(customer).size());
    }
}
