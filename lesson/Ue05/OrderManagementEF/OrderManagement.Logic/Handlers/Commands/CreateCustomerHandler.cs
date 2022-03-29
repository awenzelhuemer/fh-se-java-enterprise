using AutoMapper;
using MediatR;
using OrderManagement.Dal;
using OrderManagement.Domain;
using OrderManagement.Dtos;
using OrderManagement.Logic.Contracts.Commands;

namespace OrderManagement.Logic.Handlers.Commands
{
    public class CreateCustomerHandler : IRequestHandler<CreateCustomerCommand, CustomerDto>
    {
        #region Public Methods

        private readonly OrderManagementContext db;
        private readonly IMapper mapper;

        public CreateCustomerHandler(OrderManagementContext db, IMapper mapper)
        {
            this.db = db;
            this.mapper = mapper;
        }

        public async Task<CustomerDto> Handle(CreateCustomerCommand request, CancellationToken cancellationToken)
        {
            var customer = mapper.Map<Customer>(request.customer);
            await db.Customers.AddAsync(customer);
            await db.SaveChangesAsync(cancellationToken);
            return mapper.Map<CustomerDto>(customer);
        }

        #endregion
    }
}