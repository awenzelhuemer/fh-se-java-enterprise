using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

namespace IntroEF.Domain;

//[Table("TBL_CUSTOMER")]
public class Customer
{
    #region Public Constructors

    public Customer(string name, Rating rating) : this(Guid.NewGuid(), name, rating)
    {
    }

    public Customer(Guid id, string name, Rating rating)
    {
        this.Id = id;
        this.Name = name;
        this.Rating = rating;
    }

    #endregion

    #region Public Properties

    //[Key]
    public Guid Id { get; set; }

    //[Required]
    //[Column("COL_NAME")]
    public string? Name { get; set; }

    //[NotMapped]
    public Rating Rating { get; set; }

    //[Column(TypeName = "decimal(18,2)")]
    public decimal? TotalRevenue { get; set; }

    public Address? Address { get; set; }

    public IList<Order> Orders { get; set; } = new List<Order>();

    #endregion

    #region Public Methods

    public override string ToString() => $"Customer {{ Id: {Id}, Name: {Name}, " +
                                       $"TotalRevenue: {TotalRevenue} }}";

    #endregion
}