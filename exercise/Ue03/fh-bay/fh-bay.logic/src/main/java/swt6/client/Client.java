package swt6.client;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;
import swt6.dal.domain.Address;
import swt6.dal.domain.Customer;
import swt6.logic.CustomerLogic;
import swt6.logic.LogicConfig;

public class Client {

    public static void main(String[] args) {
        try(AbstractApplicationContext context = new AnnotationConfigApplicationContext(LogicConfig.class)) {
            CustomerLogic customerLogic = context.getBean(CustomerLogic.class);
            customerLogic.insert(new Customer("Eva", "Wenzelhuemer",
                    new Address("4070", "Pupping", "Waschpoint 10"),
                    new Address("4070", "Pupping", "Waschpoint 10")));
            customerLogic.findAll().forEach(System.out::println);
        }
    }
}
