package swt6.dal.dao.impl;

import org.springframework.stereotype.Component;
import swt6.dal.dao.ArticleDao;
import swt6.dal.domain.Article;
import swt6.dal.domain.ArticleStatus;
import swt6.dal.domain.Article_;
import swt6.dal.domain.Customer;
import swt6.dal.util.JpaUtil;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.ParameterExpression;
import javax.persistence.criteria.Root;
import java.util.List;

@Component
public class ArticleDaoImpl extends BaseDaoImpl<Article> implements ArticleDao {
    @Override
    protected Class<Article> getEntityType() {
        return Article.class;
    }

    @Override
    public List<Article> findByNameAndDescription(String searchTerm) {
        var em = JpaUtil.getTransactedEntityManager();

        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Article> articleCQ = cb.createQuery(Article.class);
        Root<Article> article = articleCQ.from(Article.class);
        ParameterExpression<String> p = cb.parameter(String.class);

        searchTerm = "%" + searchTerm + "%";
        articleCQ.where(cb.or(cb.like(article.get(Article_.name), p), cb.like(article.get(Article_.description), p))).select(article);

        TypedQuery<Article> articleQuery = em.createQuery(articleCQ);
        articleQuery.setParameter(p, searchTerm);
        return articleQuery.getResultList();
    }

    @Override
    public List<Article> findBySeller(Customer seller) {
        var em = JpaUtil.getTransactedEntityManager();
        var query = em.createQuery("from Article where seller = :seller order by name", getEntityType());
        query.setParameter("seller", seller);
        return query.getResultList();
    }

    @Override
    public List<Article> findByBuyer(Customer buyer) {
        var em = JpaUtil.getTransactedEntityManager();
        var query = em.createQuery("from Article where buyer = :buyer order by name", getEntityType());
        query.setParameter("buyer", buyer);
        return query.getResultList();
    }

    @Override
    public List<Article> findByBidder(Customer bidder) {
        var em = JpaUtil.getTransactedEntityManager();
        var query = em.createQuery("from Article where bidder = :bidder order by name", getEntityType());
        query.setParameter("bidder", bidder);
        return query.getResultList();
    }

    @Override
    public List<Article> findByStatus(ArticleStatus status) {
        var em = JpaUtil.getTransactedEntityManager();
        var query = em.createQuery("from Article where status = :status order by name" ,getEntityType());
        query.setParameter("status", status);
        return query.getResultList();
    }
}
