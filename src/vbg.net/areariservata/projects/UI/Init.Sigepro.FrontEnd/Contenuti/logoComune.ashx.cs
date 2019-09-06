using System.IO;
using System.Web;
using System.Web.SessionState;
using Init.Sigepro.FrontEnd.AppLogic.GestioneContenuti;
using Init.Sigepro.FrontEnd.AppLogic.Repositories.Interfaces;
using Init.Utils;
using Ninject;
using Init.Sigepro.FrontEnd.AppLogic.GestioneOggetti;

namespace Init.Sigepro.FrontEnd.Contenuti
{
	/// <summary>
	/// Summary description for $codebehindclassname$
	/// </summary>
	public class logoComune : Ninject.Web.HttpHandlerBase, IReadOnlySessionState
	{
		[Inject]
		public IOggettiService _oggettiService { get; set; }
		[Inject]
		protected ConfigurazioneContenuti _configurazione { get; set; }


		public string IdComune {
			get { return HttpContext.Current.Request.QueryString["alias"]; }
		}

		protected override void DoProcessRequest(HttpContext context)
		{
			byte[] data = null;

			var basePath = context.Server.MapPath("~/Contenuti/Risorse/");

			var pathComune = Path.Combine(basePath, IdComune + ".png");

			if (!File.Exists(pathComune))
			{
				var idLogo = _configurazione.DatiComune.CodiceOggetoLogo;

				if (idLogo.HasValue)
				{
					var ogg = _oggettiService.GetById(idLogo.Value);

					context.Response.Clear();
					context.Response.ContentType = ogg.MimeType;
					context.Response.BinaryWrite(ogg.FileContent);
					return;
				}

				pathComune = Path.Combine(basePath, "default.png");
			}

			var ms = StreamUtils.FileToStream(pathComune);

			data = ms.ToArray();

			context.Response.ContentType = "image/png";
			context.Response.BinaryWrite(data);

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
