using System;
using System.Linq;
using Init.Sigepro.FrontEnd.AppLogic.GestioneOggetti;
using Init.Sigepro.FrontEnd.AppLogic.Services.Domanda;
using Init.Sigepro.FrontEnd.AppLogic.Services.Navigation;
using Init.Sigepro.FrontEnd.Reserved.InserimentoIstanza.CondizioniIngressoSteps;
using log4net;
using Ninject;

namespace Init.Sigepro.FrontEnd.Reserved.InserimentoIstanza
{
	public partial class GestioneDelegaATrasmettere : IstanzeStepPage
	{

		[Inject]
		public DelegaATrasmettereService DelegaATrasmettereService { get; set; }

		[Inject]
		protected PathUtils _pathUtils { get; set; }

		[Inject]
		public ValidPostedFileSpecification _validPostedFileSpecification { get; set; }

		ILog _logger = LogManager.GetLogger(typeof(GestioneDelegaATrasmettere));

		#region Parametri letti dal file xml
		public string TestoDichiarazioneDelega
		{
			get { object o = this.ViewState["TestoDichiarazioneDelega"]; return o == null ? String.Empty : (string)o; }
			set { this.ViewState["TestoDichiarazioneDelega"] = value; }
		}

		public string TestoLinkDownload
		{
			get { object o = this.ViewState["TestoLinkDownload"]; return o == null ? "<a href=\"{0}\">Scarica il modello precompilato</a>" : (string)o; }
			set { this.ViewState["TestoLinkDownload"] = value; }
		}

		public int CodiceOggettoPdfCompilabile
		{
			get { object o = this.ViewState["CodiceOggettoPdfCompilabile"]; return o == null ? -1 : (int)o; }
			set { this.ViewState["CodiceOggettoPdfCompilabile"] = value; }
		}

		public bool IgnoraDelegaSeProcuratoreDelRichiedente
		{
			get { object o = this.ViewState["IgnoraDelegaSeProcuratoreDelRichiedente"]; return o == null ? false : (bool)o; }
			set { this.ViewState["IgnoraDelegaSeProcuratoreDelRichiedente"] = value; }
		}

		public bool RichiedeFirma
		{
			get { object o = this.ViewState["RichiedeFirma"]; return o == null ? false : (bool)o; }
			set { this.ViewState["RichiedeFirma"] = value; }
		}
		#endregion

		

		protected void Page_Load(object sender, EventArgs e)
		{
			// Il salvataggioviene effettuato dal service
			this.Master.IgnoraSalvataggioDati = true;

			if (!IsPostBack)
				DataBind();
		}

		#region Eventi della vita dello step

		public override bool CanEnterStep()
		{
			bool mustEnterStep = new CondizioneIngressoStepVerificaDelega( ReadFacade, this.IgnoraDelegaSeProcuratoreDelRichiedente ).Verificata();

			//if(ReadFacade.Domanda.DelegaATrasmettere.Presente && mustEnterStep)
			//   DelegaATrasmettereService.Elimina(IdDomanda);

			return mustEnterStep;
		}

		public override bool CanExitStep()
		{
			var allegatoDelega = ReadFacade.Domanda.DelegaATrasmettere.Allegato;

			if (allegatoDelega == null)
			{
				Errori.Add("Per proseguire è necessario allegare copia della delega a trasmettere");

				return false;
			}

			if( !allegatoDelega.FirmatoDigitalmente )
			{
				Errori.Add("La copia della delega a trasmettere allegata deve essere firmata digitalmente");

				return false;
			}

			return true;
		}

		#endregion

		public override void DataBind()
		{
			ltrTestoDelega.Text = String.Format(TestoDichiarazioneDelega, UserAuthenticationResult.DatiUtente.ToString(), UserAuthenticationResult.DatiUtente.Codicefiscale);

			if (CodiceOggettoPdfCompilabile > 0)
			{
				ltrLinkDownload.Text = String.Format(TestoLinkDownload, ResolveUrl(GetLinkDownload()));
			}

			if (ReadFacade.Domanda.DelegaATrasmettere.Allegato == null)
			{
				mvDelega.ActiveViewIndex = 0;
			}
			else
			{
				mvDelega.ActiveViewIndex = 1;

				var codiceOggetto = ReadFacade.Domanda.DelegaATrasmettere.Allegato.CodiceOggetto;
				var nomefile = ReadFacade.Domanda.DelegaATrasmettere.Allegato.NomeFile;

				var url = String.Format("~/MostraOggetto.ashx?IdComune={0}&CodiceOggetto={1}", IdComune, codiceOggetto);

				hlDelega.NavigateUrl = ResolveClientUrl(url);
				hlDelega.Text = nomefile;

				cmdFirma.Visible = lblErroreDelega.Visible = !ReadFacade.Domanda.DelegaATrasmettere.Allegato.FirmatoDigitalmente;
			}

			this.Master.MostraBottoneAvanti = ReadFacade.Domanda.DelegaATrasmettere.Allegato != null;

		}

		private string GetLinkDownload()
		{
			return _pathUtils.Reserved.InserimentoIstanza.GetUrlDownloadPdfCompilabile(IdComune, UserAuthenticationResult.Token, Software, CodiceOggettoPdfCompilabile, IdDomanda);

			return String.Empty;
		}

		protected void cmdFirma_Click(object sender, EventArgs e)
		{
			var codiceOggetto = ReadFacade.Domanda.DelegaATrasmettere.Allegato.CodiceOggetto;

			this.Master.Redirect.ToFirmaDigitale(IdComune, Software, UserAuthenticationResult.Token, IdDomanda, codiceOggetto);
		}


		protected void cmdEliminaDelega_Click(object sender, EventArgs e)
		{
			DelegaATrasmettereService.Elimina(IdDomanda);

			DataBind();
		}

		protected void cmdUpload_Click(object sender, EventArgs e)
		{
			try
			{
				var bf = new BinaryFile(fuDelega, this._validPostedFileSpecification);

				DelegaATrasmettereService.Salva(IdDomanda, bf, RichiedeFirma);

				DataBind();
			}
			catch (Exception ex)
			{
				_logger.ErrorFormat(ex.ToString());

				Errori.Add(ex.Message);
			}
		}
	}
}
