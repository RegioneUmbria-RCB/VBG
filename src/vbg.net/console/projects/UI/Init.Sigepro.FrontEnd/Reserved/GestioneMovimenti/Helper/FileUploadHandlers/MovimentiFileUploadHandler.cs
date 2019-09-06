using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.SessionState;
using System.Web.Script.Serialization;

namespace Init.Sigepro.FrontEnd.Reserved.GestioneMovimenti.Helper.FileUploadHandlers
{
	public class MovimentiFileUploadHandler:  Ninject.Web.HttpHandlerBase, IRequiresSessionState
	{
		protected HttpContext Context;

		public int IdMovimento
		{
			get
			{
				return Convert.ToInt32(Context.Request.QueryString["IdMovimento"]);
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