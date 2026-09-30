using System;

namespace Init.Sigepro.FrontEnd.AppLogic.Utils.ResultPattern
{
    public class Result<TError> where TError : class
    {
        public bool IsSuccess { get; }
        public TError? ErrorDetail { get; }

        protected Result(bool isSuccess, TError? errorDetail)
        {
            if (isSuccess && errorDetail != null)
            {
                throw new InvalidOperationException($"{nameof(errorDetail)} non può essere valorizzato se {nameof(isSuccess)} è true");
            }

            if (!isSuccess && errorDetail == null)
            {
                throw new InvalidOperationException($"{nameof(errorDetail)} non può essere null se {nameof(isSuccess)} è false");
            }

            this.IsSuccess = isSuccess;
            this.ErrorDetail = errorDetail;
        }
        /*
        public static Result<TError> Success() => new Result<TError>(true, null);
        public static Task<Result<TError>> SuccessAsync() => Task.FromResult(new Result<TError>(true, null));
        public static Result<TError> Failure(TError errorDetails) => new Result<TError>(false, errorDetails);
        */
    }
}
