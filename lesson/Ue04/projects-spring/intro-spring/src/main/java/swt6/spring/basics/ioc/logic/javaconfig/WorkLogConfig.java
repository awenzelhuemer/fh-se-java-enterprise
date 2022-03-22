package swt6.spring.basics.ioc.logic.javaconfig;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import swt6.spring.basics.ioc.util.Logger;

@Configuration
@ComponentScan(basePackageClasses = {Logger.class, WorkLogServiceImpl.class})
public class WorkLogConfig {

    // Spring creates a logger singleton
/*    @Bean
    public Logger consoleLogger() {
        return new ConsoleLogger();
    }

    @Bean
    public Logger fileLogger() {
        return new FileLogger();
    }

    @Bean
    public WorkLogService workLog() {
        return new WorkLogServiceImpl();
        // return new WorkLogServiceImpl(consoleLogger());
    }*/
}
