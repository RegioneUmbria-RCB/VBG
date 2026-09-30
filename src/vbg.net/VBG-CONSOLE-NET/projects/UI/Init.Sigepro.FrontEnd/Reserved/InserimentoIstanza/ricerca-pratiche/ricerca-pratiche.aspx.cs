using Init.Sigepro.FrontEnd.AppLogic.GestioneRicercaPratiche;
using Init.Sigepro.FrontEnd.AppLogic.RicercaPraticheWs;
using Init.Sigepro.FrontEnd.Infrastructure.Serialization;
using Ninject;
using System;

namespace Init.Sigepro.FrontEnd.Reserved.InserimentoIstanza.ricerca_pratiche
{
    public partial class ricerca_pratiche : IstanzeStepPage
    {

        [Inject]
        public IRicercaPraticheService _ricercaPraticheService { get; set; }

        public bool SelezionePraticaCollegataObbligatoria
        {
            get => this.ViewstateGet(nameof(this.SelezionePraticaCollegataObbligatoria), false);
            set => this.ViewStateSet(nameof(this.SelezionePraticaCollegataObbligatoria), value);
        }

        public string ErroreNumeroProtocolloObbligatorio
        {
            get => this.ViewstateGet(nameof(this.ErroreNumeroProtocolloObbligatorio), $"{this.txtNumeroProtocollo.Label} è obbligatorio");
            set => this.ViewStateSet(nameof(this.ErroreNumeroProtocolloObbligatorio), value);
        }

        public string ErroreDataProtocolloObbligatoria
        {
            get => this.ViewstateGet(nameof(this.ErroreDataProtocolloObbligatoria), $"{this.txtDataProtocollo.Label} è obbligatorio");
            set => this.ViewStateSet(nameof(this.ErroreDataProtocolloObbligatoria), value);
        }

        public string TestoModificaCollegamento
        {
            get => this.cmdAnnullaCollegamento.Text;
            set => this.cmdAnnullaCollegamento.Text = value;
        }

        public string TestoMantieniCollegamento
        {
            get => this.cmdMantieniCollegamento.Text;
            set => this.cmdMantieniCollegamento.Text = value;
        }

        public string TestoConfermaAnnullamentoCollegamento
        {
            get => this.ltrTestoConfermaAnnullamentoCollegamento.Text;
            set => this.ltrTestoConfermaAnnullamentoCollegamento.Text = value;
        }

        public string ErroreNumeroPraticaObbligatorio
        {
            get => this.ViewstateGet(nameof(this.ErroreNumeroPraticaObbligatorio), $"{this.txtNumeroIstanza.Label} è obbligatorio");
            set => this.ViewStateSet(nameof(this.ErroreNumeroPraticaObbligatorio), value);
        }

        public string ErroreNessunaPraticaTrovata
        {
            get => this.ltrErrorePraticaNonTrovata.Text;
            set => this.ltrErrorePraticaNonTrovata.Text = value;
        }

        public string TestoBottoneCollegaPratica
        {
            get => this.cmdCopiaDati.Text;
            set => this.cmdCopiaDati.Text = value;
        }

        public string TestoBottoneIgnoraRicerca
        {
            get => this.cmdIgnoraRicerca.Text;
            set
            {
                this.cmdIgnoraRicerca.Text = value;
                this.cmdIgnoraRicerca2.Text = value;
            }
        }

        private RisultatoRicercaPratiche UltimoRisultatoRicerca
        {
            get => this.ViewstateGet<string>(nameof(this.UltimoRisultatoRicerca), null)?.ClassFromXmlString<RisultatoRicercaPratiche>();
            set => this.ViewStateSet(nameof(this.UltimoRisultatoRicerca), value?.ToXmlString());
        }
        public bool CopiaAnagrafiche
        {
            get => this.ViewstateGet(nameof(this.CopiaAnagrafiche), true);
            set => this.ViewStateSet(nameof(this.CopiaAnagrafiche), value);
        }

        public bool CopiaLocalizzazioni
        {
            get => this.ViewstateGet(nameof(this.CopiaLocalizzazioni), true);
            set => this.ViewStateSet(nameof(this.CopiaLocalizzazioni), value);
        }

        protected void Page_Load(object sender, EventArgs e)
        {
            this.Master.MostraBottoneAvanti = false;

            if (!this.IsPostBack)
            {
                if (this.RicercaGiaEffettuata())
                {
                    this.MostraVistaDettaglioDaRicercaPrecedente();
                }
            }
        }

