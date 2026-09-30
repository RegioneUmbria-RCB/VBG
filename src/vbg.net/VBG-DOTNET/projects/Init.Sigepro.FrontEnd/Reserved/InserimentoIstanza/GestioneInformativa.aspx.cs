using Init.Sigepro.FrontEnd.AppLogic.GestioneComuni;
using Init.Sigepro.FrontEnd.AppLogic.GestioneDatiExtra;
using Init.Sigepro.FrontEnd.AppLogic.Services.Domanda;
using Init.Sigepro.FrontEnd.Infrastructure.StepsDomanda.Attributi;
using Init.Sigepro.FrontEnd.Reserved.InserimentoIstanza.Exceptions;
using Ninject;
using System;
using System.Linq;

namespace Init.Sigepro.FrontEnd.Reserved.InserimentoIstanza
{
    public partial class GestioneInformativa : IstanzeStepPage
    {
        protected static class Constants
        {
            public const string ErroreInformativaNonAccettata = "Per proseguire è necessario leggere ed accettare le condizioni riportate";
        }

        [Inject]
        public IComuniService _comuniService { get; set; }

        [Inject]
        public DatiDomandaService _datiDomandaService { get; set; }

        [Inject]
        public DatiExtraService _datiExtraService { get; set; }

        [StepProperty("Se impostato a true ignora il controllo sui comuni di riferimento (abilita lo step anche se non ci sono comuni di riferimento)", ValoreDefault = "false")]
        public bool IgnoraControlloComuneRiferimento
        {
            get => this.ViewstateGet(nameof(this.IgnoraControlloComuneRiferimento), false);
            set => this.ViewStateSet(nameof(this.IgnoraControlloComuneRiferimento), value);
        }

        [StepProperty("Codice comune per cui attivare lo step, se lasciato vuoto verrà attivato per tutti i comuni. Ha priorità più bassa rispetto a ListaComuniDiRiferimento", ValoreDefault = "")]
        public string CodiceComuneDiRiferimento
        {
            get { return this.ViewstateGet("CodiceComuneDiRiferimento", ""); }
            set { this.ViewStateSet("CodiceComuneDiRiferimento", value); }
        }

        [StepProperty("Lista di codici comune separata da virgola per cui attivare lo step, se lasciato vuoto verrà attivato per tutti i comuni. Ha priorità più alta rispetto a CodiceComuneDiRiferimento", ValoreDefault = "")]

        public string ListaComuniDiRiferimento
        {
            get { return this.ViewstateGet(nameof(this.ListaComuniDiRiferimento), ""); }
            set { this.ViewStateSet(nameof(this.ListaComuniDiRiferimento), value); }
        }

        public string MessaggioErrore
        {
            get { return this.ViewstateGet("MessaggioErrore", ""); }
            set { this.ViewStateSet("MessaggioErrore", value); }
        }

        [StepProperty("Testo della check che permette di accettare l'informativa")]
        public string TestoAccettazioneInformativa
        {
            get
            {
                return this.chkAccetto.Text;
            }
            set
            {
                this.chkAccetto.Text = "&nbsp;<b>" + value + "</b>";
            }
        }

        [StepProperty("Testo dell'informativa da accettare. Utilizzare il segnaposto {0} per recuperare il nome del comune attualmente selezionato")]
        public string TestoInformativa
        {
            get
            {
                return this.ltrTestoInformativa.Text;
            }
            set
            {
                this.ltrTestoInformativa.Text = String.Format(value, this.GetNomeComune());
            }
        }

        [StepProperty("Se impostato a false non mostra la check di accettazione, l'informativa verrà accettata implicitamente")]
        public bool MostraCheckAccettazione
        {
            get { object o = this.ViewState["MostraCheckAccettazione"]; return o == null ? true : (bool)o; }
            set { this.ViewState["MostraCheckAccettazione"] = value; }
        }

        [StepProperty("Nome della chiave con cui salvare l'accettazione. Permette di utilizzare più volte lo step con chiavi diverse", ValoreDefault = "FlagInformativa")]
        public string ChiaveInformativa
        {
            get => this.ViewstateGet(nameof(this.ChiaveInformativa), "FlagInformativa");
            set => this.ViewStateSet(nameof(this.ChiaveInformativa), value);
        }




        protected void Page_Load(object sender, EventArgs e)
        {
            // il service si occupa del salvataggio dei dati
            this.Master.IgnoraSalvataggioDati = true;

            if (!this.IsPostBack)
                this.DataBind();
        }

        public override void DataBind()
        {
            var f = this._datiExtraService.Get<string>(this.IdDomanda, this.ChiaveInformativa);
            this.chkAccetto.Checked = !String.IsNullOrEmpty(f) ? Convert.ToBoolean(f) : false;

            if (!this.MostraCheckAccettazione)
            {
                this.chkAccetto.Checked = true;
                this.chkAccetto.Visible = false;
            }
        }

        public override bool CanEnterStep()
        {
            if (this.IgnoraControlloComuneRiferimento)
            {
                return true;
            }

            var listaComuniArray = this.ListaComuniDiRiferimento;

            if (String.IsNullOrEmpty(listaComuniArray))
            {
                listaComuniArray = this.CodiceComuneDiRiferimento;
            }

            var listaComuni = listaComuniArray.Split(',').Select(x => x.Trim()).Where(x => x.Length > 0);

            return listaComuni.Contains(this.ReadFacade.Domanda.AltriDati.CodiceComune);
        }

        public override bool CanExitStep()
        {
            try
            {
                if (!this.chkAccetto.Checked)
                    throw new StepException(Constants.ErroreInformativaNonAccettata);

                return this.chkAccetto.Checked;
            }
            catch (StepException ex)
            {
                this.Errori.AddRange(ex.ErrorMessages);

                return false;
            }
        }

        public override void OnBeforeExitStep()
        {
            this._datiExtraService.Set<string>(this.IdDomanda, this.ChiaveInformativa, this.chkAccetto.Checked.ToString());
        }

        private string GetNomeComune()
        {
            var comune = this._comuniService.GetDatiComune(this.ReadFacade.Domanda.AltriDati.CodiceComune);

            if (comune == null)
            {
                return String.Empty;
            }

            return comune.Comune;
        }
    }
}
