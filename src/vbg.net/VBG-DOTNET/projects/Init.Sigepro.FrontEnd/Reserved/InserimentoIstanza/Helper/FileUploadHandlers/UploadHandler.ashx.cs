using Init.Sigepro.FrontEnd.AppLogic.Utils;
using Init.Sigepro.FrontEnd.WebForms.AppLogic.GestioneOggetti;
using Init.Sigepro.FrontEnd.WebForms.AppLogic.GestioneOggetti.PostedFileSpecifications;
using Init.Sigepro.FrontEnd.Infrastructure;
using Ninject;
using System;
using Init.Sigepro.FrontEnd.AppLogic.GestioneAllegatiDomanda;

namespace Init.Sigepro.FrontEnd.Reserved.InserimentoIstanza.Helper.FileUploadHandlers
{
    /// <summary>
    /// Summary description for UploadHandler
    /// </summary>
    public class UploadHandler : DatiDinamiciFileUploadHandlerBase
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

                return qs.ToUpperInvariant() == "TRUE";
            }
        }

        private int DimensioneMassima => Convert.ToInt32(this.Context.Request.QueryString[Constants.DimensioneMassima]);

        private string EstensioniAmmesse => this.Context.Request.QueryString[Constants.EstensioniAmmesse];


        [Inject]
        public IAllegatiDomandaFoRepository _allegatiDomandaFoRepository { get; set; }

        [Inject]
        public ValidPostedFileSpecification _defaultPostedFileSpecification { get; set; }

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

                var salvataggioResult = this._allegatiDomandaFoRepository.SalvaAllegato(this.IdDomanda, file, this.VerificaFirmaDigitale);

                var obj = new
                {
                    codiceOggetto = salvataggioResult.CodiceOggetto,
                    fileName = file.FileName,
                    length = file.Size.GetHumanReadableFileSize(),
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