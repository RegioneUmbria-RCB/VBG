using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using Ninject;
using Init.Sigepro.FrontEnd.Infrastructure.Dispatching;
using Init.Sigepro.FrontEnd.AppLogic.GestioneOggetti;
using Init.Sigepro.FrontEnd.GestioneMovimenti.Commands;

namespace Init.Sigepro.FrontEnd.Reserved.GestioneMovimenti.Helper.FileUploadHandlers
{
	/// <summary>
	/// Summary description for UploadHandler
	/// </summary>
	public class UploadHandler : MovimentiFileUploadHandler
	{
		[Inject]
		protected EventsBus _bus { get; set; }

		[Inject]
		protected IOggettiService _oggettiService{ get; set; }

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

				var file = new BinaryFile(this.Context.Request.Files[0], this._validPostedFileSpecification);

				var codiceOggetto = this._oggettiService.InserisciOggetto(file.FileName, file.MimeType, file.FileContent);

				var cmd = new AggiungiAllegatoAlMovimento(Alias, IdMovimento, codiceOggetto, file.FileName, "Allegato delle schede dinamiche");

				this._bus.Send(cmd);

				
				var obj = new
				{
					codiceOggetto = codiceOggetto,
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