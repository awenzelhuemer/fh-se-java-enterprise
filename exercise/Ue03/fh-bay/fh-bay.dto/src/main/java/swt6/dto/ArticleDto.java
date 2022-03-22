package swt6.dto;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

public class ArticleDto {

    private Long id;

    private String name;

    private String description;

    private double initialPrice;

    private double currentPrice;

    private LocalDateTime start;
    private LocalDateTime end;

    private CustomerDto seller;

    private CustomerDto buyer;

    private BidStatus status;

    public ArticleDto() {
    }

    public ArticleDto(String name, String description, double initialPrice, double currentPrice, LocalDateTime start, LocalDateTime end, CustomerDto seller, CustomerDto buyer, BidStatus status) {
        this.name = name;
        this.description = description;
        this.initialPrice = initialPrice;
        this.currentPrice = currentPrice;
        this.start = start;
        this.end = end;
        this.seller = seller;
        this.buyer = buyer;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public void setCurrentPrice(double currentPrice) {
        this.currentPrice = currentPrice;
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

    public CustomerDto getSeller() {
        return seller;
    }

    public void setSeller(CustomerDto seller) {
        this.seller = seller;
    }

    public CustomerDto getBuyer() {
        return buyer;
    }

    public void setBuyer(CustomerDto buyer) {
        this.buyer = buyer;
    }

    public BidStatus getStatus() {
        return status;
    }

    public void setStatus(BidStatus status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "ArticleDto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", initialPrice=" + initialPrice +
                ", currentPrice=" + currentPrice +
                ", start=" + start +
                ", end=" + end +
                ", seller=" + seller +
                ", buyer=" + buyer +
                ", status=" + status +
                '}';
    }
}
