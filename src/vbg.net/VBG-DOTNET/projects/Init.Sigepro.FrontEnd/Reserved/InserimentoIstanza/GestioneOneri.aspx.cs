using Init.Sigepro.FrontEnd.AppLogic.GestioneOggetti.UrlDownloadOggetti;
using Init.Sigepro.FrontEnd.AppLogic.GestioneOneri;
using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda;
using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneOneri;
using Init.Sigepro.FrontEnd.AppLogic.Services.Navigation;
using Init.Sigepro.FrontEnd.Infrastructure;
using Init.Sigepro.FrontEnd.Reserved.InserimentoIstanza.Pagamenti;
using Init.Sigepro.FrontEnd.WebForms.AppLogic.GestioneOggetti;
using Init.Sigepro.FrontEnd.WebForms.AppLogic.GestioneOggetti.PostedFileSpecifications;
using log4net;
using Ninject;
using System;
using System.Linq;
using System.Web.UI.WebControls;

namespace Init.Sigepro.FrontEnd.Reserved.InserimentoIstanza
{
    public partial class GestioneOneri : IstanzeStepPage
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

        #region Specifications utilizzate nella pagina
        public class DomandaContieneAlmenoUnOnere : ISpecification<IDomandaOnlineReadInterface>
        {
            #region ISpecification<IEnumerable<OneriDomandaRow>> Members

            public bool IsSatisfiedBy(IDomandaOnlineReadInterface item)
            {
                return item.Oneri.Oneri.Count() > 0;
            }

            #endregion
        }

        public class ImportoDaPagareUgualeAZero : ISpecification<IDomandaOnlineReadInterface>
        {
            #region ISpecification<double> Members

            public bool IsSatisfiedBy(IDomandaOnlineReadInterface item)
            {
                return item.Oneri.TotalePagato <= 0.01m;
            }

            #endregion
        }

        public class DomandaContieneAttestazioneDiPagamento : ISpecification<IDomandaOnlineReadInterface>
        {
            #region ISpecification<DomandaOnline> Members

            public bool IsSatisfiedBy(IDomandaOnlineReadInterface item)
            {
                return item.Oneri.AttestazioneDiPagamento.Presente;
            }

            #endregion
        }

        public class AttestazioneDiPagamentoFirmataDigitalmente : ISpecification<IDomandaOnlineReadInterface>
        {
            #region ISpecification<IDomandaOnlineReadInterface> Members

            public bool IsSatisfiedBy(IDomandaOnlineReadInterface item)
            {
                return item.Oneri.AttestazioneDiPagamento.FirmatoDigitalmente;
            }

            #endregion
        }

        public class UtenteDichiaraDiNonAvereOneri : ISpecification<IDomandaOnlineReadInterface>
        {
            #region ISpecification<DomandaOnline> Members

            public bool IsSatisfiedBy(IDomandaOnlineReadInterface item)
            {
                return item.Oneri.DichiaraDiNonAvereOneriDaPagare;
            }

            #endregion
        }


        #endregion

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

        public string TestoOnereNonDovuto
        {
            get { return this.grigliaOneriEndo.TestoOnereNonDovuto; }
            set { this.grigliaOneriEndo.TestoOnereNonDovuto = this.grigliaOneriIntervento.TestoOnereNonDovuto = value; }
        }


        #endregion

        #region Flags per la gestione della visualizzazione

        public bool ContieneOneriIntervento
        {
            get
            {
                return this.ReadFacade.Domanda.Oneri.Oneri.Where(x => x.Provenienza == OnereFrontoffice.ProvenienzaOnereEnum.Intervento).Count() > 0;
            }
        }

        public bool ContieneOneriEndo
        {
            get
            {
                return this.ReadFacade.Domanda.Oneri.Oneri.Where(x => x.Provenienza == OnereFrontoffice.ProvenienzaOnereEnum.Endoprocedimento).Count() > 0;
            }
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
            this.OneriDomandaService.SincronizzaOneri(this.IdDomanda, ComportamentoSincronizzazioneOneriSenzaImporto.Includi);
        }

        public override bool CanEnterStep()
        {
            return new DomandaContieneAlmenoUnOnere().IsSatisfiedBy(this.ReadFacade.Domanda);
        }

