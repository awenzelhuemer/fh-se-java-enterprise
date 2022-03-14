# 1. Kurztest

## 1. Java modules

```java
module swt6 {
	provides swt6.MyProvider with MySimpleProvider;
	uses swt6.MyProvider
	requires swt6.modular;
	exports swt6.modular;
	opens swt6.modular.impl;  // Opens class for reflection
}
```

Get best service provider.

```java
ServiceLoader<MyProvider> serviceLoader = ServiceLoader.load(MyProvider.class);
double  minResolution = Double.MAX_VALUE;
TimerProvider  minProvider = null;

for (TimerProvider  provider  :  serviceLoader) {
    if (provider.timerResolution() < minResolution) {
        minProvider = provider;
        minResolution = minProvider.timerResolution();
    }
}
return  minProvider == null  ?  null  :  minProvider.getTimer(noTicks, interval);


public interface TimerProvider {

    double timerResolution();

    Timer getTimer(int noTicks, int interval);
}
```

## 2. Java beans

```java
@FunctionalInterface
public interface TimerListener extends EventListener {
    public void expired(TimerEvent event);
}

@Override
public void addTimerListener(TimerListener listener) {
    listeners.add(listener);
}

@Override
public void removeTimerListener(TimerListener listener) {
    listeners.remove(listener);
}

private void fireEvent(TimerEvent event) {
    listeners.forEach(l -> l.expired(event));
}
```

## 3. Hibernate with session factory

```java
SessionFactory sessionFactory = new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();

Session session = sessionFactory.openSession();
Transaction tx = session.beginTransaction();
session.save(employee);
tx.commit();
session.close();
sessionFactory.close();

public class HibernateUtil {

    private static SessionFactory sessionFactory;

    public static SessionFactory getSessionFactory() {
        if(sessionFactory == null){
            sessionFactory = new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();
        }
        return  sessionFactory;
    }

    public static void closeSessionFactory() {
        if(sessionFactory != null){
            sessionFactory.close();
            sessionFactory = null;
        }
    }

    public static Session getCurrentSession() {
        return sessionFactory.getCurrentSession();
    }
}
```

Find data set by id

```java
Employee employee = session.find(Employee.class, employeeId);
```

Create query

```java
List<Employee> employees = session.createQuery("select e from Employee e", Employee.class).getResultList();
```

Create query with parameter

```java
Query<Employee> query = session.createQuery("select e from Employee e where e.lastName like :lastName", Employee.class);
        query.setParameter("lastName", "%" + lastName + "%");

        var employees = query.getResultList();
```

Delete

```java
var employee = session.find(Employee.class, employeeId);
if(employee != null) {
    session.remove(employee);
}
```

hibernate.cfg.xml

```xml
<hibernate-configuration>
    <session-factory>
        <!-- DERBY network server properties -->
        <property name="hibernate.connection.driver_class">org.apache.derby.jdbc.ClientDriver</property>
        <property name="hibernate.connection.url">jdbc:derby://localhost/WorkLogDb;create=true</property>
        <property name="hibernate.dialect">org.hibernate.dialect.DerbyTenSevenDialect</property>
        
        <property name="hibernate.hbm2ddl.auto">update</property>
        <property name="hibernate.show_sql">false</property>
        <property name="hibernate.format_sql">false</property>

        <property name="hibernate.current_session_context_class">thread</property>

        <mapping resource="swt6/orm/domain/Employee.hbm.xml" />

    </session-factory>
</hibernate-configuration>

<?xml version="1.0"?>
<!DOCTYPE hibernate-mapping PUBLIC "-//Hibernate/Hibernate Mapping DTD 3.0//EN"
  "http://www.hibernate.org/dtd/hibernate-mapping-3.0.dtd">
<hibernate-mapping>
    <class name="swt6.orm.domain.Employee">
        <id name="id" type="long">
            <generator class="identity" />
        </id>
        <property name="firstName" type="string" />
        <property name="lastName" type="string" />
        <property name="dateOfBirth" type="java.time.LocalDate" />
    </class>
</hibernate-mapping>
```

# 4. JPA and Hibernate with persistence.xml

persistence.xml
```xml
<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<persistence xmlns="https://jakarta.ee/xml/ns/persistence"
             xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
             xsi:schemaLocation="https://jakarta.ee/xml/ns/persistence https://jakarta.ee/xml/ns/persistence/persistence_3_0.xsd"
             version="3.0">
    <persistence-unit name="WorkLogPU">
        <!-- The provider only needs to be set if you use several JPA providers -->
        <provider>org.hibernate.jpa.HibernatePersistenceProvider</provider>

        <properties>
            <!-- Scan for annotated classes. The specification only
                 requires managed providers to implement this feature.
                 So, unmanaged providers may not provide it. -->
            <property name="hibernate.archive.autodetection" value="class"/>

            <!-- JPA 2.0 standard properties -->
            <property name="javax.persistence.jdbc.driver" value="org.apache.derby.jdbc.ClientDriver"/>
            <property name="javax.persistence.jdbc.url" value="jdbc:derby://localhost/WorkLogDb;create=true"/>

            <!-- Hibernate EntityManager properties -->
            <property name="hibernate.dialect" value="org.hibernate.dialect.DerbyTenSevenDialect"/>

            <property name="hibernate.hbm2ddl.auto" value="create"/>
            <property name="hibernate.show_sql" value="false"/>
            <property name="hibernate.format_sql" value="true"/>
        </properties>
    </persistence-unit>
</persistence>
```

Insert data

```java
EntityManagerFactory emf = Persistence.createEntityManagerFactory("WorkLogPU");
EntityManager em = null;
EntityTransaction tx = null;

try {
    em = emf.createEntityManager();
    tx = em.getTransaction();
    tx.begin();

    em.persist(data);

    tx.commit();
} catch (Exception ex) {
    if (tx != null && tx.isActive()) {
        tx.rollback();
    }
    throw ex;
} finally {
    if (em != null) {
        em.close();
    }
    emf.close();
}
```

Util class for EntityManager
```java
public class JpaUtil {
    private static EntityManagerFactory emFactory;
    private static final ThreadLocal<EntityManager> emThread = new ThreadLocal<>();

    public static synchronized EntityManagerFactory getEntityManagerFactory() {
        if (emFactory == null) {
            emFactory = Persistence.createEntityManagerFactory("WorkLogPU");
        }
        return emFactory;
    }

    public static synchronized EntityManager getEntityManager() {
        if (emThread.get() == null) {
            emThread.set(getEntityManagerFactory().createEntityManager());
        }
        return emThread.get();
    }

    public static synchronized void closeEntityManager() {
        if (emThread.get() != null) {
            emThread.get().close();
            emThread.set(null);
        }
    }

    public static synchronized EntityManager getTransactedEntityManager() {
        EntityManager em = getEntityManager();
        EntityTransaction tx = em.getTransaction();

        if (!tx.isActive()) {
            tx.begin();
        }

        return em;
    }

    public static synchronized void commit() {
        EntityManager em = getEntityManager();
        EntityTransaction tx = em.getTransaction();

        if (tx.isActive()) {
            tx.commit();
        }
        closeEntityManager();
    }

    public static synchronized void rollback() {
        EntityManager em = getEntityManager();
        EntityTransaction tx = em.getTransaction();

        if (tx.isActive()) {
            tx.rollback();
        }
        closeEntityManager();
    }

    public static synchronized void closeEntityManagerFactory() {
        if (emFactory != null) {
            emFactory.close();
            emFactory = null;
        }
    }
}
```