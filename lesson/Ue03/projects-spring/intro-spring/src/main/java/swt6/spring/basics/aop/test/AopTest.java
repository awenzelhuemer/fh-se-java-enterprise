package swt6.spring.basics.aop.test;

import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import swt6.spring.basics.aop.advice.TraceOptions;
import swt6.spring.basics.aop.logic.EmployeeIdNotFoundException;
import swt6.spring.basics.aop.logic.WorkLogService;
import swt6.util.PrintUtil;

public class AopTest {

    public static void reflectClass(Class<?> clazz) {
        System.out.println("class=" + clazz.getName());
        Class<?>[] interfaces = clazz.getInterfaces();
        for (Class<?> itf : interfaces) {
            System.out.println("  implements " + itf.getName());
        }
    }

    private static void testAOP(String configFileName) {
        try (AbstractApplicationContext factory = new ClassPathXmlApplicationContext(configFileName)) {
            WorkLogService workLog = factory.getBean("workLog", WorkLogService.class);

            reflectClass(workLog.getClass());

            ((TraceOptions)workLog).enableTracing();

            workLog.findAllEmployees();

            for (int i = 1; i < 5; i++) {
                try {
                    workLog.findEmployeeById((long) i);
                } catch (EmployeeIdNotFoundException e) {
                }
            }
        }
    }

    public static void main(String[] args) {
        PrintUtil.printTitle("testAOP (config based)", 60);
        testAOP("swt6/spring/basics/aop/test/applicationConfig-xml-config.xml");
        PrintUtil.printSeparator(60);

        PrintUtil.printTitle("testAOP (annotation based)", 60);
        testAOP("swt6/spring/basics/aop/test/applicationContext-annotation-config.xml");
        PrintUtil.printSeparator(60);
    }

}
