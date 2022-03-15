package swt6.spring.basics.hello;

import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class GreetingClient {
    public static void main(String[] args) {
        try (AbstractApplicationContext factory = new ClassPathXmlApplicationContext("swt6/spring/basics/hello/greetingService.xml")) {
            GreetingService bean = (GreetingService) factory.getBean("greetingService");
            bean.sayHello();

            GreetingService bean2 = (GreetingService) factory.getBean("greetingService");
            System.out.format("bean == bean2: %b", bean == bean2);
        }
    }
}
