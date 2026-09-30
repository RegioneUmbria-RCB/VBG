using Init.Sigepro.FrontEnd.AppLogic.GestioneOggetti.UrlDownloadOggetti;
using Init.Sigepro.FrontEnd.AppLogic.GestioneOneri;
using Init.Sigepro.FrontEnd.AppLogic.Services.Navigation;
using Init.Sigepro.FrontEnd.Infrastructure;
using Init.Sigepro.FrontEnd.Reserved.InserimentoIstanza.Pagamenti.Specifications;
using Init.Sigepro.FrontEnd.WebForms.AppLogic.GestioneOggetti;
using Init.Sigepro.FrontEnd.WebForms.AppLogic.GestioneOggetti.PostedFileSpecifications;
using log4net;
using Ninject;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web.UI.WebControls;

namespace Init.Sigepro.FrontEnd.Reserved.InserimentoIstanza.Pagamenti
{
    public partial class GestionePagamentiEntraNext : IstanzeStepPage
    {
        private static class VistaFile
        {
            public const int Caricamento = 0;
            public const int DettagliFile = 1;
        }


        [Inject]
        public OneriDomandaService OneriDomandaService { get; set; }

        [Inject]
        public ValidPostedFileSpecification _validPostedFileSpecification { get; set; }

        [Inject]
        public IRedirectService _redirectService { get; set; }

        [Inject]
        public IUrlDownloadOggettiService _urlDownloadService { get; set; }

        private readonly ILog _logger = LogManager.GetLogger(typeof(GestioneOneri));


        #region parametri letti dal file xml
        public bool VerificaFirmaDigitaleBollettino
        {
            get { object o = this.ViewState["VerificaFirmaDigitaleBollettino"]; return o == null ? false : (bool)o; }
            set { this.ViewState["VerificaFirmaDigitaleBollettino"] = value; }
        }

        public string EtichettaColonnaEndoprocedimento
        {
            get { object o = this.ViewState["EtichettaColonnaEndoprocedimento"]; return o == null ? "" : (string)o; }
            set { this.ViewState["EtichettaColonnaEndoprocedimento"] = value; }
        }

        public string EtichettaColonnaCausaleEndo
        {
            get { object o = this.ViewState["EtichettaColonnaCausaleEndo"]; return o == null ? "" : (string)o; }
            set { this.ViewState["EtichettaColonnaCausaleEndo"] = value; }
        }

        public string EtichettaColonnaIntervento
        {
            get { object o = this.ViewState["EtichettaColonnaIntervento"]; return o == null ? "" : (string)o; }
            set { this.ViewState["EtichettaColonnaIntervento"] = value; }
        }

        public string EtichettaColonnaCausaleIntervento
        {
            get { object o = this.ViewState["EtichettaColonnaCausaleIntervento"]; return o == null ? "" : (string)o; }
            set { this.ViewState["EtichettaColonnaCausaleIntervento"] = value; }
        }

        public string TitoloCaricamentoBollettino
        {
            get { object o = this.ViewState["TitoloCaricamentoBollettino"]; return o == null ? "" : (string)o; }
            set { this.ViewState["TitoloCaricamentoBollettino"] = value; }
        }

        public string DescrizioneCaricamentoBollettino
        {
            get { object o = this.ViewState["DescrizioneCaricamentoBollettino"]; return o == null ? "" : (string)o; }
            set { this.ViewState["DescrizioneCaricamentoBollettino"] = value; }
        }

        public string DescrizioneCaricamentoBollettinoEffettuato
        {
            get { object o = this.ViewState["DescrizioneCaricamentoBollettinoEffettuato"]; return o == null ? "" : (string)o; }
            set { this.ViewState["DescrizioneCaricamentoBollettinoEffettuato"] = value; }
        }

