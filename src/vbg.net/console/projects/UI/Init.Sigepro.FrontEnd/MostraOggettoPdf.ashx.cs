using System;
using System.IO;
using System.Web;
using System.Web.Services;
using Init.Sigepro.FrontEnd.AppLogic.Repositories.Interfaces;
using Init.Sigepro.FrontEnd.AppLogic.Services.ConversioneDomanda;
using Ninject;
using Init.Sigepro.FrontEnd.AppLogic.GestioneOggetti;


namespace Init.Sigepro.FrontEnd
{
	/// <summary>
	/// Summary description for $codebehindclassname$
	/// </summary>
	[WebService(Namespace = "http://tempuri.org/")]
	[WebServiceBinding(ConformsTo = WsiProfiles.BasicProfile1_1)]
	public class MostraOggettoPdf : Ninject.Web.HttpHandlerBase
	{
		[Inject]
		public IOggettiService _oggettiService { get; set; }
		[Inject]
		public FileConverterService _fileConverterService { get; set; }

		protected override void DoProcessRequest(HttpContext context)
		{
			var codiceOggetto	= Convert.ToInt32(context.Request.QueryString["id"]);

			var convResult = _fileConverterService.Converti( codiceOggetto, _oggettiService, "PDF");

			context.Response.ContentType = convResult.MimeType;
			context.Response.AddHeader("content-disposition", "attachment; filename=\"" + convResult.FileName + "\"");
			context.Response.BinaryWrite(convResult.FileContent);
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
