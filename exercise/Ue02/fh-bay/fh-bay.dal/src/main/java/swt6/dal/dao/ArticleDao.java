package swt6.dal.dao;

import swt6.dal.domain.Article;
import swt6.dal.domain.ArticleStatus;
import swt6.dal.domain.Customer;

import java.util.List;

public interface ArticleDao extends BaseDao<Article> {
    List<Article> findByNameAndDescription(String searchTerm);
    List<Article> findBySeller(Customer seller);
    List<Article> findByBuyer(Customer buyer);
    List<Article> findByBidder(Customer bidder);
    List<Article> findByStatus(ArticleStatus status);
}
