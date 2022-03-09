package swt6.dal.dao;

import org.junit.jupiter.api.Test;
import swt6.dal.domain.*;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

public class BidDaoTest extends BaseTest {

    @Test
    public void insert_withValidData_insertsBid() {

        CustomerDao customerDao = DaoFactory.getCustomerDao();

        Customer seller = new Customer("Andreas", "Wenzelhuemer",
                new Address("4070", "Pupping", "Waschpoint 10"),
                new Address("4070", "Pupping", "Waschpoint 10"));

        Customer bidder = new Customer("Max", "Mustermann",
                new Address("4070", "Pupping", "Waschpoint 10"),
                new Address("4070", "Pupping", "Waschpoint 10")
        );

        seller = customerDao.insert(seller);
        bidder = customerDao.insert(bidder);

        ArticleDao articleDao = DaoFactory.getArticleDao();

        Article article = new Article(
                "Testname",
                "Testdescription",
                23.0,
                40.23,
                LocalDateTime.of(2021, 3, 10, 10, 0),
                LocalDateTime.of(2021, 3, 10, 15, 0),
                seller,
                bidder,
                bidder,
                ArticleStatus.Sold
        );

        article = articleDao.insert(article);

        var result = articleDao.findById(article.getId());
        assertNotNull(result);

        BidDao bidDao = DaoFactory.getBidDao();

        bidDao.insert(new Bid(23.50, LocalDateTime.now(), bidder, article));
        bidDao.insert(new Bid(40.23, LocalDateTime.now(), bidder, article));

        assertEquals(2, bidDao.findAll().size());
    }

    @Test
    public void delete_withExistingBid_deletesBid() {
        ArticleDao articleDao = DaoFactory.getArticleDao();

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

        article = articleDao.insert(article);

        Customer bidder = new Customer("Max", "Mustermann",
                new Address("4070", "Pupping", "Waschpoint 10"),
                new Address("4070", "Pupping", "Waschpoint 10")
        );

        BidDao bidDao = DaoFactory.getBidDao();
        var bid = new Bid(23.50, LocalDateTime.now(), bidder, article);

        bid = bidDao.insert(bid);

        bidDao.delete(bid.getId());

        var result = bidDao.findById(bid.getId());
        assertNull(result);
    }

    @Test
    public void delete_withNoExistingBid_throwsException() {
        BidDao dao = DaoFactory.getBidDao();
        assertThrows(Exception.class, () -> dao.delete(1));
    }

    @Test
    public void findById_withNoExistingBid_returnsNull() {
        ArticleDao dao = DaoFactory.getArticleDao();
       assertNull(dao.findById(1));
    }

    @Test
    public void findById_withExistingBid_returnsBid() {
        BidDao dao = DaoFactory.getBidDao();
        ArticleDao articleDao = DaoFactory.getArticleDao();

        Customer bidder = new Customer("Max", "Mustermann",
                new Address("4070", "Pupping", "Waschpoint 10"),
                new Address("4070", "Pupping", "Waschpoint 10")
        );

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

        article = articleDao.insert(article);

        var bid = new Bid(23.50, LocalDateTime.now(), bidder, article);
        bid = dao.insert(bid);

        assertNotNull(dao.findById(bid.getId()));
    }

    @Test
    public void findHighestBid_returnsBidWithHighestAmount() {
        BidDao dao = DaoFactory.getBidDao();
        ArticleDao articleDao = DaoFactory.getArticleDao();
        CustomerDao customerDao = DaoFactory.getCustomerDao();

        Customer bidder = new Customer("Max", "Mustermann",
                new Address("4070", "Pupping", "Waschpoint 10"),
                new Address("4070", "Pupping", "Waschpoint 10")
        );

        bidder = customerDao.insert(bidder);

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

        article = articleDao.insert(article);

        var bid1 = new Bid(23.50, LocalDateTime.now(), bidder, article);
        var bid2 = new Bid(50.0, LocalDateTime.now(), bidder, article);
        var bid3 = new Bid(72.50, LocalDateTime.now(), bidder, article);
        bid1 = dao.insert(bid1);
        bid2 = dao.insert(bid2);
        bid3 = dao.insert(bid3);

        var resultBid = dao.findHighestBidByArticle(article);

        assertEquals(bid3, resultBid);
    }

    @Test
    public void findHighestBid_withoutBids_returnsBidWithHighestAmount() {
        BidDao dao = DaoFactory.getBidDao();
        ArticleDao articleDao = DaoFactory.getArticleDao();

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

        article = articleDao.insert(article);

        var resultBid = dao.findHighestBidByArticle(article);

        assertNull(resultBid);
    }
}
