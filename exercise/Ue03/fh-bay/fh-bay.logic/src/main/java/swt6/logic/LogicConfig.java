package swt6.logic;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import swt6.logic.impl.ArticleLogicImpl;
import swt6.logic.impl.CustomerLogicImpl;

@Configuration
@ComponentScan(basePackages="swt6.dal.dao.impl")
public class LogicConfig {

    @Bean
    public ArticleLogic getArticleLogic() {
        return new ArticleLogicImpl();
    }

    @Bean
    public CustomerLogic getCustomerLogic() {
        return new CustomerLogicImpl();
    }

}
