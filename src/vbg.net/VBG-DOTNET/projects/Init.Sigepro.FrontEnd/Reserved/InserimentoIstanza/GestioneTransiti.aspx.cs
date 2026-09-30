using Init.Sigepro.FrontEnd.AppLogic.GestioneTransiti;
using Ninject;
using System;

namespace Init.Sigepro.FrontEnd.Reserved.InserimentoIstanza
{
    public partial class GestioneTransiti : IstanzeStepPage
    {
        [Inject]
        protected IGestioneTransitiService _transitiService { get; set; }

        #region proprietà lette da wf

        public string TipoOperazione
        {
            get { return this.ViewstateGet("TipoOperazione", "NonDefinita"); }
            set { this.ViewStateSet("TipoOperazione", value); }
        }

        public int IdCampoDinamico
        {
            get => this.ViewstateGet("IdCampoDinamico", -1);
            set => this.ViewStateSet("IdCampoDinamico", value);
        }

        public bool MostraAutorizzazioniRimanenti
        {
            get => this.ViewstateGet("MostraAutorizzazioniRimanenti", true);
            set => this.ViewStateSet("MostraAutorizzazioniRimanenti", value);
        }

        #endregion

        protected void Page_Load(object sender, EventArgs e)
        {
            this.Master.MostraBottoneAvanti = false;

            if (!this.IsPostBack)
            {
                this.DataBind();
            }
        }

        public override void DataBind()
        {
            var ultimaRicerca = this._transitiService.GetRiferimentiPRaticaCercata(this.IdDomanda);

            if (ultimaRicerca == null)
            {
                return;
            }

            this.txtDataAutorizzazione.DateValue = ultimaRicerca.DataAutorizzazione;
            this.txtNumeroAutorizzazione.Value = ultimaRicerca.NumeroAutorizzazione;

            this.cmdRicerca_Click(this, EventArgs.Empty);
        }


        protected void cmdRicerca_Click(object sender, EventArgs e)
        {
            this.pnlRisultati.Visible = false;
            this.Master.MostraBottoneAvanti = false;

            var azienda = this.ReadFacade.Domanda.Anagrafiche.GetAzienda();

            if (azienda == null)
            {
                this.Errori.Add("Nessuna azienda registrata tra le anagrafiche della domanda");
                return;
            }

            if (String.IsNullOrEmpty(this.txtNumeroAutorizzazione.Text))
            {
                this.Errori.Add("Specificare un numero autorizzazione");
                return;
            }

            if (!this.txtDataAutorizzazione.DateValue.HasValue)
            {
                this.Errori.Add("Specificare la data dell'autorizzazione");
                return;
            }

            try
            {
                var autorizzazione = this._transitiService.TrovaAutorizzazione(azienda.Codicefiscale, azienda.PartitaIva, this.txtNumeroAutorizzazione.Text, this.txtDataAutorizzazione.DateValue.Value);

                if (autorizzazione == null)
                {
                    this.Errori.Add($"Autorizzazione {this.txtNumeroAutorizzazione.Text} del {this.txtDataAutorizzazione.Value} non trovata, verificare i dati immessi.");
                    return;
                }


                if (autorizzazione != null)
                {
                    this.pnlRisultati.Visible = true;

                    this.hidCodiceIstanza.Value = autorizzazione.CodiceIstanza.ToString();

                    this.ltrRiferimentiAutorizzaizone.Text = autorizzazione.Riferimenti.ToString();
                    this.ltrAutorizzazioniRimanenti.Text = autorizzazione.TransitiRimanenti.ToString();

                    this.lblDataInizioValidita.Visible = false;

                    if (autorizzazione.DataValidita.HasValue)
                    {
                        this.lblDataInizioValidita.Visible = true;
                        this.lblDataInizioValidita.Value = autorizzazione.DataValidita.Value.ToString("dd/MM/yyyy");
                    }

                    this.lblDataFineValidita.Visible = false;

                    if (autorizzazione.DataScadenza.HasValue)
                    {
                        this.lblDataFineValidita.Visible = true;
                        this.lblDataFineValidita.Value = autorizzazione.DataScadenza.Value.ToString("dd/MM/yyyy");
                    }

                    this.gvOperazioni.DataSource = autorizzazione.Operazioni;
                    this.gvOperazioni.DataBind();

                    var tipoOperazione = AutorizzazioneTransito.TipoOperazione.NonDefinita;

                    if (!Enum.TryParse<AutorizzazioneTransito.TipoOperazione>(this.TipoOperazione, out tipoOperazione))
                    {
                        this.Errori.Add("La control property \"TipoOperazione\" contiene un valore non valido: " + this.TipoOperazione);
                        tipoOperazione = AutorizzazioneTransito.TipoOperazione.NonDefinita;
                    }

                    var permetteOperazione = autorizzazione.PermetteOperazione(tipoOperazione);
                    this.Master.MostraBottoneAvanti = permetteOperazione;

                    if (!permetteOperazione)
                    {
                        this.Errori.Add("L'operazione selezionata non è valida per questa autorizzazione");
                    }

                    this.MostraAutorizzazioniRimanenti = autorizzazione.TransitiConsentiti >= 0;
                }
            }
            catch (Exception ex)
            {
                this.Errori.Add(ex.Message.Replace("it.gruppoinit.pal.gp.core.service.exception.BusinessValidationException: ", ""));
            }
        }

        public override void OnBeforeExitStep()
        {
            var valoreCercato = new RiferimentiPraticaCercata
            {
                NumeroAutorizzazione = this.txtNumeroAutorizzazione.Value,
                DataAutorizzazione = this.txtDataAutorizzazione.DateValue.Value
            };
            var idPraticaRiferimento = Convert.ToInt32(this.hidCodiceIstanza.Value);

            this._transitiService.SalvaDatiAutorizzazioneTrovata(this.IdDomanda, new DatiAutorizzazioneTrovata(valoreCercato, idPraticaRiferimento, this.IdCampoDinamico));

            base.OnBeforeExitStep();
        }
    }
}