        public override bool CanExitStep()
        {
            if (this.Errori.Count > 0)
                return false;


            if (new ImportoDaPagareUgualeAZero().And(new UtenteDichiaraDiNonAvereOneri()).IsSatisfiedBy(this.ReadFacade.Domanda))
                return true;

            if (this.VerificaFirmaDigitaleBollettino && new DomandaContieneAttestazioneDiPagamento().And(new AttestazioneDiPagamentoFirmataDigitalmente()).IsSatisfiedBy(this.ReadFacade.Domanda))
                return true;

            if (!this.VerificaFirmaDigitaleBollettino && new DomandaContieneAttestazioneDiPagamento().IsSatisfiedBy(this.ReadFacade.Domanda))
                return true;

            this.Errori.Add("Per poter proseguire è necessario allegare una copia firmata digitalmente della ricevuta attestante l'avvenuto pagamento");

            return false;

        }
        /*
		public class EstremiPagamentoDataExtractor
		{
			public class ExtractionResult
			{
				public IEnumerable<EstremiPagamento> Estremi { get; private set; }
				public IEnumerable<string> Errori { get; private set; }
				public ExtractionResult(IEnumerable<EstremiPagamento> estremi, IEnumerable<string> errori)
				{
					this.Estremi = estremi;
					this.Errori = errori;
				}
			}

			private enum ProvenienzaOnere
			{
				Intervento,
				Endo
			}

			Repeater _repeaterIntervento;
			Repeater _repeaterEndo;
			List<string> _errori = new List<string>();
			List<EstremiPagamento> _estremi = new List<EstremiPagamento>();

			public EstremiPagamentoDataExtractor(Repeater repeaterIntervento, Repeater repeaterEndo)
			{
				this._repeaterIntervento = repeaterIntervento;
				this._repeaterEndo = repeaterEndo;
			}

			public ExtractionResult EstraiDati(bool ignoraErrori)
			{
				this._errori = new List<string>();
				this._estremi = new List<EstremiPagamento>();

				EstraiDatiDaRepeater(this._repeaterIntervento, ProvenienzaOnere.Intervento, ignoraErrori);
				EstraiDatiDaRepeater(this._repeaterEndo, ProvenienzaOnere.Endo, ignoraErrori);

				return new ExtractionResult(this._estremi, this._errori);
			}

			private void EstraiDatiDaRepeater(Repeater repeater, ProvenienzaOnere provenienza, bool ignoraErrori)
			{
				foreach (var item in repeater.Items.Cast<RepeaterItem>())
				{
					var errori = new List<string>();

					var hidIdOnere = (HiddenField)item.FindControl("hidIdOnere");
					var hidCodiceEndoOIntervento = (HiddenField)item.FindControl("hidCodiceEndoOIntervento");
					var chkNonPagato = (CheckBox)item.FindControl("chkNonPagato");
					var ddlTipoPagamento = (DropDownList)item.FindControl("ddlTipoPagamento");
					var txtDataPagamento = (DateTextBox)item.FindControl("txtDataPagamento");
					var txtNumeroOperazione = (TextBox)item.FindControl("txtNumeroOperazione");
					var lblNomeOnere = (Literal)item.FindControl("lblNomeOnere");
					var txtImporto = (FloatTextBox)item.FindControl("txtImporto");

					if (chkNonPagato.Checked)
						continue;

					if (String.IsNullOrEmpty(ddlTipoPagamento.SelectedValue.Trim()))
						errori.Add("Specificare una modalità di pagamento per l'onere \"" + lblNomeOnere.Text + "\"");

					if (String.IsNullOrEmpty(txtDataPagamento.Text.Trim()))
						errori.Add("Specificare una data di pagamento per l'onere \"" + lblNomeOnere.Text + "\"");

					if (String.IsNullOrEmpty(txtNumeroOperazione.Text.Trim()))
						errori.Add("Specificare i riferimenti del pagamento per l'onere \"" + lblNomeOnere.Text + "\"");

					if (errori.Count > 0)
					{
						this._errori.AddRange(errori);

						if (!ignoraErrori)
							continue;
					}

					EstremiPagamento estremi;

					var idEndoOIntervento = Convert.ToInt32(hidCodiceEndoOIntervento.Value);
					var idOnere = Convert.ToInt32(hidIdOnere.Value);
					var idTipoPagamento = ddlTipoPagamento.SelectedValue;
					var tipoPagamento = ddlTipoPagamento.SelectedItem.Text;
					var data = txtDataPagamento.DateValue;
					var numero = txtNumeroOperazione.Text;
					var importo = txtImporto.ValoreFloat;

					if (provenienza == ProvenienzaOnere.Intervento)
						estremi = EstremiPagamento.CreaPerIntervento(idEndoOIntervento, idOnere, idTipoPagamento, tipoPagamento, data, numero, importo); 
					else
						estremi = EstremiPagamento.CreaPerEndo(idEndoOIntervento, idOnere, idTipoPagamento, tipoPagamento, data, numero, importo);

					this._estremi.Add(estremi);
				}
			}
		}
        */
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

            this.grigliaOneriIntervento.Visible = this.ContieneOneriIntervento;
            this.grigliaOneriIntervento.EtichettaColonnaCausale = this.EtichettaColonnaCausaleIntervento;
            this.grigliaOneriIntervento.ModalitaPagamento = tipiPagamento;
            this.grigliaOneriIntervento.DataSource = this.ReadFacade.Domanda.Oneri.OneriIntervento;
            this.grigliaOneriIntervento.DataBind();

            this.grigliaOneriEndo.Visible = this.ContieneOneriEndo;
            this.grigliaOneriEndo.EtichettaColonnaCausale = this.EtichettaColonnaCausaleEndo;
            this.grigliaOneriEndo.ModalitaPagamento = tipiPagamento;
            this.grigliaOneriEndo.DataSource = this.ReadFacade.Domanda.Oneri.OneriEndoprocedimenti;
            this.grigliaOneriEndo.DataBind();

            this.chkAssenzaOneri.Checked = this.ReadFacade.Domanda.Oneri.DichiaraDiNonAvereOneriDaPagare;

            // this.MostraCheckDichiarazioneOneri = new ImportoDaPagareUgualeAZero().IsSatisfiedBy(ReadFacade.Domanda);
            var contieneAttestazionePagamento = this.ReadFacade.Domanda.Oneri.AttestazioneDiPagamento.Presente;   //new DomandaContieneAttestazioneDiPagamento().IsSatisfiedBy( ReadFacade.Domanda );
            var codiceOggetto = (int?)null;
            var nomeFile = String.Empty;

            if (contieneAttestazionePagamento)
            {
                var attestazionediPagamento = this.ReadFacade.Domanda.Oneri.AttestazioneDiPagamento;

                codiceOggetto = attestazionediPagamento.CodiceOggetto;
                nomeFile = attestazionediPagamento.NomeFile;
            }

            this._logger.DebugFormat("Contiene attestazione pagamento: {0}, codiceoggetto={1}, nomefile={2}", contieneAttestazionePagamento, codiceOggetto, nomeFile);

            if (!contieneAttestazionePagamento)
                this.MostraVistaCaricamentoFile();
            else
                this.MostraVistaDettaglioFile();
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