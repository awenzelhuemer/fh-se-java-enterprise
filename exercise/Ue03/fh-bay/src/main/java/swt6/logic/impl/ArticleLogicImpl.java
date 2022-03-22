package swt6.logic.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import swt6.dal.dao.ArticleDao;
import swt6.dal.dao.BidDao;
import swt6.dal.domain.Article;
import swt6.dal.domain.Bid;
import swt6.dal.domain.BidStatus;
import swt6.dal.domain.Customer;
import swt6.logic.ArticleLogic;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ArticleLogicImpl implements ArticleLogic {

    @Autowired
    private BidDao bidDao;
    @Autowired
    private ArticleDao articleDao;

    @Override
    public Article insert(Article article) {
        return articleDao.save(article);
    }

    @Override
    public Article assignSeller(long articleId, Customer seller) {
        var article = articleDao.findById(articleId).orElse(null);

        if (article == null) {
            throw new IllegalArgumentException("Article could not be found.");
        }

        if (article.getStatus() != BidStatus.New) {
            throw new IllegalArgumentException("Article seller can't be changed anymore.");
        }

        article.setSeller(seller);

        article = articleDao.save(article);

        return article;
    }

    @Override
    public List<Article> findByNameAndDescription(String searchTerm) {
        return articleDao.findByNameOrDescriptionContains(searchTerm + "%");
    }

    public void addBid(long articleId, double amount, Customer bidder) {
        var article = articleDao.findById(articleId).orElse(null);

        validateArticleStatus(article);

        var previousBid = bidDao.findFirstByArticleOrderByAmountDesc(article);

        if (previousBid.isPresent() && article.getCurrentPrice() >= amount) {
            throw new IllegalArgumentException("Amount needs to be higher than previous bid.");
        }

        // Bid was from same customer so only amount needs to be refreshed
        if (previousBid.isPresent() && previousBid.get().getBidder() == bidder) {
            previousBid.get().setAmount(amount);
            previousBid.get().setTimestamp(LocalDateTime.now());
            bidDao.save(previousBid.get());
        } else {
            Bid bid = new Bid(amount, LocalDateTime.now(), bidder, article);
            bidDao.save(bid);
            article.setBidder(bidder);
            article.setCurrentPrice(previousBid.map(Bid::getAmount)
                    .orElseGet(article::getInitialPrice));
            articleDao.save(article);
        }
    }

    @Override
    public void finalizeAuction(long articleId) {
        var article = articleDao.findById(articleId).orElse(null);

        validateArticleStatus(article);

        var highestBid = bidDao.findFirstByArticleOrderByAmountDesc(article);

        // Article could be sold without any bid (Would remain with seller).
        if (highestBid.isPresent()) {
            article.setBuyer(highestBid.get().getBidder());
            article.setStatus(BidStatus.Sold);
        } else {
            article.setStatus(BidStatus.Expired);
        }

        articleDao.save(article);
    }

    private void validateArticleStatus(Article article) {
        if (article == null) {
            throw new IllegalArgumentException("Article could not be found.");
        }
        if (!(article.getStart().isBefore(LocalDateTime.now()) && article.getEnd().isAfter(LocalDateTime.now()))) {
            throw new IllegalArgumentException("Article is not in time range.");
        }

        if (article.getStatus() == BidStatus.New) {
            throw new IllegalArgumentException("Article is not ready for sale.");
        }

        if (article.getStatus() == BidStatus.Sold) {
            throw new IllegalArgumentException("Article is already sold.");
        }

        if (article.getStatus() == BidStatus.Expired) {
            throw new IllegalArgumentException("Article is no longer available.");
        }
    }
}
