package swt6.logic;

import swt6.dal.domain.Article;
import swt6.dal.domain.Bid;
import swt6.dal.domain.Customer;

import java.util.List;

public interface ArticleLogic {

    Article insert(Article article);

    Article assignSeller(long articleId, Customer seller);

    // Buyer gets automatically set
    Article assignBuyer(long articleId, Customer buyer);

    List<Article> findByNameAndDescription(String searchTerm);

    void addBid(long articleId, double amount, Customer bidder);
}
