package swt6.dal.domain;

import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Category.class)
public abstract class Category_ extends swt6.dal.domain.BaseEntity_ {

	public static volatile SingularAttribute<Category, String> name;
	public static volatile SetAttribute<Category, Article> articles;
	public static volatile SetAttribute<Category, Category> subCategories;

	public static final String NAME = "name";
	public static final String ARTICLES = "articles";
	public static final String SUB_CATEGORIES = "subCategories";

}

