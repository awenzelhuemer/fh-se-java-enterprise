# Kurztest 2

## 1. Aspect oriented programming (AOP)

### 1.1. With config file

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans>
    <bean id="workLog" class="swt6.spring.basics.aop.logic.WorkLogServiceImpl" />
    <bean id="traceAdvice" class="swt6.spring.basics.aop.advice.TraceAdvice" />

    <aop:config>
        <aop:pointcut id="findMethods" expression="execution(public * swt6.spring.basics.aop.logic..*find*(..))" />
        <aop:pointcut id="findByIdMethods" expression="execution(public * swt6.spring.basics.aop.logic..*find*By*Id(..))" />
        <aop:aspect id="traceAspect" ref="traceAdvice">
            <aop:before method="traceBefore" pointcut-ref="findMethods" />
            <aop:after-returning method="traceAfter" pointcut-ref="findMethods" />
            <aop:around method="traceAround" pointcut-ref="findByIdMethods" />
            <aop:after-throwing method="traceException" pointcut-ref="findByIdMethods" throwing="exception" />
            <aop:declare-parents types-matching="swt6.spring.basics.aop.logic.*"
                                 implement-interface="swt6.spring.basics.aop.advice.TraceOptions"
                                 default-impl="swt6.spring.basics.aop.advice.TraceOptionsDefaultImpl" />
        </aop:aspect>
    </aop:config>
</beans>
```

### 1.2. With annotations

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans>
    <!-- <context:annotation-config />-->
    <aop:aspectj-autoproxy />
    <context:component-scan base-package="swt6.spring.basics.aop" />
    <!-- <bean id="workLog" class="swt6.spring.basics.aop.logic.WorkLogServiceImpl"/>-->
    <!-- <bean id="traceAdvice" class="swt6.spring.basics.aop.advice.TraceAdvice"/>-->
</beans>
```

```java
@Pointcut("execution(public * swt6.spring.basics.aop.logic..*find*(..))")
private void findMethods() {}
@Before("findMethods()")
@AfterReturning("findMethods()")
@Around("findMethods()")
@AfterThrowing(pointcut = "findMethods()", throwing = "exception")
```

## 2. Spring Data

### 2.1. With JdbcTemplate

Query
`var employeeList = jdbcTemplate.query(sql, new EmployeeRowMapper(), id);`

Query single object
`var employee = jdbcTemplate.queryForObject(sql, new EmployeeRowMapper(), id);`

Update or insert
`jdbcTemplate.update(sql, e.getFirstName(), e.getLastName(), Date.valueOf(e.getDateOfBirth()));`

## 3. Spring Web
