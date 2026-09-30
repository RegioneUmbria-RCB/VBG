using Init.Sigepro.FrontEnd.AppLogic.GestioneBandiUmbria;
using Init.Sigepro.FrontEnd.AppLogic.GestioneConversioneFiles;
using Init.Sigepro.FrontEnd.AppLogic.GestioneOggetti.UrlDownloadOggetti;
using Init.Sigepro.FrontEnd.AppLogic.InvioDomanda;
using Init.Sigepro.FrontEnd.AppLogic.InvioDomanda.MessaggiErroreInvio;
using Init.Sigepro.FrontEnd.AppLogic.Services.Navigation;
using Init.Sigepro.FrontEnd.AppLogic.WsInterventi;
using Init.Sigepro.FrontEnd.WebForms.AppLogic.GestioneOggetti;
using Init.Sigepro.FrontEnd.WebForms.AppLogic.GestioneOggetti.PostedFileSpecifications;
using log4net;
using Ninject;
using System;
using System.Linq;
using System.Web.UI.WebControls;

namespace Init.Sigepro.FrontEnd.Reserved.InserimentoIstanza.GestioneBandiUmbria
{
    public partial class FirmaEInvio : IstanzeStepPage
    {
        private static class Constants
        {
            // public const string MsgErroreComuneNonSelezionato = "Per poter proseguire è necessario selezionare il comune per cui si vuole presentare l'istanza";
            // public const string MsgErroreBandoNonAttivo = "Il bando sarà attivo dalle ore {0} del {1}. Non è ancora possibile presentare domande";
            public const string MsgErroreBandoScaduto = "Il bando è scaduto e non è più possibile presentare domande";
        }

        private readonly ILog _log = LogManager.GetLogger(typeof(BenvenutoBandi));

        [Inject]
        protected IBandiUmbriaService _service { get; set; }

        [Inject]
        protected PathUtils _pathUtils { get; set; }

        [Inject]
        public InvioDomandaAreaRiservataService _invioDomandaService { get; set; }

        [Inject]
        public IMessaggioErroreInvioService _messaggioErroreService { get; set; }

        [Inject]
        public ValidPostedFileSpecification _validPostedFileSpecification { get; set; }

        [Inject]
        public IUrlDownloadOggettiService _urlDownloadOggettiService { get; set; }

        public string TestoInvio
        {
            get { return this.ltrTestoInvio.Text; }
            set { this.ltrTestoInvio.Text = value; }
        }

        public bool BandoConcluso
        {
            get
            {
                var idIntervento = this.ReadFacade.Domanda.AltriDati.Intervento.Codice;
                var codiceComune = this.ReadFacade.Domanda.AltriDati.CodiceComune;
                var interventoAttivo = this.ReadFacade.Interventi.VerificaAccessoIntervento(idIntervento, codiceComune);
                return !interventoAttivo.Is(TipoAccessibilitaIntervento.Accessibile);
            }
        }

        public string DataFineBando
        {
            get { var o = this.ViewState["DataFineBando"]; return o == null ? "11/02/2015 23.59.59" : (string)o; }
            set { this.ViewState["DataFineBando"] = value; }
        }

        protected void Page_Load(object sender, EventArgs e)
        {
            if (!this.IsPostBack)
                this.DataBind();
        }

        private void MostraErroreBandoScaduto()
        {
            this._log.ErrorFormat("L'utente {0} ha acceduto alle domande dei bandi ma il bando è scaduto (cod intervento: {1})", this.UserAuthenticationResult.DatiUtente.Codicefiscale, this.ReadFacade.Domanda.AltriDati.Intervento.Codice);


            var err = Constants.MsgErroreBandoScaduto;

            this.Errori.Add(err);
        }


        protected class AllegatoDaFirmareBindingItem
        {
            private readonly AllegatoDomandaBandi _allegato;

