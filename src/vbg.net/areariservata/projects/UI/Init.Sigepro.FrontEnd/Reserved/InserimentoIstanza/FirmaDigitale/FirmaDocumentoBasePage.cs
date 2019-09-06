namespace Init.Sigepro.FrontEnd.Reserved.InserimentoIstanza.FirmaDigitale
{
	using System;
	using System.Web.UI.WebControls;
	using Init.Sigepro.FrontEnd.AppLogic.Autenticazione.Vbg.TokenApplicazione;
	using Init.Sigepro.FrontEnd.AppLogic.Configurazione.V2;
	using Init.Sigepro.FrontEnd.AppLogic.GestioneOggetti;
	using Init.Sigepro.FrontEnd.AppLogic.Services.Navigation;
	using Init.Sigepro.FrontEnd.AppLogic.VerificaFirmaDigitale;
	using log4net;
	using Ninject;

	public abstract class FirmaDocumentoBasePage : IstanzeStepPage
	{
		private static class Constants
		{
			public const string ReturnToParameter = "ReturnTo";
			public const string CodiceOggettoParameter = "CodiceOggetto";
			public const string EsitoOK = "OK";
		}

		#region Parametri della querystring
		private string ReturnTo
		{
			get
			{
				var qs = Request.QueryString[Constants.ReturnToParameter];

				if (string.IsNullOrEmpty(qs))
				{
					throw new InvalidOperationException("Parametro ReturnTo non impostato");
				}

				return qs;
			}
		}

		protected string CodiceOggetto
		{
			get
			{
				var qs = Request.QueryString[Constants.CodiceOggettoParameter];

				if (string.IsNullOrEmpty(qs))
				{
					throw new InvalidOperationException("Parametro CodiceOggetto non impostato");
				}

				return qs;
			}
		}

		protected string TokenApplicazione
		{
			get { return this._tokenApplicazioneService.GetToken(this.IdComune); }
		}

		#endregion

		protected string UrlJspFirmaDigitale
		{
			get { return this._configurazione.Parametri.UrlJspFirmaDigitale; }
		}

		[Inject]
		protected ITokenApplicazioneService _tokenApplicazioneService { get; set; }

		[Inject]
		protected IConfigurazione<ParametriFirmaDigitale> _configurazione { get; set; }

		[Inject]
		protected IOggettiService _oggettiService { get; set; }

		[Inject]
		protected IVerificaFirmaDigitaleService _firmaDigitaleService { get; set; }

		[Inject]
		protected PathUtils _pathUtils { get; set; }

		private ILog _log = LogManager.GetLogger(typeof(FirmaDocumentoBasePage));
		
		protected void Page_Load(object sender, EventArgs e)
		{
			if (!this.IsPostBack)
			{
				this.DataBind();
			}
		}

		protected void hidEsito_ValueChanged(object sender, EventArgs e)
		{
			if (this.GetHiddenFieldEsito().Value != Constants.EsitoOK)
			{
				Errori.Add("Si è verificato un errore durante l'operazione di firma");
				return;
			}

			try
			{
				// Carico il file che è stato firmato
				var codiceOggetto = Convert.ToInt32(this.CodiceOggetto);
				var file = this._oggettiService.GetById(codiceOggetto);

				// Verifico la firma digitale
				var esitoVerificaFirma = this._firmaDigitaleService.VerificaFirmaDigitale(file);

				if (!new FirmaValidaSpecification().IsSatisfiedBy(esitoVerificaFirma))
				{
					this._log.ErrorFormat("FirmaDocumentoViewModel->AggiornaStatoFirma: Il file firmato tramite l'applet non dispone di una firma digitale valida. Id utente={0}, codiceoggetto={1}, errore={2}", this._authenticationDataResolver.DatiAutenticazione.DatiUtente.Codicefiscale, codiceOggetto, esitoVerificaFirma.Errore);

					throw new FirmaDigitaleNonValidaException("Si è verificato un errore durante l'operazione di firma" + esitoVerificaFirma.Errore);
				}

				this.DocumentoFirmatoConSuccesso(codiceOggetto, file.FileName);

				this.cmdChiudi_Click(this, EventArgs.Empty);
			}
			catch (FirmaDigitaleNonValidaException ex)
			{
				this.Errori.Add(ex.Message);
			}
		}

		protected void cmdChiudi_Click(object sender, EventArgs e)
		{
			this.Response.Redirect(this.ReturnTo);
		}

		public override void DataBind()
		{
			var fileDaFirmare = this.GetHyperLinkFileDaFirmare();
			var codiceOggetto = int.Parse(this.CodiceOggetto);

			fileDaFirmare.Text = this._oggettiService.GetNomeFile(codiceOggetto);
			fileDaFirmare.NavigateUrl = this._pathUtils.GetUrlMostraOggetto(codiceOggetto);
		}

		protected abstract HiddenField GetHiddenFieldEsito();

		protected abstract HyperLink GetHyperLinkFileDaFirmare();

		protected abstract void DocumentoFirmatoConSuccesso(int codiceOggetto, string fileName);
	}
}