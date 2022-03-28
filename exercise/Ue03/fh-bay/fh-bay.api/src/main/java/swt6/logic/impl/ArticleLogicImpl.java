package swt6.logic.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import swt6.dal.dao.ArticleDao;
import swt6.dal.dao.BidDao;
import swt6.dal.domain.Article;
import swt6.dal.domain.Bid;
import swt6.dal.domain.Customer;
import swt6.dto.BidStatus;
import swt6.logic.ArticleLogic;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ArticleLogicImpl implements ArticleLogic {

    private final Logger logger = LoggerFactory.getLogger(ArticleLogicImpl.class);

    private final BidDao bidDao;
    private final ArticleDao articleDao;

    public ArticleLogicImpl(BidDao bidDao, ArticleDao articleDao) {
        this.bidDao = bidDao;
        this.articleDao = articleDao;
    }

    @Override
    public boolean exists(long articleId) {
        return articleDao.existsById(articleId);
    }

    @Override
    public Article insert(Article article) {
        return articleDao.save(article);
    }

    @Override
    public Article assignSeller(long articleId, Customer seller) {
        var article = articleDao.findById(articleId);

        if (article.isEmpty()) {
            throw new IllegalArgumentException("Article could not be found.");
        }

        article.get().setSeller(seller);
        return articleDao.saveAndFlush(article.get());
    }

    @Override
    public Article assignBuyer(long articleId, Customer buyer) {
        var article = articleDao.findById(articleId);

        if (article.isEmpty()) {
            throw new IllegalArgumentException("Article could not be found.");
        }

        article.get().setBuyer(buyer);
        return articleDao.saveAndFlush(article.get());
    }

    @Override
    public List<Article> findByNameAndDescription(String searchTerm) {
        return articleDao.findByNameOrDescriptionContains(searchTerm + "%");
    }

    public void addBid(long articleId, double amount, Customer bidder) {
        var article = articleDao.findById(articleId).orElse(null);

        validateArticleStatus(article);

        var previousBid = bidDao.findFirstByArticleOrderByAmountDesc(article);

        if (previousBid.isPresent() && amount <= previousBid.get().getAmount()) {
            throw new IllegalArgumentException("Amount needs to be higher than previous bid.");
        } else if(amount < article.getInitialPrice()) {
            throw new IllegalArgumentException("Amount needs to be set to the initial price or higher.");
        }

        // Bid was from same customer so only amount needs to be refreshed
        if (previousBid.isPresent() && previousBid.get().getBidder().getId().equals(bidder.getId())) {
            previousBid.get().setAmount(amount);
            previousBid.get().setTimestamp(LocalDateTime.now());
            bidDao.saveAndFlush(previousBid.get());
        } else {
            Bid bid = new Bid(amount, LocalDateTime.now(), bidder, article);
            bidDao.saveAndFlush(bid);
            article.setBidder(bidder);
            article.setCurrentPrice(previousBid.map(Bid::getAmount)
                    .orElseGet(article::getInitialPrice));
            articleDao.saveAndFlush(article);
        }
    }

    @Override
    public void finalizeBidding(long articleId) {
        var article = articleDao.findById(articleId).orElseThrow();

        validateArticleStatus(article);

        article.setEnd(LocalDateTime.now());

        articleDao.save(article);
    }

    @Scheduled(fixedDelay = 10000, initialDelay = 0)
    public void checkAuctions() {
        logger.info("Checking auctions");

        var articles = articleDao.findExpired();

        for (var article : articles) {
            logger.info("Finalize auction for article %s.".formatted(article));

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
    }

    private void validateArticleStatus(Article article) {
        if (article == null) {
            throw new IllegalArgumentException("Article could not be found.");
        }
        if (!(article.getStart().isBefore(LocalDateTime.now()) && article.getEnd().isAfter(LocalDateTime.now()))) {
            throw new IllegalArgumentException("Article is not in time range.");
        }

        if (article.getStatus() == BidStatus.Sold) {
            throw new IllegalArgumentException("Article is already sold.");
        }

        if (article.getStatus() == BidStatus.Expired) {
            throw new IllegalArgumentException("Article is no longer available.");
        }
    }
}
