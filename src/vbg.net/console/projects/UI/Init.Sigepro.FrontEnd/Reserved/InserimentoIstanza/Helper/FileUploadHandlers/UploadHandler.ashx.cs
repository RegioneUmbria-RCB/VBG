using System;
using Init.Sigepro.FrontEnd.AppLogic.AllegatiDomanda;
using Init.Sigepro.FrontEnd.AppLogic.GestioneOggetti;
using Ninject;
using System.Web;

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
        }

        bool VerificaFirmaDigitale
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

		[Inject]
		public IAllegatiDomandaFoRepository _allegatiDomandaFoRepository { get; set; }

		[Inject]
		public ValidPostedFileSpecification _validPostedFileSpecification { get; set; }

		public override void DoProcessRequestInternal()
		{
			try
			{

				if (this.Context.Request.Files.Count > 1)
					throw new Exception("Errore interno (è stato caricato più di un file)");

				if (this.Context.Request.Files.Count == 0 || this.Context.Request.Files[0].ContentLength == 0)
					throw new Exception("Nessun file caricato");

				var file = new BinaryFile(this.Context.Request.Files[0], _validPostedFileSpecification);

                var salvataggioResult = _allegatiDomandaFoRepository.SalvaAllegato(IdDomanda, file, VerificaFirmaDigitale);

				var obj = new
				{
					codiceOggetto = salvataggioResult.CodiceOggetto,
					fileName = file.FileName,
					length = file.Size,
					mime = file.MimeType
				};

				SerializeResponse(obj);
			}
			catch (Exception ex)
			{
				SerializeResponse(new { Errori = ex.Message });
			}
		}
	}
}