        private void MostraVistaDettaglioDaRicercaPrecedente()
        {
            // TODO: Recuperare dati di dettaglio
            this.UltimoRisultatoRicerca = this._ricercaPraticheService.GetRisultatoRicerca(this.IdDomanda);

            this.MostraVistaDettaglio(true);
        }

        private bool RicercaGiaEffettuata()
        {
            return this._ricercaPraticheService.RisultatoRicercaPresente(this.IdDomanda);
        }

        protected void cmdCercaProtocollo_Click(object sender, EventArgs e)
        {
            if (String.IsNullOrEmpty(this.txtNumeroProtocollo.Text))
            {
                this.Errori.Add(this.ErroreNumeroProtocolloObbligatorio);
                return;
            }

            if (!this.txtDataProtocollo.DateValue.HasValue)
            {
                this.Errori.Add(this.ErroreDataProtocolloObbligatoria);
                return;
            }

            try
            {
                this.UltimoRisultatoRicerca = this._ricercaPraticheService.TrovaPraticaDaEstremiProtocollo(this.IdDomanda, this.txtNumeroProtocollo.Text, this.txtDataProtocollo.DateValue.Value);

                if (this.UltimoRisultatoRicerca == null)
                {
                    this.multiView.SetActiveView(this.nonTrovataView);

                    return;
                }

                this.MostraVistaDettaglio(false);
            }
            catch (Exception ex)
            {
                this.Errori.Add(ex.Message);
            }
        }

        protected void cmdCercaPratica_Click(object sender, EventArgs e)
        {
            if (String.IsNullOrEmpty(this.txtNumeroIstanza.Text))
            {
                this.Errori.Add(this.ErroreNumeroPraticaObbligatorio);
                return;
            }

            try
            {
                this.UltimoRisultatoRicerca = this._ricercaPraticheService.TrovaPraticaDaNumeroIstanza(this.IdDomanda, this.txtNumeroIstanza.Text);

                if (this.UltimoRisultatoRicerca == null)
                {
                    this.multiView.SetActiveView(this.nonTrovataView);

                    return;
                }

                this.MostraVistaDettaglio(false);
            }
            catch (Exception ex)
            {
                this.Errori.Add(ex.Message);
            }
        }

        private void MostraVistaDettaglio(bool daRicercaEsistente)
        {
            this.ricercapratichedettaglio.DataSource = this.UltimoRisultatoRicerca;
            this.ricercapratichedettaglio.DataBind();

            this.multiView.SetActiveView(this.dettaglioView);

            this.cmdMantieniCollegamento.Visible = false;
            this.cmdAnnullaCollegamento.Visible = false;
            this.cmdCopiaDati.Visible = false;
            this.cmdNuovaRicerca2.Visible = false;

            if (daRicercaEsistente)
            {
                this.cmdMantieniCollegamento.Visible = true;
                this.cmdAnnullaCollegamento.Visible = true;
            }
            else
            {
                this.cmdCopiaDati.Visible = true;
                this.cmdNuovaRicerca2.Visible = true;
            }
        }



        protected void cmdNuovaRicerca_Click(object sender, EventArgs e)
        {
            this.UltimoRisultatoRicerca = null;
            this.multiView.SetActiveView(this.ricercaView);
        }

        protected void cmdCopiaDati_Click(object sender, EventArgs e)
        {
            var flags = new CopiaDatiDomandaFlags
            {
                CopiaAnagrafiche = this.CopiaAnagrafiche,
                CopiaLocalizzazioni = this.CopiaLocalizzazioni
            };

            this._ricercaPraticheService.ImpostaDatiPraticaDaRisultatoRicerca(this.IdDomanda, this.UltimoRisultatoRicerca, flags);

            this.Master.cmdNextStep_Click(this, EventArgs.Empty);
        }

        protected void cmdIgnoraRicerca_Click(object sender, EventArgs e)
        {
            this._ricercaPraticheService.IgnoraRicercaPratiche(this.IdDomanda);
            this.Master.cmdNextStep_Click(this, EventArgs.Empty);
        }

        protected void cmdManieniCollegamento_Click(object sender, EventArgs e)
        {
            this.Master.cmdNextStep_Click(this, EventArgs.Empty);
        }

        protected void bmConfermaEliminazioneCollegamento_OkClicked(object sender, EventArgs e)
        {
            this._ricercaPraticheService.IgnoraRicercaPratiche(this.IdDomanda);
            this.cmdNuovaRicerca_Click(this, EventArgs.Empty);
        }
    }
}