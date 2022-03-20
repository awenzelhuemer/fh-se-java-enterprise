package swt6.dal.dao.impl;

import org.springframework.stereotype.Component;
import swt6.dal.dao.BidDao;
import swt6.dal.domain.Article;
import swt6.dal.domain.Bid;
import swt6.dal.util.JpaUtil;

@Component
public class BidDaoImpl extends BaseDaoImpl<Bid> implements BidDao {
    @Override
    public Bid findHighestBidByArticle(Article article) {
        var em = JpaUtil.getTransactedEntityManager();
        var query = em.createQuery("from Bid where article = :article order by amount desc", getEntityType());
        query.setParameter("article", article);
        query.setFirstResult(0);
        query.setMaxResults(1);
        return query.getResultList().stream().findFirst().orElse(null);
    }

    @Override
    public Bid findSecondHighestBidByArticle(Article article) {
        var em = JpaUtil.getTransactedEntityManager();
        var query = em.createQuery("from Bid where article = :article order by amount desc", getEntityType());
        query.setParameter("article", article);
        query.setFirstResult(1);
        query.setMaxResults(1);
        return query.getResultList().stream().findFirst().orElse(null);
    }

    @Override
    protected Class<Bid> getEntityType() {
        return Bid.class;
    }
}
