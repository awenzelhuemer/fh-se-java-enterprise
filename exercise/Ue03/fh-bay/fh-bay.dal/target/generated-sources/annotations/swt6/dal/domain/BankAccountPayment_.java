package swt6.dal.domain;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(BankAccountPayment.class)
public abstract class BankAccountPayment_ extends swt6.dal.domain.Payment_ {

	public static volatile SingularAttribute<BankAccountPayment, String> iban;
	public static volatile SingularAttribute<BankAccountPayment, String> bic;

	public static final String IBAN = "iban";
	public static final String BIC = "bic";

}

