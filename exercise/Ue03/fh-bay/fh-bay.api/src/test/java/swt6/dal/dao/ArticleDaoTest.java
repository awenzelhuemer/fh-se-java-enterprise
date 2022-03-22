package swt6.dal.dao;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import swt6.dal.domain.Address;
import swt6.dal.domain.Article;
import swt6.dto.BidStatus;
import swt6.dal.domain.Customer;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@Rollback
public class ArticleDaoTest {

    @Autowired
    private CustomerDao customerDao;

    @Autowired
    private ArticleDao articleDao;

    @Test
    public void insert_withValidData_insertsArticle() {
        Customer seller = new Customer("Andreas", "Wenzelhuemer",
                new Address("4070", "Pupping", "Waschpoint 10"),
                new Address("4070", "Pupping", "Waschpoint 10"));

        Customer buyer = new Customer("Max", "Mustermann",
                new Address("4070", "Pupping", "Waschpoint 10"),
                new Address("4070", "Pupping", "Waschpoint 10")
        );

        seller = customerDao.save(seller);
        buyer = customerDao.save(buyer);

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
                BidStatus.Sold
        );

        article = articleDao.save(article);

        var result = articleDao.findById(article.getId());
        assertNotNull(result.orElse(null));
    }

    @Test
    public void insert_withoutBuyerAndBidder_insertsArticle() {
        Customer seller = new Customer("Andreas", "Wenzelhuemer",
                new Address("4070", "Pupping", "Waschpoint 10"),
                new Address("4070", "Pupping", "Waschpoint 10"));

        seller = customerDao.save(seller);

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
                BidStatus.Sold
        );

        article = articleDao.save(article);

        var result = articleDao.findById(article.getId());
        assertTrue(result.isPresent());
    }

    @Test
    public void delete_withExistingArticle_deletesArticle() {
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
                BidStatus.Sold
        );

        article = articleDao.save(article);
        articleDao.delete(article);

        var result = articleDao.findById(article.getId());
        assertTrue(result.isEmpty());
    }

    @Test
    public void delete_withNoExistingArticle_throwsException() {
        assertThrows(Exception.class, () -> articleDao.deleteById(1L));
    }

    @Test
    public void findById_withNoExistingArticle_returnsNull() {
        assertTrue(articleDao.findById(1L).isEmpty());
    }

    @Test
    public void findById_withExistingArticle_returnsArticle() {
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
                BidStatus.Sold
        );

        article = articleDao.save(article);

        assertNotNull(articleDao.findById(article.getId()).orElse(null));
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
                BidStatus.Sold
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
                BidStatus.Sold
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
                BidStatus.Sold
        );


        article1 = articleDao.save(article1);
        article2 = articleDao.save(article2);
        article3 = articleDao.save(article3);

        var result = articleDao.findByNameOrDescriptionContains("%TV%");
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
                BidStatus.Sold
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
                BidStatus.Sold
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
                BidStatus.Sold
        );


        article1 = articleDao.save(article1);
        article2 = articleDao.save(article2);
        article3 = articleDao.save(article3);

        var result = articleDao.findByNameOrDescriptionContains("Random search string");
        assertEquals(0, result.size());
    }

    @Test
    public void findBySeller_withData_returnsArticles() {

        Customer customer = new Customer("Andreas", "Wenzelhuemer",
                new Address("4070", "Pupping", "Waschpoint 10"),
                new Address("4070", "Pupping", "Waschpoint 10"));

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
                BidStatus.Sold
        );
        customer = customerDao.save(customer);
        article = articleDao.save(article);

        var articles = articleDao.findBySeller(customer);
        assertEquals(1, articles.size());
    }

    @Test
    public void findByBidder_withData_returnsArticles() {

        Customer customer = new Customer("Andreas", "Wenzelhuemer",
                new Address("4070", "Pupping", "Waschpoint 10"),
                new Address("4070", "Pupping", "Waschpoint 10"));

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
                BidStatus.Sold
        );

        customer = customerDao.save(customer);
        article = articleDao.save(article);

        assertEquals(1, articleDao.findByBidder(customer).size());
    }

    @Test
    public void findByBuyer_withData_returnsArticles() {

        Customer customer = new Customer("Andreas", "Wenzelhuemer",
                new Address("4070", "Pupping", "Waschpoint 10"),
                new Address("4070", "Pupping", "Waschpoint 10"));

        customer = customerDao.save(customer);

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
                BidStatus.Sold
        );

        article = articleDao.save(article);

        assertEquals(1, articleDao.findByBuyer(customer).size());
    }
}
