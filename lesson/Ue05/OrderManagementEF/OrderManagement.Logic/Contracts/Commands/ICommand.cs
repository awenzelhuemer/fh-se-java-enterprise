using MediatR;

namespace OrderManagement.Logic.Contracts.Commands;

public interface ICommand<out TResponse> : IRequest<TResponse>
{
}