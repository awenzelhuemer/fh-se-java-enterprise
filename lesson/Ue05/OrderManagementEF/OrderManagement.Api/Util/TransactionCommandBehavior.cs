﻿using MediatR;

namespace OrderManagement.Api.Util
{
    using System.Transactions;

    public class TransactionCommandBehavior<TRequest, TResponse> :
      IPipelineBehavior<TRequest, TResponse>
        where TRequest : IRequest<TResponse>
        // TODO: Restrict TRequest do ICommand<TResponse> if only commands should be executed in transactions
    {
        #region Private Fields

        private readonly ILogger<TransactionCommandBehavior<TRequest, TResponse>> logger;

        #endregion

        #region Public Constructors

        public TransactionCommandBehavior(
      ILogger<TransactionCommandBehavior<TRequest, TResponse>> logger)
        {
            this.logger = logger;
        }

        #endregion

        #region Private Methods

        private void LogTransctionCompleted()
        {
            if (Transaction.Current is not null)
            {
                Transaction.Current.TransactionCompleted += (s, e) =>
                {
                    bool committed = e.Transaction?.TransactionInformation.Status == TransactionStatus.Committed;
                    logger.LogInformation($"Transaction {(committed ? "COMMITTED" : "ROLLED BACK")}");
                };
            }
        }

        #endregion

        #region Public Methods

        public async Task<TResponse> Handle(TRequest request,
              CancellationToken cancellationToken,
      RequestHandlerDelegate<TResponse> next)
        {
            TResponse? response = default;

            TransactionOptions transactionOptions = new TransactionOptions
            {
                IsolationLevel = IsolationLevel.ReadCommitted
            };

            logger.LogInformation("Begin Transaction");
            using (var tx = new TransactionScope(TransactionScopeOption.Required, transactionOptions,
                                                 TransactionScopeAsyncFlowOption.Enabled))
            {
                LogTransctionCompleted();

                response = await next();

                tx.Complete();
            }
            logger.LogInformation("Transaction disposed");

            return response;
        }

        #endregion
    }
}