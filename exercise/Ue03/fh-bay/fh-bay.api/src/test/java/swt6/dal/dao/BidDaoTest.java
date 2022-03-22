package swt6.dal.dao;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import swt6.dal.domain.*;
import swt6.dto.BidStatus;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ActiveProfiles(profiles = "test")
@DataJpaTest
@Rollback
public class BidDaoTest {

    @Autowired
    private CustomerDao customerDao;

    @Autowired
    private ArticleDao articleDao;

    @Autowired
    private BidDao bidDao;

    @Test
    public void insert_withValidData_insertsBid() {
        Customer seller = new Customer("Andreas", "Wenzelhuemer",
                new Address("4070", "Pupping", "Waschpoint 10"),
                new Address("4070", "Pupping", "Waschpoint 10"));

        Customer bidder = new Customer("Max", "Mustermann",
                new Address("4070", "Pupping", "Waschpoint 10"),
                new Address("4070", "Pupping", "Waschpoint 10")
        );

        seller = customerDao.save(seller);
        bidder = customerDao.save(bidder);

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
                BidStatus.Sold
        );

        article = articleDao.save(article);

        var result = articleDao.findById(article.getId());
        assertNotNull(result);

        bidDao.save(new Bid(23.50, LocalDateTime.now(), bidder, article));
        bidDao.save(new Bid(40.23, LocalDateTime.now(), bidder, article));

        assertEquals(2, bidDao.findAll().size());
    }

    @Test
    public void delete_withExistingBid_deletesBid() {
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

        Customer bidder = new Customer("Max", "Mustermann",
                new Address("4070", "Pupping", "Waschpoint 10"),
                new Address("4070", "Pupping", "Waschpoint 10")
        );

        var bid = new Bid(23.50, LocalDateTime.now(), bidder, article);

        bid = bidDao.save(bid);

        bidDao.delete(bid);

        assertNull(bidDao.findById(bid.getId()).orElse(null));
    }

    @Test
    public void delete_withNoExistingBid_throwsException() {
        assertThrows(Exception.class, () -> bidDao.deleteById(1L));
    }

    @Test
    public void findById_withNoExistingBid_returnsNull() {
       assertTrue(bidDao.findById(1L).isEmpty());
    }

    @Test
    public void findById_withExistingBid_returnsBid() {
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
                BidStatus.Sold
        );

        article = articleDao.save(article);

        var bid = new Bid(23.50, LocalDateTime.now(), bidder, article);
        bid = bidDao.save(bid);

        assertNotNull(bidDao.findById(bid.getId()).orElse(null));
    }

    @Test
    public void findHighestBid_returnsBidWithHighestAmount() {
        Customer bidder = new Customer("Max", "Mustermann",
                new Address("4070", "Pupping", "Waschpoint 10"),
                new Address("4070", "Pupping", "Waschpoint 10")
        );

        bidder = customerDao.save(bidder);

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

        var bid1 = new Bid(23.50, LocalDateTime.now(), bidder, article);
        var bid2 = new Bid(50.0, LocalDateTime.now(), bidder, article);
        var bid3 = new Bid(72.50, LocalDateTime.now(), bidder, article);
        bid1 = bidDao.save(bid1);
        bid2 = bidDao.save(bid2);
        bid3 = bidDao.save(bid3);

        var resultBid = bidDao.findFirstByArticleOrderByAmountDesc(article).orElse(null);

        assertEquals(bid3, resultBid);
    }

    @Test
    public void findHighestBid_withoutBids_returnsBidWithHighestAmount() {
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

        var resultBid = bidDao.findFirstByArticleOrderByAmountDesc(article);

        assertTrue(resultBid.isEmpty());
    }
}
