namespace IntroEF.Domain;

public class Address
{
    #region Public Constructors

    public Address(int zipCode, string city, string? street = null)
    {
        ZipCode = zipCode;
        City = city;
        Street = street;
    }

    #endregion

    #region Public Properties

    public string City { get; set; }
    public int Id { get; set; }
    public string? Street { get; set; }
    public int ZipCode { get; set; }

    #endregion

    #region Public Methods

    public override string ToString() => $"Address {{ ZipCode: {ZipCode}, City: {City}, Street: {Street ?? ""} }}";

    #endregion
}