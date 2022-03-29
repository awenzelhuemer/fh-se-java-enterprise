using MediatR;

namespace OrderManagement.Logic.Contracts.Queries
{
    public interface IQuery<out TResponse> : IRequest<TResponse>
    {
    }
}