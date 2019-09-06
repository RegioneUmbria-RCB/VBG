using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.SessionState;
using Ninject;
using Init.Sigepro.FrontEnd.AppLogic.AllegatiDomanda;
using Init.Sigepro.FrontEnd.AppLogic.GestioneOggetti;
using Init.Sigepro.FrontEnd.AppLogic.GestioneFirma.FirmaGrafometrica;
using Init.Sigepro.FrontEnd.AppLogic.Services.Navigation;
using log4net;

namespace Init.Sigepro.FrontEnd.Reserved.InserimentoIstanza.FirmaGrafometrica
{
	/// <summary>
	/// Summary description for UploadHandler
	/// </summary>
	public class UploadHandler : Ninject.Web.HttpHandlerBase, IRequiresSessionState
	{
		[Inject]
		public FirmaGrafometricaService _service { get; set; }
		[Inject]
		public ValidPostedFileSpecification _validPostedFileSpecification { get; set; }


		ILog _log = LogManager.GetLogger(typeof(UploadHandler));

		protected HttpContext Context;

		public int IdDomanda
		{
			get
			{
				return Convert.ToInt32(Context.Request.QueryString[PathUtils.UrlParameters.IdPresentazione]);
			}
		}

		public int CodiceOggetto
		{
			get
			{
				return Convert.ToInt32(Context.Request.QueryString[PathUtils.UrlParameters.CodiceOggetto]);
			}
		}

		public string Alias
		{
			get
			{
				return Context.Request.QueryString[PathUtils.UrlParameters.IdComune];
			}
		}

		private void ImpedisciCaching()
		{
			HttpContext.Current.Response.Cache.SetCacheability(HttpCacheability.NoCache);
			HttpContext.Current.Response.Cache.SetNoServerCaching();
			HttpContext.Current.Response.Cache.SetNoStore();
			HttpContext.Current.Response.Cache.SetExpires(DateTime.Now.AddDays(-1));
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

		public void DoProcessRequestInternal()
		{
			BinaryFile file = null;

			try
			{
				if (this.Context.Request.Files.Count > 1)
					throw new Exception("Errore interno (è stato caricato più di un file)");

				if (this.Context.Request.Files.Count == 0 || this.Context.Request.Files[0].ContentLength == 0)
					throw new Exception("Nessun file caricato");

				file = new BinaryFile(this.Context.Request.Files[0], this._validPostedFileSpecification);

				this._log.DebugFormat("Inizio salvataggio del file firmato con firma grafometrica ({0} {1}Kb) per la domanda {2} (codiceoggetto: {3})", file.FileName, file.Size / 1024, IdDomanda, CodiceOggetto);

				this._service.AggiornaOggettoFirmato(IdDomanda, CodiceOggetto, file);

				this._log.DebugFormat("File con firma grafometrica ({0} {1}Kb)", file.FileName, file.Size / 1024);

				this.Context.Response.Write("OK");
			}
			catch (Exception ex)
			{
				var fileName = file != null ? file.FileName : "file sconosciuto";
				var fileSize = file != null ? file.Size : 0;

				this._log.ErrorFormat("Errore durante il salvataggio del file firmato con firma grafometrica ({0} {1}Kb) per la domanda {2} (codiceoggetto: {3}): {4}", fileName, fileSize, IdDomanda, CodiceOggetto, ex.ToString());

				throw;
			}
		}
	}
}