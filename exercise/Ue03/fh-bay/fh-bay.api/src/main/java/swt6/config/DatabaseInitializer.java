package swt6.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import swt6.dal.domain.Address;
import swt6.dal.domain.Article;
import swt6.dal.domain.Customer;
import swt6.dto.BidStatus;
import swt6.logic.ArticleLogic;
import swt6.logic.CustomerLogic;

import java.time.LocalDateTime;

@Component
@Profile("dev")
public class DatabaseInitializer implements CommandLineRunner {

    private final Logger logger = LoggerFactory.getLogger(DatabaseInitializer.class);

    @Autowired
    private CustomerLogic customerLogic;

    @Autowired
    private ArticleLogic articleLogic;

    @Override
    public void run(String... args) throws Exception {
        logger.info("Seeding database ...");
        Customer customer1 = new Customer(
                "Andreas",
                "Wenzelhuemer",
                new Address("4232", "Hagenberg im Mühlkreis", "Softwarepark 23"),
                new Address("4232", "Hagenberg im Mühlkreis", "Softwarepark 23"));

        Customer customer2 = new Customer(
                "Jakob",
                "Willminger",
                new Address("4070", "Pupping", "Waschpoint 10"),
                new Address("4070", "Pupping", "Waschpoint 10"));

        customerLogic.insert(customer1);
        customerLogic.insert(customer2);

        Article article1 = new Article(
                "Border Collie",
                "Little pet dog",
                23.0,
                0,
                LocalDateTime.now(),
                LocalDateTime.now().plusHours(6),
                customer1,
                null,
                null,
                BidStatus.Offered
        );

        Article article2 = new Article(
                "Border Terrier",
                "Little pet dog",
                50.0,
                0,
                LocalDateTime.now(),
                LocalDateTime.now().plusHours(6),
                customer2,
                null,
                null,
                BidStatus.Offered
        );

        Article article3 = new Article(
                "Boykin Spaniel",
                "Little pet dog",
                45.23,
                0,
                LocalDateTime.now().minusHours(-6),
                LocalDateTime.now(),
                customer2,
                null,
                null,
                BidStatus.Offered
        );

        articleLogic.insert(article1);
        articleLogic.insert(article2);
        articleLogic.insert(article3);
    }
}
