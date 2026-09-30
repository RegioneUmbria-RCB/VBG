using Init.Sigepro.FrontEnd.AppLogic.GenerazioneDocumentiDomanda.GenerazioneRiepilogoDomanda;
using Ninject;
using System;
using System.Linq;
using System.Text;
using System.Web;
using System.Web.SessionState;

namespace Init.Sigepro.FrontEnd.Public.ModelloDomanda
{
	/// <summary>
	/// Summary description for ModelloDomandaHandler
	/// </summary>
	public class ModelloDomandaHandler : Ninject.Web.HttpHandlerBase, IReadOnlySessionState
	{
		string _idComune;
		int _intervento;
		int[] _endo = new int[0];

		[Inject]
		protected GenerazioneRiepilogoDomandaService _generazioneRiepilogoDomandaService { get; set; }
        
		protected override void DoProcessRequest(HttpContext context)
		{
			this._idComune = context.Request.QueryString["idComune"];
			this._intervento = Convert.ToInt32(context.Request.QueryString["Intervento"]);

			if (!String.IsNullOrEmpty(context.Request.QueryString["Endo"]))
			{
				this._endo = context.Request.QueryString["Endo"].Split(',').Select(x => Int32.Parse(x)).ToArray();
			}

			var riepilogo = _generazioneRiepilogoDomandaService.GeneraFacSimileDomanda(this._intervento, this._endo);

			context.Response.Clear();
			context.Response.ContentType = riepilogo.MimeType;
			context.Response.ContentEncoding = Encoding.Default;

            context.Response.AddHeader("content-disposition", "attachment; filename=fac-simile-domanda.pdf;");
			context.Response.BinaryWrite(riepilogo.FileContent);
			context.Response.End();
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