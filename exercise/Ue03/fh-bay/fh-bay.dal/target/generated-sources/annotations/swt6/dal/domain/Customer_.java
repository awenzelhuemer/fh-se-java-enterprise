package swt6.dal.domain;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Customer.class)
public abstract class Customer_ extends swt6.dal.domain.BaseEntity_ {

	public static volatile SingularAttribute<Customer, String> firstName;
	public static volatile SingularAttribute<Customer, String> lastName;
	public static volatile SingularAttribute<Customer, Address> deliveryAddress;
	public static volatile SingularAttribute<Customer, Address> billingAddress;

	public static final String FIRST_NAME = "firstName";
	public static final String LAST_NAME = "lastName";
	public static final String DELIVERY_ADDRESS = "deliveryAddress";
	public static final String BILLING_ADDRESS = "billingAddress";

}

