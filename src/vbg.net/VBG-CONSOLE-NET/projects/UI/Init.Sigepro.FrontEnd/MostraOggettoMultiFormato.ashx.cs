using System;
using System.Web;
using Ninject;
using Init.Sigepro.FrontEnd.AppLogic.GestioneOggetti;
using Init.Sigepro.FrontEnd.AppLogic.GestioneConversioneFiles;

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
            var noConv = context.Request.QueryString["noconv"];

			if (String.IsNullOrEmpty(fmt))
				fmt = "PDF";

			fmt = fmt.ToUpper();

			try
			{
                BinaryFile file = null;

                if (!String.IsNullOrEmpty(noConv))
                {
                    file = this._oggettiService.GetById(codiceOggetto);
                }
                else
                {
					if (!Enum.TryParse(fmt, out FormatoConversioneEnum formato))
					{
						throw new Exception("Formato non valido");
					}


					file = _fileConverterService.Converti(codiceOggetto, this._oggettiService, formato);
                }

                context.Response.ContentType = file.MimeType;
                context.Response.AddHeader("content-disposition", "attachment; filename=\"" + file.FileName + "\"");
                context.Response.BinaryWrite(file.FileContent);
			}
			catch(Exception ex)
			{
				context.Response.StatusCode = (int)System.Net.HttpStatusCode.InternalServerError;
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