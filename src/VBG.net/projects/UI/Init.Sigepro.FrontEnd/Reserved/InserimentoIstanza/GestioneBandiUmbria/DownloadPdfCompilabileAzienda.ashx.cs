using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using Init.Sigepro.FrontEnd.AppLogic.PrecompilazionePDF;
using log4net;
using Ninject;
using Ninject.Web;
using Init.Sigepro.FrontEnd.AppLogic.Autenticazione.Vbg;
using Init.Sigepro.FrontEnd.AppLogic.Services.Navigation;
using Init.Sigepro.FrontEnd.AppLogic.GestioneBandiUmbria.CustomIstanzaStcAdapters;
using Init.Sigepro.FrontEnd.AppLogic.Adapters;

namespace Init.Sigepro.FrontEnd.Reserved.InserimentoIstanza.GestioneBandiUmbria
{
	/// <summary>
	/// Summary description for DownloadPdfCompilabileAzienda
	/// </summary>
	public class DownloadPdfCompilabileAzienda : HttpHandlerBase, IHttpHandler
	{
		private static class QuerystringConstants
		{
			public const string UserAuthenticationResult = "UserAuthenticationResult";
			public const string CodiceOggetto = "codiceOggetto";
			public const string IdDomanda = "idDomanda";
		}

		[Inject]
		protected IPdfUtilsService _pdfUtilsService { get; set; }

		[Inject]
		protected IIstanzaStcAdapter _istanzaStcAdapter { get; set; }

		ILog _log = LogManager.GetLogger(typeof(DownloadPdfCompilabile));
		HttpContext _context;

		public UserAuthenticationResult UserAuthenticationResult
		{
			get { return HttpContext.Current.Items[QuerystringConstants.UserAuthenticationResult] as UserAuthenticationResult; }
		}

		protected string UserToken
		{
			get { return UserAuthenticationResult.Token; }
		}

		protected int CodiceOggetto
		{
			get { return Convert.ToInt32(this._context.Request.QueryString[PathUtils.UrlParameters.CodiceOggetto]); }
		}

		protected int IdDomanda
		{
			get { return Convert.ToInt32(this._context.Request.QueryString[PathUtils.UrlParameters.IdPresentazione]); }
		}

		protected string CodiceFiscaleAzienda
		{
			get { return this._context.Request.QueryString[PathUtils.UrlParameters.CodiceFiscaleAzienda]; }
		}

		protected string PartitaIvaAzienda
		{
			get { return this._context.Request.QueryString[PathUtils.UrlParameters.PartitaIvaAzienda]; }
		}

		protected override void DoProcessRequest(HttpContext context)
		{
			try
			{
				this._context = context;

				var adapter = new GestioneBandiIstanzaStcAdapter(this.PartitaIvaAzienda, this.CodiceFiscaleAzienda, this._istanzaStcAdapter);

				var file = _pdfUtilsService.PrecompilaPdf(CodiceOggetto, IdDomanda, adapter);

				context.Response.AddHeader("Content-Disposition", "attachment; filename=\"" + file.FileName + "\"");
				context.Response.ContentType = file.MimeType;
				context.Response.BinaryWrite(file.FileContent);
			}
			catch (Exception ex)
			{
				_log.ErrorFormat("Errore durante la precompilazione di un modello pdf: codiceoggetto={0}, iddomanda={1}, token={2}, errore={3}", CodiceOggetto, IdDomanda, UserToken, ex.ToString());

				context.Response.ContentType = "text/plain";
				context.Response.Write("Si è verificato un errore durante la preparazione del file. Contattare l'assistenza");
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