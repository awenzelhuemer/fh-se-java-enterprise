using AutoMapper;
using MediatR;
using Microsoft.EntityFrameworkCore;
using OrderManagement.Dal;
using OrderManagement.Dtos;
using OrderManagement.Logic.Contracts.Queries;

namespace OrderManagement.Logic.Handlers.Queries
{
    public class FindCustomersHandler : IRequestHandler<FindCustomersQuery, IEnumerable<CustomerDto>>
    {
        #region Private Fields

        private readonly OrderManagementContext db;
        private readonly IMapper mapper;

        #endregion

        #region Public Constructors

        public FindCustomersHandler(OrderManagementContext db, IMapper mapper)
        {
            this.db = db;
            this.mapper = mapper;
        }

        #endregion

        #region Public Methods

        public async Task<IEnumerable<CustomerDto>> Handle(FindCustomersQuery query, CancellationToken cancellationToken)
        {
            var customerQuery = db.Customers.AsNoTracking().Include(c => c.Address);

            if (query.rating == null)
            {
                var customers = await customerQuery.ToListAsync(cancellationToken);
                return mapper.Map<IEnumerable<CustomerDto>>(customers);
            }
            else
            {
                var domainRating = mapper.Map<Domain.Rating>(query.rating);
                var customers = await customerQuery.Where(c => c.Rating == domainRating)
                    .ToListAsync(cancellationToken);
                return mapper.Map<IEnumerable<CustomerDto>>(customers);
            }
        }

        #endregion
    }
}