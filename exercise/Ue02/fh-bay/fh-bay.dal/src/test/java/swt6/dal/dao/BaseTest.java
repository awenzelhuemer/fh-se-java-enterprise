package swt6.dal.dao;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import swt6.dal.util.JpaUtil;

public abstract class BaseTest {
    @BeforeAll
    public static void initAll() {
        JpaUtil.getEntityManagerFactory();
    }

    @AfterAll
    public static void tearDownAll() {
        JpaUtil.closeEntityManagerFactory();
    }

    @AfterEach
    public void tearDown() {
        JpaUtil.rollback();
    }
}
