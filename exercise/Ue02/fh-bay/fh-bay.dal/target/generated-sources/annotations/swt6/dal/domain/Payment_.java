package swt6.dal.domain;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Payment.class)
public abstract class Payment_ extends swt6.dal.domain.BaseEntity_ {

	public static volatile SingularAttribute<Payment, String> owner;
	public static volatile SingularAttribute<Payment, Customer> customer;

	public static final String OWNER = "owner";
	public static final String CUSTOMER = "customer";

}

