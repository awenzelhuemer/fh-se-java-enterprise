package swt6.dal.dao;

import swt6.dal.domain.Article;
import swt6.dal.domain.Bid;

public interface BidDao extends BaseDao<Bid> {
    Bid findHighestBidByArticle(Article article);
}
