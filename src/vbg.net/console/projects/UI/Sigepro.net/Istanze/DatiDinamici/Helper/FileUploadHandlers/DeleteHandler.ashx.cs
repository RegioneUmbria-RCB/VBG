using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Script.Serialization;
using System.Threading;

namespace Sigepro.net.Istanze.DatiDinamici.Helper.FileUploadHandlers
{
	/// <summary>
	/// Summary description for DeleteHandler
	/// </summary>
	public class DeleteHandler : IHttpHandler
	{
		HttpContext context;

		public void ProcessRequest(HttpContext context)
		{
			ImpedisciCaching();

			this.context = context;
			var codiceOggetto = Convert.ToInt32(context.Request.QueryString["codiceOggetto"]);

			//Thread.Sleep(1000);

			SerializeResponse(new { ok = true });
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