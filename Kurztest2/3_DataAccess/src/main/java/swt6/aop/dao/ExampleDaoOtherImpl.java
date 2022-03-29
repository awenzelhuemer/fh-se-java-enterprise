package swt6.swt6.aop.dao;

import org.springframework.stereotype.Repository;

@Repository
@DaoQualifier(DaoQualifier.Type.Cool)
public class ExampleDaoOtherImpl implements ExampleDao {
    @Override
    public void makeSomething() {
        System.out.println("ExampleDaoOtherImpl.makeSomething");
    }
}
