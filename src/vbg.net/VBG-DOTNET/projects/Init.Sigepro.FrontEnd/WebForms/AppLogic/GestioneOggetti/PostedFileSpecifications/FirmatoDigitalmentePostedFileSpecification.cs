using Init.Sigepro.FrontEnd.AppLogic.VerificaFirmaDigitale;
using System.IO;
using System.Web;

namespace Init.Sigepro.FrontEnd.WebForms.AppLogic.GestioneOggetti.PostedFileSpecifications
{
    public class FirmatoDigitalmentePostedFileSpecification : IValidPostedFileSpecification
    {
        private readonly IVerificaFirmaDigitaleService _firmaDigitaleService;

        public FirmatoDigitalmentePostedFileSpecification(IVerificaFirmaDigitaleService firmaDigitaleService)
        {
            this._firmaDigitaleService = firmaDigitaleService;
        }

        public string ErrorMessage => "Il file non è firmato digitalmente, firmare e ricaricare il file.";

        public bool IsSatisfiedBy(HttpPostedFile item)
        {
            var memStream = new MemoryStream(item.ContentLength);
            item.InputStream.CopyTo(memStream);
            item.InputStream.Seek(0, SeekOrigin.Begin);
            var esito = this._firmaDigitaleService.VerificaFirmaDigitale(WebFormsBinaryFile.FromFileData(item.FileName, item.ContentType, memStream.GetBuffer()));

            return esito.Stato == StatoVerificaFirma.FirmaValida;
        }
    }
}
