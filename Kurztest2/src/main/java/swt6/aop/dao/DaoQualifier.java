package swt6.aop.dao;

import org.springframework.beans.factory.annotation.Qualifier;

@Qualifier
public @interface DaoQualifier {

    Type value() default Type.Default;

    enum Type {
        Default,
        Cool
    }
}
