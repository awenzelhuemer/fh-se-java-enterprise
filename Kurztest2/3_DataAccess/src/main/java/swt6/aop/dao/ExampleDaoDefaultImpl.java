package swt6.swt6.aop.dao;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

@Primary
@DaoQualifier(DaoQualifier.Type.Default)
@Repository
public class ExampleDaoDefaultImpl implements ExampleDao {
    @Override
    public void makeSomething() {
        System.out.println("ExampleDaoDefaultImpl.makeSomething");
    }
}
