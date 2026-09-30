using Init.Sigepro.FrontEnd.AppLogic.GestioneOggetti;
using Init.Sigepro.FrontEnd.AppLogic.VerificaFirmaDigitale;
using Init.Sigepro.FrontEnd.WebForms.AppLogic.GestioneOggetti;
using Init.Sigepro.FrontEnd.WebForms.AppLogic.GestioneOggetti.PostedFileSpecifications;
using Init.Sigepro.FrontEnd.GestioneMovimenti.Commands;
using Init.Sigepro.FrontEnd.Infrastructure.Dispatching;
using Ninject;
using System;

namespace Init.Sigepro.FrontEnd.Reserved.GestioneMovimenti.Helper.FileUploadHandlers
{
    /// <summary>
    /// Summary description for UploadHandler
    /// </summary>
    public class UploadHandler : MovimentiFileUploadHandler
    {
        public static class Constants
        {
            public const string VerificaFirmaDigitale = "firma";
            public const string DimensioneMassima = "max";
            public const string EstensioniAmmesse = "es";
        }

        private bool VerificaFirmaDigitale
        {
            get
            {
                var qs = this.Context.Request.QueryString[Constants.VerificaFirmaDigitale];

                if (String.IsNullOrEmpty(qs))
                {
                    return false;
                }

                return string.Equals(qs, "true", StringComparison.InvariantCultureIgnoreCase);
            }
        }

        private int DimensioneMassima => Convert.ToInt32(this.Context.Request.QueryString[Constants.DimensioneMassima]);

        private string EstensioniAmmesse => this.Context.Request.QueryString[Constants.EstensioniAmmesse];

        [Inject]
        public ValidPostedFileSpecification _defaultPostedFileSpecification { get; set; }
        [Inject]
        protected EventsBus _bus { get; set; }
        [Inject]
        protected IOggettiService _oggettiService { get; set; }
        [Inject]
        public ValidPostedFileSpecification _validPostedFileSpecification { get; set; }
        [Inject]
        public IVerificaFirmaDigitaleService _firmaDigitaleService { get; set; }

        public override void DoProcessRequestInternal()
        {
            try
            {
                if (this.Context.Request.Files.Count > 1)
                    throw new Exception("Errore interno (è stato caricato più di un file)");

                if (this.Context.Request.Files.Count == 0 || this.Context.Request.Files[0].ContentLength == 0)
                    throw new Exception("Nessun file caricato");

                var specification = (IValidPostedFileSpecification)this._defaultPostedFileSpecification;

                if (this.DimensioneMassima > 0)
                {
                    specification = new SizeBasedValidPostedFileSpecification(this.DimensioneMassima * 1024);
                }

                specification = specification.And(new EstensioneValidaPostedFileSpecification(this.EstensioniAmmesse));

                var file = new WebFormsBinaryFile(this.Context.Request.Files[0], specification);

                if (this.VerificaFirmaDigitale)
                {
                    var esitoVerifica = this._firmaDigitaleService.VerificaFirmaDigitale(file);
                    var firmatoDigitalmente = new FirmaValidaSpecification().IsSatisfiedBy(esitoVerifica);

                    if (!firmatoDigitalmente)
                    {
                        throw new FirmaDigitaleNonValidaException($"Il file {file.FileName} non contiene firme digitali valide");
                    }
                }

                var codiceOggetto = this._oggettiService.InserisciOggetto(file);

                var cmd = new AggiungiAllegatoAlMovimento(this.Alias, this.IdMovimento, codiceOggetto, file.FileName, "Allegato delle schede dinamiche");

                this._bus.Send(cmd);

                var obj = new
                {
                    codiceOggetto = codiceOggetto,
                    fileName = file.FileName,
                    length = file.Size,
                    mime = file.MimeType
                };

                this.SerializeResponse(obj);
            }
            catch (Exception ex)
            {
                this.SerializeResponse(new { Errori = ex.Message });
            }
        }
    }
}