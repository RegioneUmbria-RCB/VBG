using System;
using System.Collections.Generic;
using System.Linq;
using System.Web.UI.WebControls;
using Init.Sigepro.FrontEnd.AppLogic.GestioneOggetti;
using Init.Sigepro.FrontEnd.AppLogic.GestioneOneri;
using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda;
using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneOneri;
using Init.Sigepro.FrontEnd.Infrastructure;
using Init.Utils.Web.UI;
using log4net;
using Ninject;
using Init.Sigepro.FrontEnd.Reserved.InserimentoIstanza.Pagamenti;
using Init.Sigepro.FrontEnd.Infrastructure.UrlsAndPaths;
using Init.Sigepro.FrontEnd.QsParameters;
using Init.Sigepro.FrontEnd.AppLogic.Services.Navigation;

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
        public RedirectService _redirectService { get; set; }

        ILog _logger = LogManager.GetLogger(typeof(GestioneOneri));

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
				return item.Oneri.TotalePagato <= 0.01d;
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
			get { object o = this.ViewState[ "TestoDichiarazioneAssenzaOneri" ]; return o == null ? "Dichiaro di non avere oneri da pagare" : (string)o; }
			set { this.ViewState[ "TestoDichiarazioneAssenzaOneri" ] = value; }
		}

        public string TestoOereNonDovuto
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
				return ReadFacade.Domanda.Oneri.Oneri.Where(x => x.Provenienza == OnereFrontoffice.ProvenienzaOnereEnum.Intervento).Count() > 0;
			}
		}

		public bool ContieneOneriEndo
		{
			get
			{
				return ReadFacade.Domanda.Oneri.Oneri.Where( x => x.Provenienza == OnereFrontoffice.ProvenienzaOnereEnum.Endoprocedimento).Count() > 0;
			}
		}
		#endregion

		protected void Page_Load(object sender, EventArgs e)
		{
			// Il Service si occupa del salvataggio dei dati
			Master.IgnoraSalvataggioDati = true;

			if (!IsPostBack)
				DataBind();
		}


		#region Ciclo di vita dello step
		public override void OnInitializeStep()
		{
			OneriDomandaService.SincronizzaOneri(IdDomanda);
		}

		public override bool CanEnterStep()
		{
			return new DomandaContieneAlmenoUnOnere().IsSatisfiedBy( ReadFacade.Domanda );
		}

		public override bool CanExitStep()
		{
			if (Errori.Count > 0)
				return false;


			if (new ImportoDaPagareUgualeAZero().And( new UtenteDichiaraDiNonAvereOneri()).IsSatisfiedBy(ReadFacade.Domanda))
				return true;

			if( VerificaFirmaDigitaleBollettino &&  new DomandaContieneAttestazioneDiPagamento().And( new AttestazioneDiPagamentoFirmataDigitalmente()).IsSatisfiedBy(ReadFacade.Domanda))
				return true;

			if (!VerificaFirmaDigitaleBollettino && new DomandaContieneAttestazioneDiPagamento().IsSatisfiedBy(ReadFacade.Domanda))
				return true;

			Errori.Add("Per poter proseguire è necessario allegare una copia firmata digitalmente della ricevuta attestante l'avvenuto pagamento");

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
			var estremi = new EstremiPagamentoDataExtractor(this.grigliaOneriIntervento.Repeater , this.grigliaOneriEndo.Repeater).EstraiDati(false);

			this.Errori.AddRange(estremi.Errori);

			if (this.Errori.Count == 0 )
				OneriDomandaService.SpecificaEstremiPagamento(IdDomanda, estremi.Estremi);
		}

		#endregion

		public override void DataBind()
		{
			var modalitaPagamento = this.OneriDomandaService.GetListaModalitaPagamento().ToList();
				modalitaPagamento.Insert(0,new TipoPagamento("",""));

			grigliaOneriIntervento.Visible= ContieneOneriIntervento;
			grigliaOneriIntervento.EtichettaColonnaCausale = EtichettaColonnaCausaleIntervento;
			grigliaOneriIntervento.ModalitaPagamento = modalitaPagamento;
			grigliaOneriIntervento.DataSource = ReadFacade.Domanda.Oneri.OneriIntervento;
			grigliaOneriIntervento.DataBind();

			grigliaOneriEndo.Visible = ContieneOneriEndo;
			grigliaOneriEndo.EtichettaColonnaCausale = EtichettaColonnaCausaleEndo;
			grigliaOneriEndo.ModalitaPagamento = modalitaPagamento;
			grigliaOneriEndo.DataSource = ReadFacade.Domanda.Oneri.OneriEndoprocedimenti;
			grigliaOneriEndo.DataBind();

			chkAssenzaOneri.Checked = ReadFacade.Domanda.Oneri.DichiaraDiNonAvereOneriDaPagare;

			// this.MostraCheckDichiarazioneOneri = new ImportoDaPagareUgualeAZero().IsSatisfiedBy(ReadFacade.Domanda);
            var contieneAttestazionePagamento = ReadFacade.Domanda.Oneri.AttestazioneDiPagamento.Presente;   //new DomandaContieneAttestazioneDiPagamento().IsSatisfiedBy( ReadFacade.Domanda );
            var codiceOggetto = (int?)null;
            var nomeFile = String.Empty;

            if (contieneAttestazionePagamento)
            {
                var attestazionediPagamento = ReadFacade.Domanda.Oneri.AttestazioneDiPagamento;

                codiceOggetto = attestazionediPagamento.CodiceOggetto;
                nomeFile = attestazionediPagamento.NomeFile;
            }

            this._logger.DebugFormat("Contiene attestazione pagamento: {0}, codiceoggetto={1}, nomefile={2}", contieneAttestazionePagamento, codiceOggetto, nomeFile );

			if (!contieneAttestazionePagamento)
				MostraVistaCaricamentoFile();
			else
				MostraVistaDettaglioFile();
		}

		private void SalvaValoriEstremiOneri()
		{
			var estremi = new EstremiPagamentoDataExtractor(this.grigliaOneriIntervento.Repeater, this.grigliaOneriEndo.Repeater).EstraiDati(true);

			OneriDomandaService.SpecificaEstremiPagamento(IdDomanda, estremi.Estremi);
		}

		protected void cmdUpload_Click(object sender, EventArgs e)
		{
			try
			{
				SalvaValoriEstremiOneri();
				
				
				var file = new BinaryFile(fuCaricaFile, this._validPostedFileSpecification);

				OneriDomandaService.InserisciAttestazioneDiPagamento(IdDomanda,  file);

				DataBind();
			}
			catch (Exception ex)
			{
				_logger.ErrorFormat("Errore durante il caricamento del bollettino in gestioneoneri.aspx: {0}", ex.ToString());

				Errori.Add(ex.Message);
			}

		}

		protected void cmdRimuovi_Click(object sender, EventArgs e)
		{
			try
			{
				SalvaValoriEstremiOneri();

				OneriDomandaService.EliminaAttestazioneDiPagamento(IdDomanda);

				DataBind();
			}
			catch (Exception ex)
			{
				_logger.ErrorFormat("Errore durante la rimozione del bollettino in gestioneoneri.aspx: {0}", ex.ToString());

				Errori.Add(ex.Message);
			}
		}

		protected void cmdFirma_Click(object sender, EventArgs e)
		{
			var codiceOggetto = ReadFacade.Domanda.Oneri.AttestazioneDiPagamento.CodiceOggetto.Value;

			this._redirectService.ToFirmaDigitale(IdDomanda, codiceOggetto);
		}

		protected void chkAssenzaOneri_CheckedChanged(object sender, EventArgs e)
		{
			if (chkAssenzaOneri.Checked)
			{
				OneriDomandaService.ImpostaDichiarazioneDiAssenzaOneriDaPagare(IdDomanda);
			}
			else
			{
				OneriDomandaService.RimuoviDichiarazioneDiAssenzaOneriDaPagare(IdDomanda);
			}
		}

		#region Gestione delle views di caricamento/Visualizzazione attestazione oneri
		private void MostraVistaCaricamentoFile()
		{
			mvCaricamentoBollettino.ActiveViewIndex = VistaFile.Caricamento;
		}

		private void MostraVistaDettaglioFile()
		{
			mvCaricamentoBollettino.ActiveViewIndex = VistaFile.DettagliFile;

			var attestazionediPagamento = ReadFacade.Domanda.Oneri.AttestazioneDiPagamento;

			var codiceOggetto	= attestazionediPagamento.CodiceOggetto;
			var nomeFile		= attestazionediPagamento.NomeFile;
			
			// Dati del file caricato
            var url = UrlBuilder.Url("~/Reserved/MostraOggettoFo.ashx", x => {
                x.Add(new QsAliasComune(IdComune));
                x.Add(new QsSoftware(Software));
                x.Add(new QsCodiceOggetto(codiceOggetto.Value));
                x.Add(new QsIdDomandaOnline(IdDomanda));
            });

            hlFileCaricato.Text			= nomeFile;
			hlFileCaricato.NavigateUrl = ResolveClientUrl(url);

			// Etichetta di errore se firma digitale mancante
			cmdFirma.Visible =
			lblErroreFirma.Visible = false;

			if (this.VerificaFirmaDigitaleBollettino && !attestazionediPagamento.FirmatoDigitalmente)
				cmdFirma.Visible = lblErroreFirma.Visible = true;

			Master.MostraBottoneAvanti = true;
		}
		#endregion
	}
}