using System;
using System.Linq;
using Init.Sigepro.FrontEnd.AppLogic.GestioneOggetti;
using Init.Sigepro.FrontEnd.AppLogic.Services.Domanda;
using Init.Sigepro.FrontEnd.AppLogic.Services.Navigation;
using Init.Sigepro.FrontEnd.Reserved.InserimentoIstanza.CondizioniIngressoSteps;
using log4net;
using Ninject;
using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneAllegati;

namespace Init.Sigepro.FrontEnd.Reserved.InserimentoIstanza
{
	public partial class GestioneDelegaATrasmettere : IstanzeStepPage
	{

		[Inject]
		public DelegaATrasmettereService DelegaATrasmettereService { get; set; }

		[Inject]
		protected PathUtils _pathUtils { get; set; }
        [Inject]
        public RedirectService _redirectService { get; set; }



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

        public bool RichiedeDocumentoIdentita
        {
            get { object o = this.ViewState["RichiedeDocumentoIdentita"]; return o == null ? false : (bool)o; }
            set { this.ViewState["RichiedeDocumentoIdentita"] = value; }
        }

        public string TitoloDocumentoIdentita
        {
            get { object o = this.ViewState["TitoloDocumentoIdentita"]; return o == null ? "Documento d'identità" : (string)o; }
            set { this.ViewState["TitoloDocumentoIdentita"] = value; }
        }

        public string TestoDocumentoIdentita
        {
            get { object o = this.ViewState["TestoDocumentoIdentita"]; return o == null ? "" : (string)o; }
            set { this.ViewState["TestoDocumentoIdentita"] = value; }
        }


        #endregion


        protected void Page_Load(object sender, EventArgs e)
		{
			// Il salvataggioviene effettuato dal service
			this.Master.IgnoraSalvataggioDati = true;
            this.Master.ResetValidatorsOnLoad = false;

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

			if( RichiedeFirma && !allegatoDelega.FirmatoDigitalmente )
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

            ltrTitoloDocumentoIdentita.Text = this.TitoloDocumentoIdentita;
            ltrTestoDocumentoIdentita.Text = this.TestoDocumentoIdentita;

			if (CodiceOggettoPdfCompilabile > 0)
			{
				ltrLinkDownload.Text = String.Format(TestoLinkDownload, ResolveUrl(GetLinkDownload()));
			}

            this.delegaATrasmettereView.DataSource = GestioneDelegaATrasmettere_file_view.DelegaATrasmettereFileModel.FromAllegatoDomanda(IdComune, ReadFacade.Domanda.DelegaATrasmettere.Allegato, this.RichiedeFirma);
            this.documentoIdentitaView.DataSource = GestioneDelegaATrasmettere_file_view.DelegaATrasmettereFileModel.FromAllegatoDomanda(IdComune, ReadFacade.Domanda.DelegaATrasmettere.DocumentoIdentita, false);

            this.Master.MostraBottoneAvanti = ReadFacade.Domanda.DelegaATrasmettere.Allegato != null;

		}

		private string GetLinkDownload()
		{
			return _pathUtils.Reserved.InserimentoIstanza.GetUrlDownloadPdfCompilabile(CodiceOggettoPdfCompilabile, IdDomanda);
		}

		protected void delegaATrasmettereView_EliminaDocumento(object sender, EventArgs e)
		{
			DelegaATrasmettereService.EliminaDelegaATrasmettere(IdDomanda);

			DataBind();
		}


        protected void delegaATrasmettereView_FirmaDocumento(object sender, EventArgs e)
        {
            var codiceOggetto = ReadFacade.Domanda.DelegaATrasmettere.Allegato.CodiceOggetto;

            this._redirectService.ToFirmaDigitale(IdDomanda, codiceOggetto);
        }

        protected void delegaATrasmettereView_CaricaDocumento(object sender, EventArgs e)
        {
            try
            {
                var documento = this.delegaATrasmettereView.UploadedFile;

                if (documento != null)
                {
                    DelegaATrasmettereService.SalvaAllegato(IdDomanda, documento, RichiedeFirma);
                }                

                DataBind();
            }
            catch (Exception ex)
            {
                _logger.ErrorFormat(ex.ToString());

                Errori.Add(ex.Message);
            }
        }

        protected void documentoIdentitaView_EliminaDocumento(object sender, EventArgs e)
        {
            DelegaATrasmettereService.EliminaDocumentoIdentita(IdDomanda);

            DataBind();
        }

        protected void documentoIdentitaView_FirmaDocumento(object sender, EventArgs e)
        {
            var codiceOggetto = ReadFacade.Domanda.DelegaATrasmettere.DocumentoIdentita.CodiceOggetto;

            this._redirectService.ToFirmaDigitale(IdDomanda, codiceOggetto);
        }

        protected void documentoIdentitaView_FileCaricato(object sender, EventArgs e)
        {
            try
            {
                var documento = this.documentoIdentitaView.UploadedFile;

                if (documento != null)
                {
                    DelegaATrasmettereService.SalvaDocumentoIdentita(IdDomanda, documento, false);
                }

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
