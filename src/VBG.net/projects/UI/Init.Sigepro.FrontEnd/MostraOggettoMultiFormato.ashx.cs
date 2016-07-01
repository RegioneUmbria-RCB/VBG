using System;
using System.IO;
using System.Web;
using Init.Sigepro.FrontEnd.AppLogic.Repositories.Interfaces;
using Init.Sigepro.FrontEnd.AppLogic.Services.ConversioneDomanda;
using Ninject;
using Init.Sigepro.FrontEnd.AppLogic.GestioneOggetti;



namespace Init.Sigepro.FrontEnd
{
	/// <summary>
	/// Summary description for MostraOggettoMultiFormato
	/// </summary>
	public class MostraOggettoMultiFormato : Ninject.Web.HttpHandlerBase
	{
		[Inject]
		public IOggettiService _oggettiService { get; set; }
		[Inject]
		public FileConverterService _fileConverterService { get; set; }

		protected override void DoProcessRequest(HttpContext context)
		{
			var codiceOggetto = Convert.ToInt32(context.Request.QueryString["id"]);
			var fmt = context.Request.QueryString["fmt"];

			if (String.IsNullOrEmpty(fmt))
				fmt = "PDF";

			fmt = fmt.ToUpper();

			try
			{
				var convResult = _fileConverterService.Converti( codiceOggetto, this._oggettiService, fmt);

				context.Response.ContentType = convResult.MimeType;
				context.Response.AddHeader("content-disposition", "attachment; filename=\"" + convResult.FileName + "\"");
				context.Response.BinaryWrite(convResult.FileContent);
			}
			catch(Exception ex)
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