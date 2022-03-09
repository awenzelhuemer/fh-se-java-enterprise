package swt6.dal.domain;

import java.time.LocalDateTime;
import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Article.class)
public abstract class Article_ extends swt6.dal.domain.BaseEntity_ {

	public static volatile SingularAttribute<Article, Customer> seller;
	public static volatile SingularAttribute<Article, Customer> bidder;
	public static volatile SingularAttribute<Article, Double> initialPrice;
	public static volatile SingularAttribute<Article, String> name;
	public static volatile SingularAttribute<Article, LocalDateTime> start;
	public static volatile SingularAttribute<Article, String> description;
	public static volatile SingularAttribute<Article, Double> finalPrice;
	public static volatile SingularAttribute<Article, LocalDateTime> end;
	public static volatile SetAttribute<Article, Category> categories;
	public static volatile SingularAttribute<Article, Customer> buyer;
	public static volatile SingularAttribute<Article, ArticleStatus> status;

	public static final String SELLER = "seller";
	public static final String BIDDER = "bidder";
	public static final String INITIAL_PRICE = "initialPrice";
	public static final String NAME = "name";
	public static final String START = "start";
	public static final String DESCRIPTION = "description";
	public static final String FINAL_PRICE = "finalPrice";
	public static final String END = "end";
	public static final String CATEGORIES = "categories";
	public static final String BUYER = "buyer";
	public static final String STATUS = "status";

}

