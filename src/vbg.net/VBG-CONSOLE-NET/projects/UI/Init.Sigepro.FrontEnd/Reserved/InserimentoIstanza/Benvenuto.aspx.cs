using Init.Sigepro.FrontEnd.AppLogic.AreaRiservataService;
using Init.Sigepro.FrontEnd.AppLogic.GestioneFiltroInterventiAlbero;
using Init.Sigepro.FrontEnd.AppLogic.GestioneInterventi;
using Init.Sigepro.FrontEnd.AppLogic.PresentazioneIstanze.Workflow;
using Init.Sigepro.FrontEnd.AppLogic.RedirectFineDomanda.CopiaDomanda;
using Init.Sigepro.FrontEnd.AppLogic.Repositories.AmbitoRicercaIntervento;
using Init.Sigepro.FrontEnd.AppLogic.SalvataggioDomanda;
using Init.Sigepro.FrontEnd.AppLogic.Services.Domanda;
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

            public const int ViewIdCopiaDa = 0;
            public const int ViewIdSelezionaComune = 1;
        }


        [Inject]
        public CondizioneIngressoStepSempreVera _condizioneIngresso { get; set; }

        [Inject]
        public ComuneDiPresentazioneSelezionato _condizioneUscita { get; set; }

        [Inject]
        public DatiDomandaService DatiDomandaService { get; set; }

        [Inject]
        public IWorkflowService WorkflowService { get; set; }

        [Inject]
        public ISalvataggioDomandaStrategy _salvataggioDomandaStrategy { get; set; }

        [Inject]
        protected ICopiaDatiDomandaService _copiaDomandaService { get; set; }
        [Inject]
        public IInterventiRepository _interventiRepository { get; set; }
        [Inject]
        public FiltroInterventiAlberoService _filtroAlberoService { get; set; }

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

        public string LabelSelezioneComune
        {
            get => this.lblComuniAssociati.Text;
            set => this.lblComuniAssociati.Text = value;
        }

        protected void Page_Load(object sender, EventArgs e)
        {
            // il service si occupa del salvataggio dei dati
            this.Master.IgnoraSalvataggioDati = true;

            if (!this.IsPostBack)
            {
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

        #region ciclo di vita della pagina
        public override bool CanEnterStep()
        {
            return this._condizioneIngresso.Verificata();
        }

        public override void OnBeforeExitStep()
        {

            var codiceComune = this.cmbComuni.SelectedValue;

            this.DatiDomandaService.SetCodiceComune(this.IdDomanda, codiceComune);

            if (this.CopiaDa.HasValue)
            {
                this.DatiDomandaService.ImpostaIdIstanzaOrigine(this.IdDomanda, this.CopiaDa.Value);
            }

            if (this.SelezionaIntervento.HasValue)
            {

                var livelloAccesso = this._interventiRepository.VerificaAccessoIntervento(this.SelezionaIntervento.Value, this.UserAuthenticationResult.LivelloAutenticazione, this.UserAuthenticationResult.DatiUtente.UtenteTester, codiceComune);

                if (!livelloAccesso.Is(TipoAccessibilitaIntervento.Accessibile))
                {
                    return;
                }

                var intervento = this._interventiRepository.GetDettagliIntervento(this.IdComune, this.SelezionaIntervento.Value, new AmbitoRicercaAreaRiservata(this.UserAuthenticationResult.DatiUtente?.UtenteTester ?? false));

                // Se l'intervento passato non esiste... oh no! Semplicemente non imposto niente e faccio selezionare l'intervento all'utente
                if (intervento == null)
                {
                    return;
                }

                var nodiFiglio = this._interventiRepository.GetSottonodi(this.IdComune, this.Software, this.SelezionaIntervento.Value, new AmbitoRicercaAreaRiservata(this.UserAuthenticationResult.DatiUtente?.UtenteTester ?? false), this.cmbComuni.SelectedValue);

                // Se l'intervento è una foglia (non ha nodi figlio) seleziono l'intervento passato
                if (!(nodiFiglio?.Any() ?? false))
                {
                    this.DatiDomandaService.ImpostaIdIntervento(this.IdDomanda, this.SelezionaIntervento.Value, null, true);
                    return;
                }

                // Se l'intervento è un ramo imposto la domanda in modo da bloccare tutta la parte di albero che precede
                // (Sarà compito del renderer dell'albero visualizzarlo nello step interventi)
                // TODO:
                this._filtroAlberoService.ImpostaFiltroInterventi(this.IdDomanda, this.SelezionaIntervento.Value);
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
            this.Master.MostraPaginatoreSteps = true;
            this.multiView.ActiveViewIndex = Constants.ViewIdSelezionaComune;

            this.ltrTestoListaStep.Text = this.PreparaTestoPagina();

            var listaComuni = this.GetComuniAssociatiBindingSource();

            this.cmbComuni.DataSource = listaComuni;
            this.cmbComuni.DataBind();

            var idComuneAssociatoSelezionato = this.ReadFacade.Domanda.AltriDati.CodiceComune;

            if (!String.IsNullOrEmpty(idComuneAssociatoSelezionato))
                this.cmbComuni.SelectedValue = idComuneAssociatoSelezionato;

            this.pnlSelezioneComune.Visible = true;

            if (listaComuni.Count() == 2)   // La prima riga è qella che riporta il testo "Selezionare...", la seconda è la riga del comune corrente
            {
                this.pnlSelezioneComune.Visible = false;
                this.cmbComuni.SelectedIndex = 1;
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
            var titoliSteps = this.WorkflowService.GetCurrentWorkflow().GetTitoliSteps();

            sb.Append("<ol>");

            foreach (var titoloStep in titoliSteps)
                sb.AppendFormat("<li>{0}</li>", titoloStep);

            sb.Append("</ol>");

            return String.Format(modelloTesto, sb.ToString());
        }

        public IEnumerable<KeyValuePair<string, string>> GetComuniAssociatiBindingSource()
        {
            var l = this.ReadFacade.Comuni.GetComuniAssociati();

            if (l.Count() == 0)
                throw new ApplicationException("La tabella comuniassociati non contiene righe");

            yield return new KeyValuePair<string, string>(String.Empty, String.Empty);

            foreach (var comune in l)
                yield return new KeyValuePair<string, string>(comune.CodiceComune, comune.Comune);
        }

        protected void confermaCopia_Click(object sender, EventArgs e)
        {
            try
            {
                var erroriValidazione = this.formCopiaDati.GetErroriDiValidazione();

                if (erroriValidazione.Count() > 0)
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
