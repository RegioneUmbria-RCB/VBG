using Microsoft.AspNetCore.Components.Forms;

namespace Init.Sigepro.FrontEnd.CoreServices.GestioneOggetti
{
    public class CompositePostedFileSpecification : IValidPostedFileSpecification, IValidPostedFileSpecificationAsync
    {
        private readonly List<IValidPostedFileSpecification> _validators = new List<IValidPostedFileSpecification>();
        private readonly List<IValidPostedFileSpecificationAsync> _validatorsAsync = new List<IValidPostedFileSpecificationAsync>();

        public string ErrorMessage { get; private set; } = "";

        public bool IsSatisfiedBy(IBrowserFile item)
        {
            this.ErrorMessage = "";

            foreach (var validator in this._validators)
            {
                if (!validator.IsSatisfiedBy(item))
                {
                    this.ErrorMessage = validator.ErrorMessage;
                    return false;
                }
            }

            return true;
        }

        public void Add(IValidPostedFileSpecification specification)
        {
            this._validators.Add(specification);
        }
        public void AddAsyncSpecification(IValidPostedFileSpecificationAsync specification)
        {
            this._validatorsAsync.Add(specification);
        }

        public async Task<bool> IsSatisfiedByAsync(IBrowserFile item)
        {
            this.ErrorMessage = "";

            foreach (var validator in this._validatorsAsync)
            {
                var validatorResult = await validator.IsSatisfiedByAsync(item);
                if (!validatorResult)
                {
                    this.ErrorMessage = validator.ErrorMessage;
                    return false;
                }
            }

            return true;
        }
    }
}