        public string TestoDichiarazioneAssenzaOneri
        {
            get { object o = this.ViewState["TestoDichiarazioneAssenzaOneri"]; return o == null ? "Dichiaro di non avere oneri da pagare" : (string)o; }
            set { this.ViewState["TestoDichiarazioneAssenzaOneri"] = value; }
        }

        #endregion

        protected void Page_Load(object sender, EventArgs e)
        {
            // Il Service si occupa del salvataggio dei dati
            this.Master.IgnoraSalvataggioDati = true;

            if (!this.IsPostBack)
                this.DataBind();
        }


        #region Ciclo di vita dello step
        public override void OnInitializeStep()
        {
            this.OneriDomandaService.SincronizzaOneri(this.IdDomanda, ComportamentoSincronizzazioneOneriSenzaImporto.Ignora);
        }

        public override bool CanEnterStep()
        {
            //if (PagamentiEntraNextService.GetPagamentiInSospeso(IdDomanda).Count() > 0)
            //{
            //    this.Master.cmdPrevStep_Click(this, EventArgs.Empty);
            //    return false;
            //}

            return new DomandaContieneAlmenoUnOnereSpecification().IsSatisfiedBy(this.ReadFacade.Domanda);
        }

        public override bool CanExitStep()
        {
            if (this.Errori.Count > 0)
                return false;

            if (!new TuttiGliOneriPagatiOnlineSpecification().IsSatisfiedBy(this.ReadFacade.Domanda.Oneri))
            {
                this.Errori.Add("Tutti gli oneri della domanda devono essere pagati on-line");

                return false;
            }

            if (new NessunOnereDovutoSpecification().AndNot(new UtenteDichiaraDiNonAvereOneriSpecification()).IsSatisfiedBy(this.ReadFacade.Domanda.Oneri))
            {
                this.Errori.Add($"Gli oneri della domanda non sono dovuti. È necessario dichiarare che \"{this.TestoDichiarazioneAssenzaOneri}\"");

                return false;
            }

            return true;
        }

        public override void OnBeforeExitStep()
        {
            var estremi = new EstremiPagamentoDataExtractor(this.grigliaOneriIntervento.Repeater, this.grigliaOneriEndo.Repeater).EstraiDati(false);

            this.Errori.AddRange(estremi.Errori);

            if (this.Errori.Count == 0)
                this.OneriDomandaService.SpecificaEstremiPagamento(this.IdDomanda, estremi.Estremi);
        }

        #endregion

        public override void DataBind()
        {
            var tipiPagamento = this.OneriDomandaService.GetListaTipiPagamento().ToList();
            tipiPagamento.Insert(0, new TipoPagamento("", ""));

            this.grigliaOneriIntervento.Visible = new DomandaContieneOneriDaInterventoSpecification().IsSatisfiedBy(this.ReadFacade.Domanda);
            this.grigliaOneriIntervento.EtichettaColonnaCausale = this.EtichettaColonnaCausaleIntervento;
            this.grigliaOneriIntervento.ModalitaPagamento = tipiPagamento;
            this.grigliaOneriIntervento.DataSource = this.ReadFacade.Domanda.Oneri.OneriIntervento;
            this.grigliaOneriIntervento.DataBind();

            this.grigliaOneriEndo.Visible = new DomandaContieneOneriDaEndoSpecification().IsSatisfiedBy(this.ReadFacade.Domanda);
            this.grigliaOneriEndo.EtichettaColonnaCausale = this.EtichettaColonnaCausaleEndo;
            this.grigliaOneriEndo.ModalitaPagamento = tipiPagamento;
            this.grigliaOneriEndo.DataSource = this.ReadFacade.Domanda.Oneri.OneriEndoprocedimenti;
            this.grigliaOneriEndo.DataBind();

            this.chkAssenzaOneri.Checked = this.ReadFacade.Domanda.Oneri.DichiaraDiNonAvereOneriDaPagare;

            if (!new DomandaContieneAttestazioneDiPagamentoSpecification().IsSatisfiedBy(this.ReadFacade.Domanda.Oneri))
            {
                this.MostraVistaCaricamentoFile();
            }
            else
            {
                this.MostraVistaDettaglioFile();
            }
        }

