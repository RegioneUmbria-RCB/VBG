using Init.Sigepro.FrontEnd.AppLogic.Utils;
using Microsoft.AspNetCore.Components.Forms;

namespace Init.Sigepro.FrontEnd.CoreServices.GestioneOggetti
{
    public class SizeBasedValidPostedFileSpecification : IValidPostedFileSpecification, IValidPostedFileSpecificationAsync
    {
        private readonly int _maxSize;

        public SizeBasedValidPostedFileSpecification(int maxSize)
        {
            this._maxSize = maxSize;
        }

        public string ErrorMessage
        {
            get { return $"Le dimensioni del file caricato superano le dimensioni massime consentite ({this._maxSize.GetHumanReadableFileSize()})"; }
        }

        public bool IsSatisfiedBy(IBrowserFile item)
        {
            if (this._maxSize == 0)
            {
                return true;
            }

            return item.Size <= this._maxSize;
        }

        public Task<bool> IsSatisfiedByAsync(IBrowserFile item)
        {
            if (this._maxSize == 0)
            {
                return Task.FromResult(true);
            }

            return Task.FromResult(item.Size <= this._maxSize);
        }
    }
}
