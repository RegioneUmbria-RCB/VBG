using Init.Sigepro.FrontEnd.AppLogic.GestioneOggetti;
using Init.Sigepro.FrontEnd.AppLogic.GestioneOggetti.PostedFileSpecifications;
using Init.Sigepro.FrontEnd.AppLogic.GestioneOggetti.UrlDownloadOggetti;
using Init.Sigepro.FrontEnd.AppLogic.GestioneOneri;
using Init.Sigepro.FrontEnd.AppLogic.GestionePagamenti.NODOPAGAMENTI;
using Init.Sigepro.FrontEnd.AppLogic.Services.Navigation;
using Init.Sigepro.FrontEnd.Infrastructure;
using Init.Sigepro.FrontEnd.Infrastructure.UrlsAndPaths;
using Init.Sigepro.FrontEnd.QsParameters;
using Init.Sigepro.FrontEnd.Reserved.InserimentoIstanza.Pagamenti.Specifications;
using log4net;
using Ninject;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web.UI.WebControls;

namespace Init.Sigepro.FrontEnd.Reserved.InserimentoIstanza.Pagamenti
{
    public partial class GestionePagamentiNodoPagamenti : IstanzeStepPage
    {
        private static class VistaFile
        {
            public const int Caricamento = 0;
            public const int DettagliFile = 1;
        }


        [Inject]
        public OneriDomandaService OneriDomandaService { get; set; }
        [Inject]
        public IPagamentiNodoPagamentiService NodoPagamentiService { get; set; }
        [Inject]
        public ValidPostedFileSpecification _validPostedFileSpecification { get; set; }
        [Inject]
        public RedirectService _redirectService { get; set; }
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

        public bool PermettiGiaPagatoConPagaDopoAttivo
        {
            get { object o = this.ViewState["PermettiGiaPagatoConPagaDopoAttivo"]; return o == null ? false : (bool)o; }
            set { this.ViewState["PermettiGiaPagatoConPagaDopoAttivo"] = value; }
        }
        #endregion

        protected void Page_Load(object sender, EventArgs e)
        {
            this.grigliaOneriIntervento.GetModalitaPagamentoSupportate += this.GetModalitaPagamentoSupportate;
            this.grigliaOneriEndo.GetModalitaPagamentoSupportate += this.GetModalitaPagamentoSupportate;
            // Il Service si occupa del salvataggio dei dati
            this.Master.IgnoraSalvataggioDati = true;

            if (!this.IsPostBack)
                this.DataBind();
        }

        private List<KeyValuePair<string, string>> GetModalitaPagamentoSupportate()
        {
            var l = new List<KeyValuePair<string, string>>
            {
                new KeyValuePair<string, string>("1", "Online")
            };

            if (this.PermettiGiaPagato())
            {
                l.Add(new KeyValuePair<string, string>("2", "Effettuato"));
            }

            l.Add(new KeyValuePair<string, string>("0", "Non dovuto"));

            return l;
        }

        private bool PermettiGiaPagato()
        {
            if (!this.NodoPagamentiService.PagoDopoAttivo(this.IdDomanda))
            {
                return true;
            }

            if (this.PermettiGiaPagatoConPagaDopoAttivo)
            {
                return true;
            }

            return false;
        }


        #region Ciclo di vita dello step
        public override void OnInitializeStep()
        {
            this.OneriDomandaService.SincronizzaOneri(this.IdDomanda, ComportamentoSincronizzazioneOneriSenzaImporto.Includi);
        }

        public override bool CanEnterStep()
        {
            if (!this.NodoPagamentiService.NodoPagamentoAttivo(this.IdDomanda))
            {
                var url = UrlBuilder.Url("~/reserved/inserimentoistanza/gestioneoneri.aspx", pb =>
                {
                    pb.Add(new QsAliasComune(this.IdComune));
                    pb.Add(new QsSoftware(this.Software));
                    pb.Add(new QsStepId(this.Request.QueryString));
                    pb.Add(new QsIdDomandaOnline(this.IdDomanda));
                });

                this.Response.Redirect(url);
                return true;
            }

            // TODO ????
            if (this.NodoPagamentiService.GetStatoPagamentiInSospeso(this.IdDomanda).Pagamenti.Count() > 0)
            {
                this.Master.cmdPrevStep_Click(this, EventArgs.Empty);
                return false;
            }

            return new DomandaContieneAlmenoUnOnereSpecification().IsSatisfiedBy(this.ReadFacade.Domanda);
        }

