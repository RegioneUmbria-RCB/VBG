using System;
using System.Data;
using System.Web;
using System.Collections;
using System.Web.Services;
using System.Web.Services.Protocols;
using Init.Sigepro.FrontEnd.AppLogic.ObjectSpace;
using System.Web.SessionState;
using Ninject;
using Init.Sigepro.FrontEnd.AppLogic.Repositories.Interfaces;
using Init.Sigepro.FrontEnd.Infrastructure.Caching;
using Init.Sigepro.FrontEnd.AppLogic.GestioneOggetti;

namespace Init.Sigepro.FrontEnd
{
	/// <summary>
	/// Summary description for $codebehindclassname$
	/// </summary>
	[WebService(Namespace = "http://tempuri.org/")]
	[WebServiceBinding(ConformsTo = WsiProfiles.BasicProfile1_1)]
	public class MostraRisorsa : Ninject.Web.HttpHandlerBase, IReadOnlySessionState
	{
		[Inject]
		public IOggettiService _oggettiService { get; set; }

		protected override void DoProcessRequest(HttpContext context)
		{
			context.Response.Cache.SetExpires(DateTime.Now.AddHours(1));

			string idComune  = context.Request.QueryString["IdComune"];
			string idRisorsa = context.Request.QueryString["IdRisorsa"];

			try
			{
				if (string.IsNullOrEmpty(idComune))
					throw new Exception("IdComune non impostato");

				if (string.IsNullOrEmpty(idRisorsa))
					throw new Exception("Id risorsa non impostato");

				var file = _oggettiService.GetRisorsaFrontoffice(idRisorsa);

				context.Response.ContentType = file.MimeType;
				context.Response.BinaryWrite(file.FileContent);
			}
			catch (Exception ex)
			{
				context.Response.ContentType = "text/plain";
				context.Response.Write(ex.Message);
			}
		}

		public override bool IsReusable
		{
			get
			{
				return false;
			}
		}
	}
}
