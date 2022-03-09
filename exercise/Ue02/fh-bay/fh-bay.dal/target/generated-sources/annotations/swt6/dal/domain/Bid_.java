package swt6.dal.domain;

import java.time.LocalDateTime;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Bid.class)
public abstract class Bid_ extends swt6.dal.domain.BaseEntity_ {

	public static volatile SingularAttribute<Bid, Double> amount;
	public static volatile SingularAttribute<Bid, Customer> bidder;
	public static volatile SingularAttribute<Bid, Article> article;
	public static volatile SingularAttribute<Bid, LocalDateTime> timestamp;

	public static final String AMOUNT = "amount";
	public static final String BIDDER = "bidder";
	public static final String ARTICLE = "article";
	public static final String TIMESTAMP = "timestamp";

}

