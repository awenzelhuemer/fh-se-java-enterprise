package swt6.spring.worklog.test;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.SQLException;
import javax.sql.DataSource;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import swt6.util.DbScriptRunner;
import static swt6.util.PrintUtil.printSeparator;
import static swt6.util.PrintUtil.printTitle;

public class DbTest {

  private static void createSchema(DataSource ds, String ddlScript) {
    try {
      DbScriptRunner scriptRunner = new DbScriptRunner(ds.getConnection());
      InputStream is = DbTest.class.getClassLoader().getResourceAsStream(ddlScript);
      if (is == null)
        throw new IllegalArgumentException(String.format("File %s not found in classpath.", ddlScript));
      scriptRunner.runScript(new InputStreamReader(is));
    }
    catch (SQLException | IOException e) {
      e.printStackTrace();
      return;
    }
  }

  private static void testJdbc() {

		try (AbstractApplicationContext factory =
			new ClassPathXmlApplicationContext(
				"swt6/spring/worklog/test/applicationContext-jdbc.xml")) {

      printTitle("create schema", 60, '-');
			createSchema(factory.getBean("dataSource", DataSource.class),
                                   "swt6/spring/worklog/test/CreateWorklogDbSchema.sql");

			//
			// get reference to implementation of EmployeeDao
			//

		}
  }

  @SuppressWarnings("unused")
  private static void testJpa() {
    try (AbstractApplicationContext factory = new ClassPathXmlApplicationContext(
        "swt6/spring/worklog/test/applicationContext-jpa1.xml")) {

      //
      // Insert your test code here
      //
    }
  }

  @SuppressWarnings("unused")
  private static void testSpringData() {
    try (AbstractApplicationContext factory = new ClassPathXmlApplicationContext(
        "swt6/spring/worklog/test/applicationContext-jpa1.xml")) {
				
			//
			// Insert your test code here
			//
		}
  }

  public static void main(String[] args) {

  	printSeparator(60); printTitle("testJDBC", 60); printSeparator(60);
    testJdbc();

//  	printSeparator(60); printTitle("testJpa", 60); printSeparator(60);
//    testJpa();

//  	printSeparator(60); printTitle("testSpringData", 60); printSeparator(60);
//    testSpringData();
  }
}
