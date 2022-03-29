using OrderManagement.Dtos;

namespace OrderManagement.Logic.Contracts.Commands
{
    public record CreateCustomerCommand(CustomerForCreationDto customer) : ICommand<CustomerDto>
    {
    }
}