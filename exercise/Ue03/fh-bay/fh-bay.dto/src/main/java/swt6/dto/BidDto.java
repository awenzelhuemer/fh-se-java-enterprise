package swt6.dto;

public class BidDto {
    private CustomerDto customer;
    private double amount;
    private Long articleId;

    public BidDto() {
    }

    public BidDto(CustomerDto customer, double amount, Long articleId) {
        this.customer = customer;
        this.amount = amount;
        this.articleId = articleId;
    }

    public CustomerDto getCustomer() {
        return customer;
    }

    public void setCustomer(CustomerDto customer) {
        this.customer = customer;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Long getArticleId() {
        return articleId;
    }

    public void setArticleId(Long articleId) {
        this.articleId = articleId;
    }
}
