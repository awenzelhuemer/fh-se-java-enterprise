namespace IntroEF.Domain;

public class Order
{
    #region Public Constructors

    public Order(string article, DateTimeOffset orderDate, decimal totalPrice) :
    this(Guid.NewGuid(), article, orderDate, totalPrice)
    {
    }

    public Order(Guid id, string article, DateTimeOffset orderDate, decimal totalPrice)
    {
        this.Id = id;
        this.OrderDate = orderDate;
        this.Article = article;
        this.TotalPrice = totalPrice;
    }

    #endregion

    #region Public Properties

    public string Article { get; set; }
    public Customer Customer { get; set; }
    public Guid Id { get; set; }
    public DateTimeOffset OrderDate { get; set; }

    public decimal TotalPrice { get; set; }

    #endregion

    #region Public Methods

    public override string ToString() => $"Order {{ Id: {Id}, Article: {Article}, OrderDate: {OrderDate}, TotalPrice: {TotalPrice} }}";

    #endregion
}