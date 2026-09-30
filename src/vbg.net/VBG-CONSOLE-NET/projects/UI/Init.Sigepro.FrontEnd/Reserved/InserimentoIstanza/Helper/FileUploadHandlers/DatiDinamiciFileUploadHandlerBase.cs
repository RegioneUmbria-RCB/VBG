using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Script.Serialization;
using System.Web.SessionState;
using Init.Sigepro.FrontEnd.AppLogic.Common;

namespace Init.Sigepro.FrontEnd.Reserved.InserimentoIstanza.Helper.FileUploadHandlers
{
	public class DatiDinamiciFileUploadHandlerBase : Ninject.Web.HttpHandlerBase, IRequiresSessionState
	{
		protected HttpContext Context;

		public int IdDomanda
		{
			get
			{
				return Convert.ToInt32(Context.Request.QueryString["IdPresentazione"]);
			}
		}

		public string Alias
		{
			get
			{
				return Context.Request.QueryString["IdComune"];
			}
		}


		public virtual void DoProcessRequestInternal()
		{
		}

		private void ImpedisciCaching()
		{
			HttpContext.Current.Response.Cache.SetCacheability(HttpCacheability.NoCache);
			HttpContext.Current.Response.Cache.SetNoServerCaching();
			HttpContext.Current.Response.Cache.SetNoStore();
			HttpContext.Current.Response.Cache.SetExpires(DateTime.Now.AddDays(-1));
		}

		protected void SerializeResponse(object result)
		{
			JavaScriptSerializer jss = new JavaScriptSerializer();
			var responseText = jss.Serialize(result);

			this.Context.Response.ContentType = "text/plain";
			this.Context.Response.Write(responseText);
		}

		protected override void DoProcessRequest(HttpContext context)
		{
			this.Context = context;

			ImpedisciCaching();

			DoProcessRequestInternal();
		}

		public override bool IsReusable
		{
			get { return false; }
		}
	}
}