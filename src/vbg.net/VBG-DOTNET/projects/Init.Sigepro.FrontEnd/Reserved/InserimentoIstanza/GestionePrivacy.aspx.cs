using Init.Sigepro.FrontEnd.AppLogic.GestioneComuni;
using Init.Sigepro.FrontEnd.AppLogic.Services.Domanda;
using Init.Sigepro.FrontEnd.Reserved.InserimentoIstanza.CondizioniIngressoSteps;
using Init.Sigepro.FrontEnd.Reserved.InserimentoIstanza.CondizioniUscitaSteps;
using Init.Sigepro.FrontEnd.Reserved.InserimentoIstanza.Exceptions;
using Init.SIGePro.DatiDinamici.Markdown;
using Ninject;
using System;

namespace Init.Sigepro.FrontEnd.Reserved.InserimentoIstanza
{
    public partial class GestionePrivacy : IstanzeStepPage
    {
        [Inject]
        public CondizioneIngressoStepSempreVera _condizioneIngresso { get; set; }

        [Inject]
        public CondizioneUscitaPrivacyAccettata _condizioneUscita { get; set; }

        [Inject]
        public IComuniService _comuniService { get; set; }

        [Inject]
        public DatiDomandaService DatiDomandaService { get; set; }

        public string MessaggioErrore
        {
            get { return this.ViewstateGet("MessaggioErrore", ""); }
            set { this.ViewStateSet("MessaggioErrore", value); }
        }

        public string TestoAccettazionePrivacy
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
        public string TestoPrivacy
        {
            get
            {
                return this.ltrTestoPrivacy.Text;
            }
            set
            {
                this.ltrTestoPrivacy.Text = new MarkdownString(String.Format(value, this.GetNomeComune())).Html;
            }
        }
        public bool MostraCheckAccettazione
        {
            get { var o = this.ViewState["MostraCheckAccettazione"]; return o == null ? true : (bool)o; }
            set { this.ViewState["MostraCheckAccettazione"] = value; }
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
            this.chkAccetto.Checked = this.ReadFacade.Domanda.AltriDati.FlagPrivacy;

            if (!this.MostraCheckAccettazione)
            {
                this.chkAccetto.Checked = true;
                this.chkAccetto.Visible = false;
            }
        }

        public override bool CanEnterStep()
        {
            return this._condizioneIngresso.Verificata();
        }

        public override bool CanExitStep()
        {
            try
            {
                this._condizioneUscita.MessaggioErrore = this.MessaggioErrore;

                return this._condizioneUscita.Verificata();
            }
            catch (StepException ex)
            {
                this.Errori.AddRange(ex.ErrorMessages);

                return false;
            }
        }

        public override void OnBeforeExitStep()
        {
            this.DatiDomandaService.SetFlagPrivacy(this.IdDomanda, this.chkAccetto.Checked);
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
