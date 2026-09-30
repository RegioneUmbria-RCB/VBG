using System;
using System.Data;
using System.Web;
using System.Collections;
using System.Web.Services;
using System.Web.Services.Protocols;
using System.Web.Script.Serialization;
using Init.SIGePro.Data;

namespace Sigepro.net.Archivi.Modelli.Endpoints
{
	/// <summary>
	/// Summary description for $codebehindclassname$
	/// </summary>
	[WebService(Namespace = "http://tempuri.org/")]
	[WebServiceBinding(ConformsTo = WsiProfiles.BasicProfile1_1)]
	public class ModelliAlbero : IHttpHandler
	{

		public void ProcessRequest(HttpContext context)
		{
			SetNoCache(context);

			string tokenId = context.Request.QueryString["Token"];

			// TODO: verificare la validità del token...

			string tipoModelloId = context.Request.QueryString["TipoModello"];

			// TODO: leggere il tipo da analizzare dal db...

			// HACK: i tipi suttportati per ora vengono messi in un array
			int iTipoModello = String.IsNullOrEmpty( tipoModelloId ) ? 0 : int.Parse(tipoModelloId);
			Type[] tipiAnalizzati = { typeof(Init.SIGePro.Data.Istanze), typeof(Anagrafe) };

			if (iTipoModello > tipiAnalizzati.Length - 1)
				iTipoModello = 0;

			GeneratoreAlbero ca = new GeneratoreAlbero(tipiAnalizzati[iTipoModello]);
			string jsonString = new JavaScriptSerializer().Serialize(ca.AnalizzaAlberoClasse());
			context.Response.Write(jsonString);
		}

		protected void SetNoCache(HttpContext context)
		{
			context.Response.ContentType = "application/json";
			context.Response.Expires = 0;
			context.Response.Cache.SetNoStore();
		}

		public bool IsReusable
		{
			get
			{
				return false;
			}
		}
	}
}
