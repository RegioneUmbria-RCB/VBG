using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using Init.SIGePro.Authentication;
using Init.SIGePro.Manager.Logic.OggettiLogic;

namespace Sigepro.net.Istanze.DatiDinamici.Helper.FileUploadHandlers
{
	/// <summary>
	/// Summary description for DownloadHandler
	/// </summary>
	public class DownloadHandler : IHttpHandler
	{

		HttpContext context;

		private int CodiceOggetto
		{
			get { return Convert.ToInt32(context.Request.QueryString["codiceOggetto"]); }
		}

		private string Token
		{
			get { return context.Request.QueryString["Token"]; }
		}

		public void ProcessRequest(HttpContext context)
		{
			ImpedisciCaching();

			this.context = context;

			try
			{

				var authInfo = AuthenticationManager.CheckToken(Token);

				if (authInfo == null)
					throw new Exception("Token non valido o non impostato");

				var datiOggetto = new OggettiServiceProxy(authInfo.CreateDatabase()).GetByIdNativo(CodiceOggetto);

				if (datiOggetto == null)
					throw new Exception("L'oggetto identificato dal codiceoggetto " + CodiceOggetto + " non è stato trovato");

				context.Response.Clear();
				context.Response.ContentType = datiOggetto.mimeType;
				context.Response.AddHeader("Content-Disposition","attachment; filename=\"" + datiOggetto.fileName + "\"");
				context.Response.BinaryWrite(datiOggetto.binaryData); 

			}
			catch (Exception ex)
			{
				context.Response.Clear();
				context.Response.ContentType = "text/plain";
				context.Response.Write("Errore durante la lettura del file specificato: " + ex.Message);
			}
		}

		public bool IsReusable
		{
			get
			{
				return false;
			}
		}

		private void ImpedisciCaching()
		{
			HttpContext.Current.Response.Cache.SetCacheability(HttpCacheability.NoCache);
			HttpContext.Current.Response.Cache.SetNoServerCaching();
			HttpContext.Current.Response.Cache.SetNoStore();
			HttpContext.Current.Response.Cache.SetExpires(DateTime.Now.AddDays(-1));
		}
	}
}