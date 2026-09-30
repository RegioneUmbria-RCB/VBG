using Microsoft.AspNetCore.Components.Forms;

namespace Init.Sigepro.FrontEnd.CoreServices.GestioneOggetti
{
    public class NonZeroPostedFileSpecification : IValidPostedFileSpecification, IValidPostedFileSpecificationAsync
    {
        public string ErrorMessage => "Il file inviato è vuoto oppure non è stato possibile leggerne il contenuto";

        public bool IsSatisfiedBy(IBrowserFile item)
        {
            return item.Size > 0;
        }

        public Task<bool> IsSatisfiedByAsync(IBrowserFile item)
        {
            return Task.FromResult(item.Size > 0);
        }
    }
}
