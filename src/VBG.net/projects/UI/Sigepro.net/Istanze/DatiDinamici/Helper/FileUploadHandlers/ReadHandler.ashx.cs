using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Script.Serialization;
using System.Threading;
using Init.SIGePro.Authentication;
using Init.SIGePro.Manager.Logic.OggettiLogic;

namespace Sigepro.net.Istanze.DatiDinamici.Helper.FileUpload
{
	/// <summary>
	/// Summary description for ReadHandler
	/// </summary>
	public class ReadHandler : IHttpHandler
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

				var datiOggetto = new OggettiServiceProxy(authInfo.CreateDatabase()).GetById(CodiceOggetto);

				if (datiOggetto == null)
					throw new Exception("L'oggetto identificato dal codiceoggetto " + CodiceOggetto + " non è stato trovato");

				var obj = new
				{
					codiceOggetto = CodiceOggetto,
					nomeFile = datiOggetto.NOMEFILE,
					size = datiOggetto.OGGETTO.Length,
					mime = ""
				};

				SerializeResponse(obj);
			}
			catch (Exception ex)
			{
				SerializeResponse(new { Errori=ex.Message});
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