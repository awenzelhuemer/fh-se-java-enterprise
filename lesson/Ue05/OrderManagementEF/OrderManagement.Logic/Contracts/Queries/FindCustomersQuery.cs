using OrderManagement.Dtos;

namespace OrderManagement.Logic.Contracts.Queries
{
    public record FindCustomersQuery(Rating? rating = null)
        : IQuery<IEnumerable<CustomerDto>>
    {
    }
}