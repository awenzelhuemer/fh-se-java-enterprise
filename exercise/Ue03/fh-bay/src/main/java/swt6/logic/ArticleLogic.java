package swt6.logic;

import swt6.dal.domain.Article;
import swt6.dal.domain.Bid;
import swt6.dal.domain.Customer;

import java.util.List;

public interface ArticleLogic {

    Article insert(Article article);

    Article assignSeller(long articleId, Customer seller);

    List<Article> findByNameAndDescription(String searchTerm);

    void addBid(long articleId, double amount, Customer bidder);

    void finalizeAuction(long articleId);
}
