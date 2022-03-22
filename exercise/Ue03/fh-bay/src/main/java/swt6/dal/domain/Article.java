package swt6.dal.domain;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Article extends BaseEntity {
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String description;
    @Column(nullable = false)
    private double initialPrice;
    private double currentPrice;
    @Column(nullable = false)
    private LocalDateTime start;
    private LocalDateTime end;

    @ManyToOne
    private Customer seller;
    @ManyToOne
    private Customer buyer;
    @ManyToOne
    private Customer bidder;

    @Column(nullable = false)
    private BidStatus status;

    @ManyToMany(mappedBy = "articles", fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    private Set<Category> categories = new HashSet<>();

    public Article() {
    }

    public Article(String name, String description, double initialPrice, double currentPrice, LocalDateTime start, LocalDateTime end, Customer seller, Customer buyer, Customer bidder, BidStatus status) {
        this.name = name;
        this.description = description;
        this.initialPrice = initialPrice;
        this.currentPrice = currentPrice;
        this.start = start;
        this.end = end;
        this.seller = seller;
        this.buyer = buyer;
        this.bidder = bidder;
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getInitialPrice() {
        return initialPrice;
    }

    public void setInitialPrice(double initialPrice) {
        this.initialPrice = initialPrice;
    }

    public double getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(double finalPrice) {
        this.currentPrice = finalPrice;
    }

    public LocalDateTime getStart() {
        return start;
    }

    public void setStart(LocalDateTime start) {
        this.start = start;
    }

    public LocalDateTime getEnd() {
        return end;
    }

    public void setEnd(LocalDateTime end) {
        this.end = end;
    }

    public Customer getSeller() {
        return seller;
    }

    public void setSeller(Customer seller) {
        this.seller = seller;
    }

    public Customer getBuyer() {
        return buyer;
    }

    public void setBuyer(Customer buyer) {
        this.buyer = buyer;
    }

    public Customer getBidder() {
        return bidder;
    }

    public void setBidder(Customer bidder) {
        this.bidder = bidder;
    }

    public BidStatus getStatus() {
        return status;
    }

    public void setStatus(BidStatus status) {
        this.status = status;
    }

    public Set<Category> getCategories() {
        return categories;
    }

    public void setCategories(Set<Category> categories) {
        this.categories = categories;
    }

    public void assignCategory(Category category) {
        this.categories.add(category);
        category.getArticles().add(this);
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        return name + " (" + description + ")" +
                " prices: (initial " + initialPrice + "EUR final " + currentPrice + "EUR)" +
                " time range: " + start.format(formatter) + " - " + end.format(formatter) +
                " status: " + status;
    }
}
