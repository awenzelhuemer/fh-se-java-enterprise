package swt6.dal.domain;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(CreditCardPayment.class)
public abstract class CreditCardPayment_ extends swt6.dal.domain.Payment_ {

	public static volatile SingularAttribute<CreditCardPayment, Integer> expirationYear;
	public static volatile SingularAttribute<CreditCardPayment, Integer> expirationMonth;
	public static volatile SingularAttribute<CreditCardPayment, String> cardNumber;

	public static final String EXPIRATION_YEAR = "expirationYear";
	public static final String EXPIRATION_MONTH = "expirationMonth";
	public static final String CARD_NUMBER = "cardNumber";

}

