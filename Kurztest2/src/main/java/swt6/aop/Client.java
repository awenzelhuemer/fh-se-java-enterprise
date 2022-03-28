package swt6.aop;

import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Client {
    public static void main(String[] args) {
        try(AbstractApplicationContext context = new ClassPathXmlApplicationContext("swt6/aop/application-context.xml")) {
            var testClass = context.getBean("testClass", TestClass.class);
            testClass.doSomethingService();
        }
    }
}
