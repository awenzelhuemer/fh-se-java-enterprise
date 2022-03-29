package swt6.swt6.aop;

import org.springframework.stereotype.Component;
import swt6.swt6.aop.dao.DaoQualifier;
import swt6.swt6.aop.dao.ExampleDao;

@Component("testClass")
public class TestClass {

    private final ExampleDao exampleDao;

    public TestClass(@DaoQualifier(DaoQualifier.Type.Cool) ExampleDao exampleDao) {
        this.exampleDao = exampleDao;
    }

    public void doSomethingService() {
        exampleDao.makeSomething();
    }

}
