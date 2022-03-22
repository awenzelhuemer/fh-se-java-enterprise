package swt6.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import swt6.Application;
import swt6.dal.domain.Address;
import swt6.dal.domain.Customer;
import swt6.logic.CustomerLogic;

// https://stackoverflow.com/questions/62732605/spring-shell-with-command-line-runner-interface
@Order(-1)
@Component
@Profile("dev")
public class DatabaseInitializer implements CommandLineRunner {

    private final Logger logger = LoggerFactory.getLogger(Application.class);

    @Autowired
    private CustomerLogic customerLogic;

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

        // TODO Add seeding of articles and some example bets
    }
}
