using Init.Sigepro.FrontEnd.AppLogic.GestioneBandiUmbria;
using Init.Sigepro.FrontEnd.AppLogic.GestioneConversioneFiles;
using Init.Sigepro.FrontEnd.AppLogic.GestioneOggetti.UrlDownloadOggetti;
using Init.Sigepro.FrontEnd.AppLogic.InvioDomanda;
using Init.Sigepro.FrontEnd.AppLogic.InvioDomanda.MessaggiErroreInvio;
using Init.Sigepro.FrontEnd.AppLogic.WsInterventi;
using Init.Sigepro.FrontEnd.WebForms.AppLogic.GestioneOggetti;
using Init.Sigepro.FrontEnd.WebForms.AppLogic.GestioneOggetti.PostedFileSpecifications;
using log4net;
using Ninject;
using System;
using System.Linq;
using System.Web.UI.WebControls;

namespace Init.Sigepro.FrontEnd.Reserved.InserimentoIstanza.GestioneBandiUmbria.Incoming1
{
    public partial class FirmaEInvioIncoming : IstanzeStepPage
    {
        private static class Constants
        {
            // public const string MsgErroreComuneNonSelezionato = "Per poter proseguire è necessario selezionare il comune per cui si vuole presentare l'istanza";
            // public const string MsgErroreBandoNonAttivo = "Il bando sarà attivo dalle ore {0} del {1}. Non è ancora possibile presentare domande";
            public const string MsgErroreBandoScaduto = "Il bando è scaduto e non è più possibile presentare domande";
        }

        private readonly ILog _log = LogManager.GetLogger(typeof(FirmaEInvioIncoming));

        [Inject]
        protected IBandiIncomingService _service { get; set; }


        [Inject]
        public InvioDomandaAreaRiservataService _invioDomandaService { get; set; }

        [Inject]
        public IMessaggioErroreInvioService _messaggioErroreService { get; set; }

        [Inject]
        public ValidPostedFileSpecification _validPostedFileSpecification { get; set; }

        [Inject]
        protected IUrlDownloadOggettiService _urlDownloadOggettiService { get; set; }

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
            public bool HaFileFirmato { get; set; }
            public string Descrizione { get; set; }
            public string UrlDownloadFileFirmatoDigitalmente { get; set; }
            public string Id { get; set; }
            public string UrlDownloadFileDaFirmare { get; set; }
            public string NomeFile { get; set; }
            public string NomeFileFirmatoDigitalmente { get; set; }

            public AllegatoDaFirmareBindingItem(AllegatoDomandaBandi x, IUrlDownloadOggettiService urlDownloadOggettiService)
            {
                this.Id = x.Id;
                this.HaFileFirmato = x.IdAllegatoFirmatoDigitalmente.HasValue;
                this.Descrizione = x.Descrizione;
                this.NomeFile = x.NomeFile;
                this.NomeFileFirmatoDigitalmente = x.NomeFileFirmatoDigitalemnte;

                this.UrlDownloadFileDaFirmare = urlDownloadOggettiService.GetUrlDownloadConvertito(x.IdAllegato.Value, FormatoConversioneEnum.PDF);

                if (x.IdAllegatoFirmatoDigitalmente.HasValue)
                {
                    this.UrlDownloadFileFirmatoDigitalmente = urlDownloadOggettiService.GetUrlDownload(x.IdAllegatoFirmatoDigitalmente.Value);
                }
            }


        }

        public override void DataBind()
        {
            var allegati = this._service.GetAllegatiCheNecessitanoFirma(this.IdDomanda);

            this.rptAllegatiDaFirmare.DataSource = allegati.Select(x => new AllegatoDaFirmareBindingItem(x, this._urlDownloadOggettiService));
            this.rptAllegatiDaFirmare.DataBind();

            this.pnlInvio.Visible = allegati.Where(x => !x.IdAllegatoFirmatoDigitalmente.HasValue).Count() == 0;


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