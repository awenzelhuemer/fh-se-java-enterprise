package swt6.dal.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
public class Bid extends BaseEntity {
    @Column(nullable = false)
    private double amount;
    @Column(nullable = false)
    private LocalDateTime timestamp;

    @ManyToOne
    private Customer bidder;

    @ManyToOne
    private Article article;

    public Bid() {
    }

    public Bid(double amount, LocalDateTime timestamp, Customer bidder, Article article) {
        this.amount = amount;
        this.timestamp = timestamp;
        this.bidder = bidder;
        this.article = article;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public Customer getBidder() {
        return bidder;
    }

    public void setBidder(Customer bidder) {
        this.bidder = bidder;
    }

    public Article getArticle() {
        return article;
    }

    public void setArticle(Article article) {
        this.article = article;
    }

    @Override
    public String toString() {
        var formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return amount + "EUR from " + bidder + " at " + timestamp.format(formatter);
    }
}