            public bool HaFileFirmato => this._allegato.IdAllegatoFirmatoDigitalmente.HasValue;
            public string Descrizione => this._allegato.Descrizione;
            public string Id => this._allegato.Id;
            public string NomeFile => this._allegato.NomeFile;
            public string NomeFileFirmatoDigitalmente => this._allegato.NomeFileFirmatoDigitalemnte;
            public string UrlDownloadFileFirmatoDigitalmente { get; set; }
            public string UrlDownloadFileDaFirmare { get; set; }

            public AllegatoDaFirmareBindingItem(AllegatoDomandaBandi allegato, IUrlDownloadOggettiService urlDownloadOggetti)
            {
                this._allegato = allegato;

                this.UrlDownloadFileDaFirmare = urlDownloadOggetti.GetUrlDownloadConvertito(allegato.IdAllegato.Value, FormatoConversioneEnum.PDF);

                if (allegato.IdAllegatoFirmatoDigitalmente.HasValue)
                {
                    this.UrlDownloadFileFirmatoDigitalmente = urlDownloadOggetti.GetUrlDownload(allegato.IdAllegatoFirmatoDigitalmente.Value);
                }
            }
        }

        public override void DataBind()
        {
            var allegati = this._service.GetAllegatiCheNecessitanoFirma(this.IdDomanda);

            this.rptAllegatiDaFirmare.DataSource = allegati.Select(x => new AllegatoDaFirmareBindingItem(x, this._urlDownloadOggettiService));
            this.rptAllegatiDaFirmare.DataBind();

            this.pnlInvio.Visible = allegati.Count(x => !x.IdAllegatoFirmatoDigitalmente.HasValue) == 0;


            if (this.BandoConcluso)
            {
                this.MostraErroreBandoScaduto();
                this.Master.MostraBottoneAvanti = false;
                this.Master.MostraPaginatoreSteps = false;
                this.pnlInvio.Visible = false;
            }
        }

        protected void OnFileUploaded(object sender, EventArgs e)
        {
            try
            {
                var link = (Button)sender;
                var fuAllegato = (FileUpload)link.NamingContainer.FindControl("fuAllegato");
                var hidId = (HiddenField)link.NamingContainer.FindControl("hidId");

                this._service.AggiungiFileFirmatoAdAllegato(this.IdDomanda, hidId.Value, new WebFormsBinaryFile(fuAllegato, this._validPostedFileSpecification));
            }
            catch (Exception ex)
            {
                this.Errori.Add(ex.Message);
            }
            finally
            {
                this.DataBind();
            }
        }

        protected void OnDeleteClicked(object sender, EventArgs e)
        {
            try
            {
                var link = (LinkButton)sender;
                var id = link.CommandArgument;

                this._service.RimuoviFileFirmatoDaAllegato(this.IdDomanda, id);
            }
            catch (Exception ex)
            {
                this.Errori.Add(ex.Message);
            }
            finally
            {
                this.DataBind();
            }
        }

        protected void InviaPratica(object sender, EventArgs e)
        {
            this._service.PreparaAllegatiDomanda(this.IdDomanda);

            var risultato = this._invioDomandaService.Invia(this.IdDomanda, String.Empty);

            if (risultato.Esito == InvioIstanzaResult.TipoEsitoInvio.InvioRiuscito || risultato.Esito == InvioIstanzaResult.TipoEsitoInvio.InvioRiuscitoNoBackend)
            {
                this.Redirect("~/Reserved/InserimentoIstanza/CertificatoInvio.aspx", qs =>
                {
                    qs.Add("Id", risultato.CodiceIstanza);
                    qs.Add("IdPresentazione", this.IdDomanda);
                });

                return;
            }

            this.multiView.ActiveViewIndex = 1;
            this.Master.MostraPaginatoreSteps = false;

            this.lblErroreInvio.Text = this._messaggioErroreService.GeneraMessaggioErrore(this.IdDomanda);
        }
    }
}