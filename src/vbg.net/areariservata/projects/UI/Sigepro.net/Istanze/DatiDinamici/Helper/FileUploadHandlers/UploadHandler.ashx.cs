using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Script.Serialization;
using System.Threading;
using Init.SIGePro.Authentication;
using Init.SIGePro.Manager;
using Init.SIGePro.Data;
using System.IO;

namespace Sigepro.net.Istanze.DatiDinamici.Helper.FileUpload
{
	/// <summary>
	/// Summary description for UploadHandler
	/// </summary>
	public class UploadHandler : IHttpHandler
	{
		HttpContext context;

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
				if (context.Request.Files.Count > 1)
					throw new Exception("Errore interno (è stato caricato più di un file)");

				if (context.Request.Files.Count == 0 || context.Request.Files[0].ContentLength == 0)
					throw new Exception ("Nessun file caricato" );

				var file = context.Request.Files[0];

				var authInfo = AuthenticationManager.CheckToken(Token);

				if (authInfo == null)
					throw new Exception( "Token non valido o non impostato" );

				var oggettiMgr = new OggettiMgr(authInfo.CreateDatabase());

				var oggettoInserito = oggettiMgr.Insert(authInfo.IdComune, file);

				var obj = new
				{
					codiceOggetto = Convert.ToInt32(oggettoInserito.CODICEOGGETTO),
					fileName = oggettoInserito.NOMEFILE,
					length = file.ContentLength,
					mime = file.ContentType
				};

				SerializeResponse(obj);

			}
			catch (Exception ex)
			{
				SerializeResponse(new { Errori = ex.Message });
			}

			
		}

		public bool IsReusable
		{
			get
			{
				return false;
			}
		}

		private void SerializeResponse(object result)
		{
			JavaScriptSerializer jss = new JavaScriptSerializer();
			var responseText = jss.Serialize(result);

			this.context.Response.ContentType = "text/plain";
			this.context.Response.Write(responseText);
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