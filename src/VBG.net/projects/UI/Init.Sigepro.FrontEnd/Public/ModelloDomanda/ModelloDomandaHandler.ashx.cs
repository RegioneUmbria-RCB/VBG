using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.SessionState;
using Ninject;
using Init.Sigepro.FrontEnd.AppLogic.GenerazioneRiepilogoDomanda;
using Init.Sigepro.FrontEnd.AppLogic.Services.Domanda;
using System.Text;

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
		GenerazioneRiepilogoDomandaService.FormatoConversioneModello _formato = GenerazioneRiepilogoDomandaService.FormatoConversioneModello.HTML;

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

			if (!String.IsNullOrEmpty(context.Request.QueryString["pdf"]))
			{
				this._formato = GenerazioneRiepilogoDomandaService.FormatoConversioneModello.PDF;
			}

			var riepilogo = _generazioneRiepilogoDomandaService.GeneraModelloDiDomanda(this._intervento, this._endo, this._formato);

			context.Response.Clear();
			context.Response.ContentType = riepilogo.MimeType;
			context.Response.ContentEncoding = Encoding.Default;

			if (this._formato == GenerazioneRiepilogoDomandaService.FormatoConversioneModello.PDF)
			{
				context.Response.AddHeader("content-disposition", "attachment; filename=ModelloDomanda.pdf;");
			}

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