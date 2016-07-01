using System;
using Init.Sigepro.FrontEnd.AppLogic.AllegatiDomanda;
using Ninject;

namespace Init.Sigepro.FrontEnd.Reserved.InserimentoIstanza.Helper.FileUploadHandlers
{
	/// <summary>
	/// Summary description for ReadHandler
	/// </summary>
	public class ReadHandler : DatiDinamiciFileUploadHandlerBase
	{
		[Inject]
		public IAllegatiDomandaFoRepository AllegatiDomandaFoRepository { get; set; }

		private int CodiceOggetto
		{
			get
			{
				return Convert.ToInt32(this.Context.Request.QueryString["CodiceOggetto"]);
			}
		}

		public override void DoProcessRequestInternal()
		{
			try
			{
				var file = AllegatiDomandaFoRepository.LeggiAllegato(IdDomanda , CodiceOggetto);

				if (file == null)
					throw new Exception("L'oggetto identificato dal codiceoggetto " + CodiceOggetto + " non è stato trovato");

				var obj = new
				{
					codiceOggetto = CodiceOggetto,
					nomeFile = file.FileName,
					size = file.FileContent.Length,
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