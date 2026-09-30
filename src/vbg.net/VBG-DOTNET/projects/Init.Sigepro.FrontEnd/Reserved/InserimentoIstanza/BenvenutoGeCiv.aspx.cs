using Init.Sigepro.FrontEnd.AppLogic.GestioneComuni;
using Init.Sigepro.FrontEnd.AppLogic.PresentazioneIstanze.Workflow;
using Init.Sigepro.FrontEnd.AppLogic.Services.Domanda;
using Init.Sigepro.FrontEnd.Reserved.InserimentoIstanza.CondizioniIngressoSteps;
using Init.Sigepro.FrontEnd.Reserved.InserimentoIstanza.CondizioniUscitaSteps;
using Ninject;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.Sigepro.FrontEnd.Reserved.InserimentoIstanza
{
    public partial class BenvenutoGeCiv : IstanzeStepPage
    {
        private static class Constants
        {
            public const string MsgErroreComuneNonSelezionato = "Per poter proseguire è necessario selezionare il comune per cui si vuole presentare l'istanza";
            public const string QsParamSelezionaIntervento = "SelezionaIntervento";
            public const string UrlBenvenutoStar = "~/reserved/inserimentoIstanza/BenvenutoSTAR.aspx";
        }


        [Inject]
        public CondizioneIngressoStepSempreVera _condizioneIngresso { get; set; }
        [Inject]
        public ComuneDiPresentazioneSelezionato _condizioneUscita { get; set; }
        // [Inject]
        // public DatiDomandaService DatiDomandaService { get; set; }
        [Inject]
        public DatiDomandaService DatiDomandaService { get; set; }
        [Inject]
        public IWorkflowService WorkflowService { get; set; }
        [Inject]
        public IComuniAssociatiService _comuniService { get; set; }

        protected bool NoStar
        {
            get
            {
                return this.Request.QueryString["star"] == "0";
            }
        }


        protected int? SelezionaIntervento
        {
            get
            {
                try
                {
                    var qs = this.Request.QueryString[Constants.QsParamSelezionaIntervento];

                    if (string.IsNullOrEmpty(qs))
                        return null;

                    return Convert.ToInt32(qs);
                }
                catch (FormatException)
                {
                    return null;
                }
            }
        }

        public string TestoDescrizioneSteps
        {
            get { object o = this.ViewState["TestoDescrizioneSteps"]; return o == null ? String.Empty : (string)o; }
            set { this.ViewState["TestoDescrizioneSteps"] = value; }
        }

        protected void Page_Load(object sender, EventArgs e)
        {
            // il service si occupa del salvataggio dei dati
            this.Master.IgnoraSalvataggioDati = true;

            if (!this.IsPostBack)
            {
                this.DataBind();
            }
        }

        #region ciclo di vita della pagina
        public override bool CanEnterStep()
        {
            return this._condizioneIngresso.Verificata();
        }

        public override void OnBeforeExitStep()
        {
            this.DatiDomandaService.SetCodiceComune(this.IdDomanda, this.cmbComuni.SelectedValue);

            if (this.SelezionaIntervento.HasValue)
            {
                this.DatiDomandaService.ImpostaIdIntervento(this.IdDomanda, this.SelezionaIntervento.Value, null, true);
            }
        }

        public override bool CanExitStep()
        {
            if (!this._condizioneUscita.Verificata())
            {
                this.Errori.Add(Constants.MsgErroreComuneNonSelezionato);
                return false;
            }

            return true;
        }
        #endregion

        public override void DataBind()
        {
            if (!this.UserAuthenticationResult.DatiUtente.UtenteTester)
            {
                this.MostraErroreAccesso();
                return;
            }

            this.ltrTestoListaStep.Text = this.PreparaTestoPagina();

            var listaComuni = this.GetComuniAssociatiBindingSource();

            this.cmbComuni.DataSource = listaComuni;
            this.cmbComuni.DataBind();

            var idComuneAssociatoSelezionato = this.ReadFacade.Domanda.AltriDati.CodiceComune;

            if (!String.IsNullOrEmpty(idComuneAssociatoSelezionato))
                this.cmbComuni.SelectedValue = idComuneAssociatoSelezionato;

            this.pnlSelezioneComune.Visible = true;

            if (listaComuni.Count() == 2)	// La prima riga è qella che riporta il testo "Selezionare...", la seconda è la riga del comune corrente
            {
                this.pnlSelezioneComune.Visible = false;
                this.cmbComuni.SelectedIndex = 1;
            }
        }

        private void MostraErroreAccesso()
        {
            this.multiView.ActiveViewIndex = 1;

            this.Master.MostraPaginatoreSteps = false;
        }


        public string PreparaTestoPagina()
        {
            string modelloTesto = this.TestoDescrizioneSteps;

            if (modelloTesto.IndexOf("{0}") == -1)
                return modelloTesto;

            var sb = new StringBuilder();
            var titoliSteps = this.WorkflowService.GetWorkflowByIdDomanda(this.IdDomanda).GetTitoliSteps();

            sb.Append("<ol>");

            foreach (var titoloStep in titoliSteps)
                sb.AppendFormat("<li>{0}</li>", titoloStep);

            sb.Append("</ol>");

            return String.Format(modelloTesto, sb.ToString());
        }

        public IEnumerable<KeyValuePair<string, string>> GetComuniAssociatiBindingSource()
        {
            var l = this._comuniService.GetComuniAssociati();

            if (l.Count() == 0)
                throw new ApplicationException("La tabella comuniassociati non contiene righe");

            yield return new KeyValuePair<string, string>(String.Empty, "Selezionare...");

            foreach (var comune in l)
                yield return new KeyValuePair<string, string>(comune.CodiceComune, comune.Comune);
        }
    }
}