        private void SalvaValoriEstremiOneri()
        {
            var estremi = new EstremiPagamentoDataExtractor(this.grigliaOneriIntervento.Repeater, this.grigliaOneriEndo.Repeater).EstraiDati(true);

            this.OneriDomandaService.SpecificaEstremiPagamento(this.IdDomanda, estremi.Estremi);
        }

        protected void cmdUpload_Click(object sender, EventArgs e)
        {
            try
            {
                this.SalvaValoriEstremiOneri();


                var file = new WebFormsBinaryFile(this.fuCaricaFile, this._validPostedFileSpecification);

                this.OneriDomandaService.InserisciAttestazioneDiPagamento(this.IdDomanda, file);

                this.DataBind();
            }
            catch (Exception ex)
            {
                this._logger.ErrorFormat("Errore durante il caricamento del bollettino in gestioneoneri.aspx: {0}", ex.ToString());

                this.Errori.Add(ex.Message);
            }

        }

        protected void cmdRimuovi_Click(object sender, EventArgs e)
        {
            try
            {
                this.SalvaValoriEstremiOneri();

                this.OneriDomandaService.EliminaAttestazioneDiPagamento(this.IdDomanda);

                this.DataBind();
            }
            catch (Exception ex)
            {
                this._logger.ErrorFormat("Errore durante la rimozione del bollettino in gestioneoneri.aspx: {0}", ex.ToString());

                this.Errori.Add(ex.Message);
            }
        }

        protected void cmdFirma_Click(object sender, EventArgs e)
        {
            var codiceOggetto = this.ReadFacade.Domanda.Oneri.AttestazioneDiPagamento.CodiceOggetto.Value;

            this._redirectService.ToFirmaDigitale(this.IdDomanda, codiceOggetto);
        }

        protected void chkAssenzaOneri_CheckedChanged(object sender, EventArgs e)
        {
            if (this.chkAssenzaOneri.Checked)
            {
                this.OneriDomandaService.ImpostaDichiarazioneDiAssenzaOneriDaPagare(this.IdDomanda);
            }
            else
            {
                this.OneriDomandaService.RimuoviDichiarazioneDiAssenzaOneriDaPagare(this.IdDomanda);
            }
        }

        #region Gestione delle views di caricamento/Visualizzazione attestazione oneri
        private void MostraVistaCaricamentoFile()
        {
            this.mvCaricamentoBollettino.ActiveViewIndex = VistaFile.Caricamento;
        }

        private void MostraVistaDettaglioFile()
        {
            this.mvCaricamentoBollettino.ActiveViewIndex = VistaFile.DettagliFile;

            var attestazionediPagamento = this.ReadFacade.Domanda.Oneri.AttestazioneDiPagamento;

            var codiceOggetto = attestazionediPagamento.CodiceOggetto;
            var nomeFile = attestazionediPagamento.NomeFile;

            // Dati del file caricato
            var url = this._urlDownloadService.GetUrlDownload(codiceOggetto.Value);

            this.hlFileCaricato.Text = nomeFile;
            this.hlFileCaricato.NavigateUrl = this.ResolveClientUrl(url);

            // Etichetta di errore se firma digitale mancante
            this.cmdFirma.Visible =
            this.lblErroreFirma.Visible = false;

            if (this.VerificaFirmaDigitaleBollettino && !attestazionediPagamento.FirmatoDigitalmente)
                this.cmdFirma.Visible = this.lblErroreFirma.Visible = true;

            this.Master.MostraBottoneAvanti = true;
        }
        #endregion

        protected List<KeyValuePair<string, string>> GetModalitaPagamentoSupportate()
        {
            return new List<KeyValuePair<string, string>>
            {
                new KeyValuePair<string,string>("1", "Online")
            };
        }
    }
}
