using Init.Sigepro.FrontEnd.AppLogic.VerificaFirmaDigitale;
using Microsoft.AspNetCore.Components.Forms;

namespace Init.Sigepro.FrontEnd.CoreServices.GestioneOggetti
{
    public class FirmatoDigitalmentePostedFileSpecification : IValidPostedFileSpecification, IValidPostedFileSpecificationAsync
    {
        //private readonly IHostingEnvironment _webHostEnvironment;
        //private readonly IWebHostEnvironment __webHostEnvironment;

        private readonly IVerificaFirmaDigitaleService _firmaDigitaleService;

        public FirmatoDigitalmentePostedFileSpecification(IVerificaFirmaDigitaleService firmaDigitaleService)
        {
            this._firmaDigitaleService = firmaDigitaleService;
            //this._webHostEnvironment = webHostEnvironment;
        }

        public string ErrorMessage => "Attenzione, il file deve essere firmato digitalmente";

        public bool IsSatisfiedBy(IBrowserFile item)
        {
            using (var memStream = new MemoryStream())
            {
                item.OpenReadStream(maxAllowedSize: 80 * 1024 * 1024).CopyTo(memStream);
                var esito = this._firmaDigitaleService.VerificaFirmaDigitale(WebFormsBinaryFile.FromFileData(item.Name, item.ContentType, memStream.GetBuffer()));

                return esito.Stato == StatoVerificaFirma.FirmaValida;
            }
        }

        public async Task<bool> IsSatisfiedByAsync(IBrowserFile item)
        {
            using (var memStream = new MemoryStream())
            {
                await item.OpenReadStream(maxAllowedSize: 80 * 1024 * 1024).CopyToAsync(memStream);
                var esito = await this._firmaDigitaleService.VerificaFirmaDigitaleAsync(WebFormsBinaryFile.FromFileData(item.Name, item.ContentType, memStream.GetBuffer()));

                return esito.Stato == StatoVerificaFirma.FirmaValida;
            }
        }
    }
}
