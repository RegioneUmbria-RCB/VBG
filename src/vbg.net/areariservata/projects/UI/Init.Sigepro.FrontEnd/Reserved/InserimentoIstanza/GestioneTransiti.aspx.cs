using Init.Sigepro.FrontEnd.AppLogic.GestioneDatiExtra;
using Init.Sigepro.FrontEnd.AppLogic.GestioneTransiti;
using Init.Sigepro.FrontEnd.AppLogic.SalvataggioDomanda;
using Init.Sigepro.FrontEnd.AppLogic.Services.Domanda;
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
            get { return ViewstateGet("TipoOperazione", "NonDefinita"); }
            set { ViewStateSet("TipoOperazione", value); }
        }

        public int IdCampoDinamico
        {
            get => ViewstateGet("IdCampoDinamico", -1);
            set => ViewStateSet("IdCampoDinamico", value);
        }

        public bool MostraAutorizzazioniRimanenti {
            get => ViewstateGet("MostraAutorizzazioniRimanenti", true);
            set => ViewStateSet("MostraAutorizzazioniRimanenti", value);
        }

        #endregion

        protected void Page_Load(object sender, EventArgs e)
        {
            if (!IsPostBack)
            {
                DataBind();
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
            pnlRisultati.Visible = false;
            Master.MostraBottoneAvanti = false;

            var azienda = ReadFacade.Domanda.Anagrafiche.GetAzienda();

            if (azienda == null)
            {
                Errori.Add("Nessuna azienda registrata tra le anagrafiche della domanda");
                return;
            }

            if (String.IsNullOrEmpty(txtNumeroAutorizzazione.Text))
            {
                Errori.Add("Specificare un numero autorizzazione");
                return;
            }

            if (!txtDataAutorizzazione.DateValue.HasValue)
            {
                Errori.Add("Specificare la data dell'autorizzazione");
                return;
            }

            try
            {
                var autorizzazione = _transitiService.TrovaAutorizzazione(azienda.Codicefiscale, azienda.PartitaIva, txtNumeroAutorizzazione.Text, txtDataAutorizzazione.DateValue.Value);

                if (autorizzazione == null)
                {
                    Errori.Add($"Autorizzazione {txtNumeroAutorizzazione.Text} del {txtDataAutorizzazione.Value} non trovata, verificare i dati immessi.");
                    return;
                }


                if (autorizzazione != null)
                {
                    pnlRisultati.Visible = true;

                    hidCodiceIstanza.Value = autorizzazione.CodiceIstanza.ToString();

                    ltrRiferimentiAutorizzaizone.Text = autorizzazione.Riferimenti.ToString();
                    ltrAutorizzazioniRimanenti.Text = autorizzazione.TransitiRimanenti.ToString();

                    lblDataInizioValidita.Visible = false;

                    if (autorizzazione.DataValidita.HasValue)
                    {
                        lblDataInizioValidita.Visible = true;
                        lblDataInizioValidita.Value = autorizzazione.DataValidita.Value.ToString("dd/MM/yyyy");
                    }

                    lblDataFineValidita.Visible = false;

                    if (autorizzazione.DataScadenza.HasValue)
                    {
                        lblDataFineValidita.Visible = true;
                        lblDataFineValidita.Value = autorizzazione.DataScadenza.Value.ToString("dd/MM/yyyy");
                    }

                    gvOperazioni.DataSource = autorizzazione.Operazioni;
                    gvOperazioni.DataBind();

                    var tipoOperazione = AutorizzazioneTransito.TipoOperazione.NonDefinita;

                    if (!Enum.TryParse<AutorizzazioneTransito.TipoOperazione>(TipoOperazione, out tipoOperazione))
                    {
                        Errori.Add("La control property \"TipoOperazione\" contiene un valore non valido: " + TipoOperazione);
                        tipoOperazione = AutorizzazioneTransito.TipoOperazione.NonDefinita;
                    }

                    var permetteOperazione = autorizzazione.PermetteOperazione(tipoOperazione);
                    Master.MostraBottoneAvanti = permetteOperazione;

                    if (!permetteOperazione)
                    {
                        Errori.Add("L'operazione selezionata non è valida per questa autorizzazione");
                    }

                    this.MostraAutorizzazioniRimanenti = autorizzazione.TransitiConsentiti >= 0;
                }
            }
            catch (Exception ex)
            {
                Errori.Add(ex.Message);
            }
        }

        public override void OnBeforeExitStep()
        {
            var valoreCercato = new RiferimentiPraticaCercata
            {
                NumeroAutorizzazione = this.txtNumeroAutorizzazione.Value,
                DataAutorizzazione = this.txtDataAutorizzazione.DateValue.Value
            };
            var idPraticaRiferimento = Convert.ToInt32(hidCodiceIstanza.Value);

            this._transitiService.SalvaDatiAutorizzazioneTrovata(this.IdDomanda, new DatiAutorizzazioneTrovata(valoreCercato, idPraticaRiferimento, this.IdCampoDinamico));
            
            base.OnBeforeExitStep();
        }
    }
}