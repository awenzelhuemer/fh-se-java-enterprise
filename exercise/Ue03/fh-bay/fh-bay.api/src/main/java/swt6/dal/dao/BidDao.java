package swt6.dal.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import swt6.dal.domain.Article;
import swt6.dal.domain.Bid;

import java.util.Optional;

@Repository
public interface BidDao extends JpaRepository<Bid, Long> {
    Optional<Bid> findFirstByArticleOrderByAmountDesc(Article article);
}
