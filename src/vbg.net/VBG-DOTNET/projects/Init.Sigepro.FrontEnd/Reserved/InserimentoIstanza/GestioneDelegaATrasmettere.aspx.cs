using Init.Sigepro.FrontEnd.AppLogic.GestioneDelegaATrasmettere;
using Init.Sigepro.FrontEnd.AppLogic.GestioneOggetti.UrlDownloadOggetti;
using Init.Sigepro.FrontEnd.AppLogic.Services.Navigation;
using Init.Sigepro.FrontEnd.Reserved.InserimentoIstanza.CondizioniIngressoSteps;
using log4net;
using Ninject;
using System;

namespace Init.Sigepro.FrontEnd.Reserved.InserimentoIstanza
{
    public partial class GestioneDelegaATrasmettere : IstanzeStepPage
    {

        [Inject]
        public IDelegaATrasmettereService DelegaATrasmettereService { get; set; }

        [Inject]
        protected PathUtils _pathUtils { get; set; }
        [Inject]
        public IRedirectService _redirectService { get; set; }
        [Inject]
        public IUrlDownloadOggettiService _urlDownloadOggettiService { get; set; }

        private readonly ILog _logger = LogManager.GetLogger(typeof(GestioneDelegaATrasmettere));

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

            if (!this.IsPostBack)
                this.DataBind();


        }

        #region Eventi della vita dello step

        public override bool CanEnterStep()
        {
            bool mustEnterStep = new CondizioneIngressoStepVerificaDelega(this.ReadFacade, this.IgnoraDelegaSeProcuratoreDelRichiedente).Verificata();

            //if(ReadFacade.Domanda.DelegaATrasmettere.Presente && mustEnterStep)
            //   DelegaATrasmettereService.Elimina(IdDomanda);

            return mustEnterStep;
        }

        public override bool CanExitStep()
        {
            var allegatoDelega = this.ReadFacade.Domanda.DelegaATrasmettere.Allegato;

            if (allegatoDelega == null)
            {
                this.Errori.Add("Per proseguire è necessario allegare copia della delega a trasmettere");

                return false;
            }

            if (this.RichiedeFirma && !allegatoDelega.FirmatoDigitalmente)
            {
                this.Errori.Add("La copia della delega a trasmettere allegata deve essere firmata digitalmente");

                return false;
            }

            return true;
        }

        #endregion

        public override void DataBind()
        {
            this.ltrTestoDelega.Text = String.Format(this.TestoDichiarazioneDelega, this.UserAuthenticationResult.DatiUtente.ToString(), this.UserAuthenticationResult.DatiUtente.Codicefiscale);

            this.ltrTitoloDocumentoIdentita.Text = this.TitoloDocumentoIdentita;
            this.ltrTestoDocumentoIdentita.Text = this.TestoDocumentoIdentita;

            if (this.CodiceOggettoPdfCompilabile > 0)
            {
                this.ltrLinkDownload.Text = String.Format(this.TestoLinkDownload, this.ResolveUrl(this.GetLinkDownload()));
            }

            this.delegaATrasmettereView.DataSource = GestioneDelegaATrasmettere_file_view.DelegaATrasmettereFileModel.FromAllegatoDomanda(this.IdComune, this.ReadFacade.Domanda.DelegaATrasmettere.Allegato, this.RichiedeFirma, this._urlDownloadOggettiService);
            this.documentoIdentitaView.DataSource = GestioneDelegaATrasmettere_file_view.DelegaATrasmettereFileModel.FromAllegatoDomanda(this.IdComune, this.ReadFacade.Domanda.DelegaATrasmettere.DocumentoIdentita, false, this._urlDownloadOggettiService);

            this.Master.MostraBottoneAvanti = this.ReadFacade.Domanda.DelegaATrasmettere.Allegato != null;

        }

        private string GetLinkDownload()
        {
            return this._urlDownloadOggettiService.GetUrlDownloadPdfCompilabile(this.CodiceOggettoPdfCompilabile, this.IdDomanda, this.Software);
        }

        protected void delegaATrasmettereView_EliminaDocumento(object sender, EventArgs e)
        {
            this.DelegaATrasmettereService.EliminaDelegaATrasmettere(this.IdDomanda);

            this.DataBind();
        }


        protected void delegaATrasmettereView_FirmaDocumento(object sender, EventArgs e)
        {
            var codiceOggetto = this.ReadFacade.Domanda.DelegaATrasmettere.Allegato.CodiceOggetto;

            this._redirectService.ToFirmaDigitale(this.IdDomanda, codiceOggetto);
        }

        protected void delegaATrasmettereView_CaricaDocumento(object sender, EventArgs e)
        {
            try
            {
                var documento = this.delegaATrasmettereView.UploadedFile;

                if (documento != null)
                {
                    this.DelegaATrasmettereService.SalvaAllegato(this.IdDomanda, documento, this.RichiedeFirma);
                }

                this.DataBind();
            }
            catch (Exception ex)
            {
                this._logger.ErrorFormat(ex.ToString());

                this.Errori.Add(ex.Message);
            }
        }

        protected void documentoIdentitaView_EliminaDocumento(object sender, EventArgs e)
        {
            this.DelegaATrasmettereService.EliminaDocumentoIdentita(this.IdDomanda);

            this.DataBind();
        }

        protected void documentoIdentitaView_FirmaDocumento(object sender, EventArgs e)
        {
            var codiceOggetto = this.ReadFacade.Domanda.DelegaATrasmettere.DocumentoIdentita.CodiceOggetto;

            this._redirectService.ToFirmaDigitale(this.IdDomanda, codiceOggetto);
        }

        protected void documentoIdentitaView_FileCaricato(object sender, EventArgs e)
        {
            try
            {
                var documento = this.documentoIdentitaView.UploadedFile;

                if (documento != null)
                {
                    this.DelegaATrasmettereService.SalvaDocumentoIdentita(this.IdDomanda, documento, false);
                }

                this.DataBind();
            }
            catch (Exception ex)
            {
                this._logger.ErrorFormat(ex.ToString());

                this.Errori.Add(ex.Message);
            }
        }
    }
}
