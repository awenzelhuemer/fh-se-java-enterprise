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
    <aop:aspectj-autoproxy />
    <context:component-scan base-package="swt6.spring.basics.aop" />
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

```xml
<bean id="dataSource"
      class="org.apache.commons.dbcp2.BasicDataSource"
      destroy-method="close">
    <property name="driverClassName" value="${database.driverClassName}"/>
    <property name="url" value="${database.url}"/>
    <property name="username" value="${database.username}"/>
    <property name="password" value="${database.password}"/>
</bean>

<bean id="jdbcTemplate" class="org.springframework.jdbc.core.JdbcTemplate">
    <property name="dataSource" ref="dataSource"/>
</bean>
<bean id="employeeDaoJdbc" class="swt6.spring.worklog.dao.jdbc.EmployeeDaoJdbc">
    <property name="jdbcTemplate" ref="jdbcTemplate"/>
    <property name="dataSource" ref="dataSource" />
</bean>
```

### 2.1. With JpaRepository

```java
@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    Optional<Employee> findByLastName(@Param("lastName") String lastName);

    @Query("select e from Employee e where e.dateOfBirth < :date")
    List<Employee> findOlderThan(@Param("date") LocalDate date);
}
```

## 3. Transactions

### 3.1. With AOP

```xml
<bean id="transactionManager" class="org.springframework.orm.jpa.JpaTransactionManager">
    <property name="entityManagerFactory" ref="entityManagerFactory"/>
</bean>
<tx:annotation-driven transaction-manager="transactionManager"/>
```

### 3.2. With Annotations

```java
@Transactional
public class WorkLogServiceImpl2 implements WorkLogService {
    @Transactional(readOnly = true)
    @Override
    public Optional<Employee> findEmployeeById(Long id) {
        return employeeRepository.findById(id);
    }
}
```

```xml
<bean id="transactionManager" class="org.springframework.orm.jpa.JpaTransactionManager">
    <property name="entityManagerFactory" ref="entityManagerFactory"/>
</bean>

<tx:advice id="txAdvice" transaction-manager="transactionManager">
    <tx:attributes>
        <tx:method name="find*" read-only="true"/>
        <tx:method name="*"/>
    </tx:attributes>
</tx:advice>

<tx:annotation-driven transaction-manager="transactionManager" />
```

## 3. Spring Web

API-Controller
```java
@RestController
@RequestMapping(value = "/workLog", produces = MediaType.APPLICATION_JSON_VALUE)
public class EmployeeRestController {

    @GetMapping("/employees")
    @Operation(summary = "Employee list", description = "Returns a list of all stored employees.")
    @ApiResponse(responseCode = "200", description = "Success")
    public List<EmployeeDto> getAllEmployees() {
        var employees = workLog.findAllEmployees();
        return mapper.map(employees, new TypeToken<Collection<EmployeeDto>>() {}.getType());
    }

}
```

Cors
```java
@Configuration
public class AppConfig {

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**").allowedMethods("GET", "PUT", "POST", "DELETE");
            }
        };
    }
}
```
