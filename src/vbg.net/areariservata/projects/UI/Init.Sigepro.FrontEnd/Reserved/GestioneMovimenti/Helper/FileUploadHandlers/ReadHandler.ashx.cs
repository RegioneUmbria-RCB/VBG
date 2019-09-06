using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using Init.Sigepro.FrontEnd.AppLogic.GestioneOggetti;
using Ninject;

namespace Init.Sigepro.FrontEnd.Reserved.GestioneMovimenti.Helper.FileUploadHandlers
{
	/// <summary>
	/// Summary description for ReadHandler
	/// </summary>
	public class ReadHandler : MovimentiFileUploadHandler
	{
		[Inject]
		protected IOggettiRepository OggettiRepository{ get; set; }

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
				var file = OggettiRepository.GetOggetto(Alias, CodiceOggetto);

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