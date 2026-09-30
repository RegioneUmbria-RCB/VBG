using Init.Sigepro.FrontEnd.Infrastructure;
using System.Web;

namespace Init.Sigepro.FrontEnd.WebForms.AppLogic.GestioneOggetti
{
    public interface IValidPostedFileSpecification : ISpecificationWithErrorMessage<HttpPostedFile>
    {
    }

    public class AndSpecification : IValidPostedFileSpecification
    {
        private readonly IValidPostedFileSpecification _specificationOne;
        private readonly IValidPostedFileSpecification _specificationTwo;
        private string _errorMessage = "";

        public string ErrorMessage => this._errorMessage;

        internal AndSpecification(IValidPostedFileSpecification specificationOne,
          IValidPostedFileSpecification specificationTwo)
        {
            this._specificationOne = specificationOne;
            this._specificationTwo = specificationTwo;
        }

        public bool IsSatisfiedBy(HttpPostedFile entity)
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
                this._errorMessage = this._specificationOne.ErrorMessage;
            }
            else
            {
                this._errorMessage = this._specificationTwo.ErrorMessage;
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
    }
}