        public override bool CanExitStep()
        {
            var oneri = this.ReadFacade.Domanda.Oneri;

            if (this.Errori.Count > 0)
                return false;

            if (new TuttiGliOneriPagatiOnlineSpecification().IsSatisfiedBy(oneri))
                return true;

            if (new NessunOnereDovutoSpecification().AndNot(new UtenteDichiaraDiNonAvereOneriSpecification()).IsSatisfiedBy(oneri))
            {
                this.Errori.Add("Per proseguire è necessario dichiarare che la pratica non contiene oneri da pagare");

                return false;
            }

            if (new AlmenoUnOnerePagatoOffline().AndNot(new DomandaContieneAttestazioneDiPagamentoSpecification()).IsSatisfiedBy(oneri))
            {
                this.Errori.Add("Caricare l'attestazione di pagamento per gli oneri già pagati fuori dal circuito Pago PA");

                return false;
            }

            if (this.VerificaFirmaDigitaleBollettino && new DomandaContieneAttestazioneDiPagamentoSpecification().AndNot(new AttestazioneDiPagamentoFirmataDigitalmenteSpecification()).IsSatisfiedBy(oneri))
            {
                this.Errori.Add("Il documento \"attestazione di pagamento\" non è stato firmato digitalmente");

                return false;
            }

            // VerificaFirmaDigitaleBollettino

            return true;



            if (this.Errori.Count > 0)
                return false;

            if (new NessunOnereDovutoSpecification().And(new UtenteDichiaraDiNonAvereOneriSpecification()).IsSatisfiedBy(this.ReadFacade.Domanda.Oneri))
                return true;

            return true;


            if (new NessunOnereDovutoSpecification().AndNot(new UtenteDichiaraDiNonAvereOneriSpecification()).IsSatisfiedBy(oneri))
            {
                this.Errori.Add("Per proseguire è necessario dichiarare che la pratica non contiene oneri da pagare");

                return false;
            }
            /*
            if (new TuttiGliOneriPagatiOnlineSpecification().IsSatisfiedBy(ReadFacade.Domanda.Oneri))
                return true;



            if (VerificaFirmaDigitaleBollettino && new DomandaContieneAttestazioneDiPagamentoSpecification().And(new AttestazioneDiPagamentoFirmataDigitalmenteSpecification()).IsSatisfiedBy(ReadFacade.Domanda.Oneri))
                return true;

            if (!VerificaFirmaDigitaleBollettino && new DomandaContieneAttestazioneDiPagamentoSpecification().IsSatisfiedBy(ReadFacade.Domanda.Oneri))
                return true;

            Errori.Add("Per poter proseguire è necessario allegare una copia firmata digitalmente della ricevuta attestante l'avvenuto pagamento");

            return false;
            */
        }

        public override void OnBeforeExitStep()
        {
            var estremi = new EstremiPagamentoDataExtractor(this.grigliaOneriIntervento.Repeater, this.grigliaOneriEndo.Repeater).EstraiDati(false);

            this.Errori.AddRange(estremi.Errori);

            var oneriConImporto0 = estremi.Estremi.Where(x => x.ModalitaPagamento == ModalitaPagamentoOnereEnum.Online && x.EstremiPagamento.ImportoPagato == 0);

            oneriConImporto0.ToList().ForEach(x => this.Errori.Add($"L'onere {x.Descrizione} non ha un importo specificato. Utilizzare l'opzione \"Non dovuto\" se l'onere non è dovuto."));

            if (this.Errori.Count == 0)
            {
                this.OneriDomandaService.SpecificaEstremiPagamentoOneriNonPagatiOnline(this.IdDomanda, estremi.Estremi);
            }

        }

        #endregion

        public override void DataBind()
        {
            var modalitaPagamento = this.OneriDomandaService.GetListaModalitaPagamento().ToList();
            modalitaPagamento.Insert(0, new TipoPagamento("", ""));

            this.grigliaOneriIntervento.Visible = new DomandaContieneOneriDaInterventoSpecification().IsSatisfiedBy(this.ReadFacade.Domanda);
            this.grigliaOneriIntervento.EtichettaColonnaCausale = this.EtichettaColonnaCausaleIntervento;
            this.grigliaOneriIntervento.ModalitaPagamento = modalitaPagamento;
            this.grigliaOneriIntervento.DataSource = this.ReadFacade.Domanda.Oneri.OneriIntervento;
            this.grigliaOneriIntervento.DataBind();

            this.grigliaOneriEndo.Visible = new DomandaContieneOneriDaEndoSpecification().IsSatisfiedBy(this.ReadFacade.Domanda);
            this.grigliaOneriEndo.EtichettaColonnaCausale = this.EtichettaColonnaCausaleEndo;
            this.grigliaOneriEndo.ModalitaPagamento = modalitaPagamento;
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


                var file = new BinaryFile(this.fuCaricaFile, this._validPostedFileSpecification);

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
            //cmdFirma.Visible =
            this.lblErroreFirma.Visible = false;

            if (this.VerificaFirmaDigitaleBollettino && !attestazionediPagamento.FirmatoDigitalmente)
                /*cmdFirma.Visible = */
                this.lblErroreFirma.Visible = true;

            this.Master.MostraBottoneAvanti = true;
        }
        #endregion
    }
}