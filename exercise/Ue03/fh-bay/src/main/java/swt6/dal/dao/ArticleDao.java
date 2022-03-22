package swt6.dal.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import swt6.dal.domain.Article;
import swt6.dal.domain.BidStatus;
import swt6.dal.domain.Customer;

import java.util.List;

@Repository
public interface ArticleDao extends JpaRepository<Article, Long> {
    @Query("select a from Article a where a.name like :searchTerm or a.description like :searchTerm order by a.name asc")
    List<Article> findByNameOrDescriptionContains(@Param("searchTerm") String searchTerm);
    List<Article> findBySeller(Customer seller);
    List<Article> findByBuyer(Customer buyer);
    List<Article> findByBidder(Customer bidder);
    List<Article> findByStatus(BidStatus status);
}
