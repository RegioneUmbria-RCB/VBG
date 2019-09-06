using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using Ninject;
using Init.Sigepro.FrontEnd.AppLogic.GestioneOggetti;

namespace Init.Sigepro.FrontEnd.Reserved.GestioneMovimenti.Helper.FileUploadHandlers
{
	/// <summary>
	/// Summary description for DownloadHandler
	/// </summary>
	public class DownloadHandler : MovimentiFileUploadHandler
	{

		[Inject]
		public IOggettiRepository OggettiRepository{ get; set; }

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
					throw new Exception("File " + CodiceOggetto + " non trovato");

				Context.Response.Clear();
				Context.Response.ContentType = file.MimeType;
				Context.Response.AddHeader("Content-Disposition", "attachment; filename=\"" + file.FileName + "\"");
				Context.Response.BinaryWrite(file.FileContent);
			}
			catch (Exception ex)
			{
				Context.Response.Clear();
				Context.Response.ContentType = "text/plain";
				Context.Response.Write("Errore durante la lettura del file specificato: " + ex.Message);
			}

		}
	}
}