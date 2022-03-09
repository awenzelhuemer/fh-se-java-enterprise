// package swt6.dal.domain;
//
// import javax.persistence.Entity;
// import javax.persistence.ManyToOne;
// import java.time.LocalDate;
//
// @Entity
// public class Article extends BaseEntity {
//     private String name;
//     private String description;
//     private double initialPrice;
//     private double finalPrice;
//     private LocalDate start;
//     private LocalDate end;
//
//     @ManyToOne
//     private Customer seller;
//
//     @ManyToOne
//     private Customer buyer;
//
//     @ManyToOne
//     private Customer bidder;
//     private ArticleStatus status;
//
//     public Article() {
//     }
//
//     public Article(String name, String description, double initialPrice, double finalPrice, LocalDate start, LocalDate end, Customer seller, Customer buyer, Customer bidder, ArticleStatus status) {
//         this.name = name;
//         this.description = description;
//         this.initialPrice = initialPrice;
//         this.finalPrice = finalPrice;
//         this.start = start;
//         this.end = end;
//         this.seller = seller;
//         this.buyer = buyer;
//         this.bidder = bidder;
//         this.status = status;
//     }
//
//     public String getName() {
//         return name;
//     }
//
//     public void setName(String name) {
//         this.name = name;
//     }
//
//     public String getDescription() {
//         return description;
//     }
//
//     public void setDescription(String description) {
//         this.description = description;
//     }
//
//     public double getInitialPrice() {
//         return initialPrice;
//     }
//
//     public void setInitialPrice(double initialPrice) {
//         this.initialPrice = initialPrice;
//     }
//
//     public double getFinalPrice() {
//         return finalPrice;
//     }
//
//     public void setFinalPrice(double finalPrice) {
//         this.finalPrice = finalPrice;
//     }
//
//     public LocalDate getStart() {
//         return start;
//     }
//
//     public void setStart(LocalDate start) {
//         this.start = start;
//     }
//
//     public LocalDate getEnd() {
//         return end;
//     }
//
//     public void setEnd(LocalDate end) {
//         this.end = end;
//     }
//
//     public Customer getSeller() {
//         return seller;
//     }
//
//     public void setSeller(Customer seller) {
//         this.seller = seller;
//     }
//
//     public Customer getBuyer() {
//         return buyer;
//     }
//
//     public void setBuyer(Customer buyer) {
//         this.buyer = buyer;
//     }
//
//     public Customer getBidder() {
//         return bidder;
//     }
//
//     public void setBidder(Customer bidder) {
//         this.bidder = bidder;
//     }
//
//     public ArticleStatus getStatus() {
//         return status;
//     }
//
//     public void setStatus(ArticleStatus status) {
//         this.status = status;
//     }
// }
