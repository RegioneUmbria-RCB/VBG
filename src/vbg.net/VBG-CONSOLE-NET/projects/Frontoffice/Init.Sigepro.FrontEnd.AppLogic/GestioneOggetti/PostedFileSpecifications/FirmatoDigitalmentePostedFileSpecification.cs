using Init.Sigepro.FrontEnd.AppLogic.VerificaFirmaDigitale;
using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Text;
using System.Web;

namespace Init.Sigepro.FrontEnd.AppLogic.GestioneOggetti.PostedFileSpecifications
{
    public class FirmatoDigitalmentePostedFileSpecification : IValidPostedFileSpecification
    {
        private readonly IVerificaFirmaDigitaleService _firmaDigitaleService;

        public FirmatoDigitalmentePostedFileSpecification(IVerificaFirmaDigitaleService firmaDigitaleService)
        {
            _firmaDigitaleService = firmaDigitaleService;
        }

        public string ErrorMessage => "Il file non è firmato digitalmente, firmare e ricaricare il file.";

        public bool IsSatisfiedBy(HttpPostedFile item)
        {
            var memStream = new MemoryStream(item.ContentLength);
            item.InputStream.CopyTo(memStream);
            var esito = _firmaDigitaleService.VerificaFirmaDigitale(BinaryFile.FromFileData(item.FileName, item.ContentType, memStream.GetBuffer()));

            return esito.Stato == StatoVerificaFirma.FirmaValida;
        }
    }
}
