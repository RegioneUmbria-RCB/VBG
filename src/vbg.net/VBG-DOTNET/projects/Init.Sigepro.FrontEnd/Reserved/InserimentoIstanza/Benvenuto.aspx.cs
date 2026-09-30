using Init.Sigepro.FrontEnd.AppLogic.GestioneComuni;
using Init.Sigepro.FrontEnd.AppLogic.GestioneInterventi;
using Init.Sigepro.FrontEnd.AppLogic.GestioneSTAR;
using Init.Sigepro.FrontEnd.AppLogic.PresentazioneIstanze.Workflow;
using Init.Sigepro.FrontEnd.AppLogic.RedirectFineDomanda.CopiaDomanda;
using Init.Sigepro.FrontEnd.AppLogic.SalvataggioDomanda;
using Init.Sigepro.FrontEnd.AppLogic.Services.Domanda;
using Init.Sigepro.FrontEnd.AppLogic.WsInterventi;
using Init.Sigepro.FrontEnd.QsParameters;
using Init.Sigepro.FrontEnd.Reserved.InserimentoIstanza.CondizioniIngressoSteps;
using Init.Sigepro.FrontEnd.Reserved.InserimentoIstanza.CondizioniUscitaSteps;
using Ninject;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.Sigepro.FrontEnd.Reserved.InserimentoIstanza
{
    public partial class Benvenuto : IstanzeStepPage
    {
        private static class Constants
        {
            public const string MsgErroreComuneNonSelezionato = "Per poter proseguire è necessario selezionare il comune per cui si vuole presentare l'istanza";
            public const string QsParamSelezionaIntervento = "SelezionaIntervento";
            public const string UrlBenvenutoStar = "~/reserved/inserimentoIstanza/BenvenutoSTAR.aspx";

            public const int ViewIdCopiaDa = 0;
            public const int ViewIdSelezionaComune = 1;
        }


        [Inject]
        public CondizioneIngressoStepSempreVera _condizioneIngresso { get; set; }
        [Inject]
        public ComuneDiPresentazioneSelezionato _condizioneUscita { get; set; }
        [Inject]
        public DatiDomandaService _datiDomandaService { get; set; }
        [Inject]
        public IWorkflowService _workflowService { get; set; }
        [Inject]
        public IInterventiRepository _interventiService { get; set; }
        [Inject]
        public STARUrlService _starUrlService { get; set; }
        [Inject]
        public ISalvataggioDomandaStrategy _salvataggioDomandaStrategy { get; set; }
        [Inject]
        protected ICopiaDatiDomandaService _copiaDomandaService { get; set; }
        [Inject]
        protected IComuniAssociatiService _comuniService { get; set; }

        protected QsCopiaDa CopiaDa
        {
            get { return new QsCopiaDa(this.Request.QueryString); }
        }

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
            get { var o = this.ViewState["TestoDescrizioneSteps"]; return o == null ? String.Empty : (string)o; }
            set { this.ViewState["TestoDescrizioneSteps"] = value; }
        }

        public string SelezionaComune
        {
            get { var o = this.ViewState["SelezionaComune"]; return o == null ? String.Empty : (string)o; }
            set { this.ViewState["SelezionaComune"] = value; }
        }

        public string TestoSelezionaComune
        {
            get => this.ViewstateGet("TestoSelezionaComune", "Seleziona il comune per cui vuoi presentare l'istanza");
            set => this.ViewState["TestoSelezionaComune"] = value;
        }

        public string EscludiComuni
        {
            get
            {
                var o = this.ViewState["EscludiComuni"];
                if (o == null || String.IsNullOrEmpty(o.ToString()))
                {
                    return null;
                }

                return o.ToString().Replace(" ", "").ToUpper();
            }
            set { this.ViewState["EscludiComuni"] = value; }
        }


        protected void Page_Load(object sender, EventArgs e)
        {
            // il service si occupa del salvataggio dei dati
            this.Master.IgnoraSalvataggioDati = true;

            if (!this.IsPostBack)
            {
                this.VerificaRedirectStar();

                if (this.MostraCopiaDa())
                {
                    this.DatabindCopiaDa();
                }
                else
                {
                    this.DataBind();
                }

            }
        }

        private void VerificaRedirectStar()
        {
            if (!this.NoStar && this._starUrlService.StarAttivo())
            {
                var qs = this.Request.QueryString.ToString();
                this.Response.Redirect(String.Format("{0}?{1}", Constants.UrlBenvenutoStar, qs));
                this.Response.End();
            }
        }

        #region ciclo di vita della pagina
        public override bool CanEnterStep()
        {
            return this._condizioneIngresso.Verificata();
        }

        public override void OnBeforeExitStep()
        {
            var codiceComune = this.cmbComuni.SelectedValue;
            this._datiDomandaService.SetCodiceComune(this.IdDomanda, codiceComune);

            if (this.SelezionaIntervento.HasValue)
            {
                // Se l'intervento preselezionato non è attivo per il comune selezionato, allora non lo imposto
                var esitoVerifica = this._interventiService.VerificaAccessoIntervento(this.SelezionaIntervento.Value, codiceComune);

                if (esitoVerifica.Is(TipoAccessibilitaIntervento.Accessibile))
                {
                    this._datiDomandaService.ImpostaIdIntervento(this.IdDomanda, this.SelezionaIntervento.Value, null, true);
                }
            }

            if (this.CopiaDa.HasValue)
            {
                this._datiDomandaService.ImpostaIdDomandaCollegata(this.IdDomanda, this.CopiaDa.Value);
            }

        }

        public override bool CanExitStep()
        {
            if (this.SelezionaIntervento.HasValue)
            {
                var codiceComune = this.cmbComuni.SelectedValue;
                var esitoVerifica = this._interventiService.VerificaAccessoIntervento(this.SelezionaIntervento.Value, codiceComune);

                if (!esitoVerifica.Is(TipoAccessibilitaIntervento.Accessibile))
                {
                    this.Errori.Add(esitoVerifica.MessaggioErrore);

                    return false;
                }
            }

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
            this.Master.MostraPaginatoreSteps = true;
            this.multiView.ActiveViewIndex = Constants.ViewIdSelezionaComune;

            this.ltrTestoListaStep.Text = this.PreparaTestoPagina();


            var listaComuni = this._comuniService.GetComuniAssociati(this.EscludiComuni?.Split(','))
                                                .Select(x => new KeyValuePair<string, string>(x.CodiceComune, x.Comune))
                                                .OrderBy(x => x.Value)
                                                .ToList();
            listaComuni.Insert(0, new KeyValuePair<string, string>(string.Empty, string.Empty));

            this.cmbComuni.DataSource = listaComuni;
            this.cmbComuni.DataBind();

            var idComuneAssociatoSelezionato = this.ReadFacade.Domanda.AltriDati.CodiceComune;

            if (!String.IsNullOrEmpty(idComuneAssociatoSelezionato))
                this.cmbComuni.SelectedValue = idComuneAssociatoSelezionato;

            this.pnlSelezioneComune.Visible = true;

            if (listaComuni.Count == 2)   // La prima riga è qella che riporta il testo "Selezionare...", la seconda è la riga del comune corrente
            {
                this.pnlSelezioneComune.Visible = false;
                this.cmbComuni.SelectedIndex = 1;
            }

            if (!String.IsNullOrEmpty(this.SelezionaComune))
            {
                this.pnlSelezioneComune.Visible = false;

                if (String.IsNullOrEmpty(idComuneAssociatoSelezionato))
                {
                    this.cmbComuni.SelectedValue = this.SelezionaComune;
                }
            }
        }

        private bool MostraCopiaDa()
        {
            return this.CopiaDa.HasValue;
        }

        private void DatabindCopiaDa()
        {
            this.Master.MostraPaginatoreSteps = false;
            this.multiView.ActiveViewIndex = Constants.ViewIdCopiaDa;
            var domandaOrigine = this._salvataggioDomandaStrategy.GetById(this.CopiaDa.Value);

            this.formCopiaDati.Domanda = domandaOrigine.ReadInterface;
            this.formCopiaDati.DataBind();
        }


        public string PreparaTestoPagina()
        {
            var modelloTesto = this.TestoDescrizioneSteps;

            if (modelloTesto.IndexOf("{0}") == -1)
                return modelloTesto;

            var sb = new StringBuilder();
            var titoliSteps = this._workflowService.GetWorkflowByIdDomanda(this.IdDomanda).GetTitoliSteps();

            sb.Append("<ol>");

            foreach (var titoloStep in titoliSteps)
                sb.AppendFormat("<li>{0}</li>", titoloStep);

            sb.Append("</ol>");

            return String.Format(modelloTesto, sb.ToString());
        }

        protected void confermaCopia_Click(object sender, EventArgs e)
        {
            try
            {
                var erroriValidazione = this.formCopiaDati.GetErroriDiValidazione();

                if (erroriValidazione.Any())
                {
                    foreach (var errore in erroriValidazione)
                    {
                        this.Errori.Add(errore);
                    }

                    return;
                }
                // Copia dati su nuova istanza...
                var elementiDaCopiare = this.formCopiaDati.GetElementiSelezionati();

                this._copiaDomandaService.CopiaDatiDomanda(this.CopiaDa.Value, this.IdDomanda, elementiDaCopiare);

                this.DataBind();
            }
            catch (Exception ex)
            {
                this.Errori.Add(ex.Message);
            }

        }
    }
}
