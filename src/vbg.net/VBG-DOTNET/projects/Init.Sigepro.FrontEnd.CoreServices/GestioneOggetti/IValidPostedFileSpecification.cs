using Init.Sigepro.FrontEnd.Infrastructure;
using Microsoft.AspNetCore.Components.Forms;

namespace Init.Sigepro.FrontEnd.CoreServices.GestioneOggetti
{
    public interface IValidPostedFileSpecification : ISpecificationWithErrorMessage<IBrowserFile>
    {
    }

    public interface IValidPostedFileSpecificationAsync : ISpecificationWithErrorMessageAsync<IBrowserFile>
    {
    }

    public class NullValidPostedFileSpecificationAsync : IValidPostedFileSpecificationAsync
    {
        public string ErrorMessage => "";

        public Task<bool> IsSatisfiedByAsync(IBrowserFile item)
        {
            return Task.FromResult(true);
        }
    }

    public class AndSpecification : IValidPostedFileSpecification
    {
        private readonly IValidPostedFileSpecification _specificationOne;
        private readonly IValidPostedFileSpecification _specificationTwo;

        public string ErrorMessage { get; private set; } = "";

        internal AndSpecification(IValidPostedFileSpecification specificationOne,
          IValidPostedFileSpecification specificationTwo)
        {
            this._specificationOne = specificationOne;
            this._specificationTwo = specificationTwo;
        }

        public bool IsSatisfiedBy(IBrowserFile entity)
        {
            var firstSatisfied = this._specificationOne.IsSatisfiedBy(entity);
            var secondSatisfied = this._specificationTwo.IsSatisfiedBy(entity);
            var result = firstSatisfied && secondSatisfied;

            if (result)
            {
                return true;
            }

            if (!firstSatisfied)
            {
                this.ErrorMessage = this._specificationOne.ErrorMessage;
            }
            else
            {
                this.ErrorMessage = this._specificationTwo.ErrorMessage;
            }

            return false;
        }
    }

    public class AndSpecificationAsync : IValidPostedFileSpecificationAsync
    {
        private readonly IValidPostedFileSpecificationAsync _specificationOneAsync;
        private readonly IValidPostedFileSpecificationAsync _specificationTwoAsync;

        public string ErrorMessage { get; private set; } = "";
        internal AndSpecificationAsync(IValidPostedFileSpecificationAsync specificationOne,
          IValidPostedFileSpecificationAsync specificationTwo)
        {
            this._specificationOneAsync = specificationOne;
            this._specificationTwoAsync = specificationTwo;
        }

        public async Task<bool> IsSatisfiedByAsync(IBrowserFile entity)
        {
            var firstSatisfied = await this._specificationOneAsync.IsSatisfiedByAsync(entity);
            var secondSatisfied = await this._specificationTwoAsync.IsSatisfiedByAsync(entity);
            var result = firstSatisfied && secondSatisfied;

            if (result)
            {
                return true;
            }

            if (!firstSatisfied)
            {
                this.ErrorMessage = this._specificationOneAsync.ErrorMessage;
            }
            else
            {
                this.ErrorMessage = this._specificationTwoAsync.ErrorMessage;
            }

            return false;
        }
    }

    public static class IValidPostedFileSpecificationExtensions
    {
        public static IValidPostedFileSpecification And(this IValidPostedFileSpecification first, IValidPostedFileSpecification second)
        {
            return new AndSpecification(first, second);
        }

        public static IValidPostedFileSpecificationAsync And(this IValidPostedFileSpecificationAsync first, IValidPostedFileSpecificationAsync second)
        {
            return new AndSpecificationAsync(first, second);
        }
    }
}
