package swt6.logic.impl;

import swt6.dal.dao.ArticleDao;
import swt6.dal.dao.BidDao;
import swt6.dal.dao.DaoFactory;
import swt6.dal.domain.Article;
import swt6.dal.domain.ArticleStatus;
import swt6.dal.domain.Bid;
import swt6.dal.domain.Customer;
import swt6.dal.util.JpaUtil;
import swt6.logic.ArticleLogic;

import javax.inject.Inject;
import java.time.LocalDateTime;
import java.util.List;

public class ArticleLogicImpl implements ArticleLogic {

    @Inject
    private BidDao bidDao;

    @Inject
    private ArticleDao articleDao;

    public ArticleLogicImpl() {
    }

    @Override
    public Article insert(Article article) {
        article = articleDao.insert(article);

        // TODO Abstract
        JpaUtil.commit();

        return article;
    }

    @Override
    public Article assignSellerAndBuyer(long articleId, Customer seller, Customer buyer) {

        var article = articleDao.findById(articleId).orElse(null);

        if(article == null) {
            throw new IllegalArgumentException("Article could not be found.");
        }

        if(seller.getId() == buyer.getId()) {
            throw new IllegalArgumentException("Seller and buyer are not allowed to be the same person.");
        }

        article.setSeller(seller);
        article.setBuyer(buyer);

        article = articleDao.insert(article);

        // TODO Abstract
        JpaUtil.commit();

        return article;
    }

    @Override
    public List<Article> findByNameAndDescription(String searchTerm) {
        var articles = articleDao.findByNameAndDescription(searchTerm);

        // TODO Abstract
        JpaUtil.commit();

        return articles;
    }

    public void addBid(long articleId, double amount, Customer bidder) {
        var article = articleDao.findById(articleId).orElse(null);

        var previousBid = bidDao.findHighestBidByArticle(article);

        if(article == null) {
            throw new IllegalArgumentException("Article could not be found.");
        }

        if(article.getStatus() == ArticleStatus.Sold) {
            throw new IllegalArgumentException("Article is already sold.");
        }

        if(article.getStatus() == ArticleStatus.Unsaleable) {
            throw new IllegalArgumentException("Article is not available.");
        }

        if (previousBid != null && previousBid.getAmount() >= amount) {
            throw new IllegalArgumentException("Amount needs to be higher than previous bid.");
        }

        // Bid was from same customer so only amount needs to be refreshed
        if(previousBid != null && previousBid.getBidder() == bidder) {
            previousBid.setAmount(amount);
            previousBid.setTimestamp(LocalDateTime.now());
            bidDao.update(previousBid);
        } else {
            Bid bid = new Bid(amount, LocalDateTime.now(), bidder, article);
            bidDao.insert(bid);
            article.setBidder(bidder);
            articleDao.insert(article);
        }

        // TODO Abstract
        JpaUtil.commit();
    }

    @Override
    public void finalizeAuction(long articleId) {
        var article = articleDao.findById(articleId).orElse(null);

        var previousBid = bidDao.findSecondHighestBidByArticle(article);
        var bid = bidDao.findHighestBidByArticle(article);

        if(article == null) {
            throw new IllegalArgumentException("Article could not be found.");
        }

        if(article.getStatus() == ArticleStatus.Sold) {
            throw new IllegalArgumentException("Article is already sold.");
        }

        if(article.getStatus() == ArticleStatus.Unsaleable) {
            throw new IllegalArgumentException("Article is not available.");
        }

        if(previousBid == null) {
            article.setFinalPrice(article.getInitialPrice());
        } else {
            // Amount from second-highest bid gets used because that's the limit of the previous bidder.
            article.setFinalPrice(previousBid.getAmount());
        }

        // Article could be sold without any bid (Would remain with seller).
        if(bid != null) {
            article.setBuyer(bid.getBidder());
            article.setStatus(ArticleStatus.Sold);
        } else {
            article.setStatus(ArticleStatus.Unsaleable);
        }

        articleDao.update(article);

        JpaUtil.commit();
    }
}
