using Init.Sigepro.FrontEnd.AppLogic.Configurazione.V2;
using Init.Sigepro.FrontEnd.AppLogic.GestioneBookmarks;
using Init.Sigepro.FrontEnd.AppLogic.GestioneComuni;
using Init.Sigepro.FrontEnd.AppLogic.PresentazioneIstanze.Workflow;
using Init.Sigepro.FrontEnd.AppLogic.Services.Domanda;
using Init.Sigepro.FrontEnd.Reserved.InserimentoIstanza.CondizioniUscitaSteps;
using Ninject;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.Sigepro.FrontEnd.Reserved.InserimentoIstanza
{
    public partial class BenvenutoBookmark : IstanzeStepPage
    {
        private static class Constants
        {
            public const string MsgErroreComuneNonSelezionato = "Per poter proseguire è necessario selezionare il comune per cui si vuole presentare l'istanza";
            public const string QsParamSelezionaBookmark = "bookmark";
        }

        [Inject]
        public DatiDomandaService DatiDomandaService { get; set; }

        [Inject]
        public BookmarksService BookmarksService { get; set; }

        [Inject]
        public IConfigurazione<ParametriWorkflow> _configurazione { get; set; }

        [Inject]
        public IWorkflowService WorkflowService { get; set; }

        [Inject]
        public ComuneDiPresentazioneSelezionato _condizioneDiUscita { get; set; }
        [Inject]
        public IComuniAssociatiService _comuniService { get; set; }

        public string TestoDescrizioneSteps
        {
            get { object o = this.ViewState["TestoDescrizioneSteps"]; return o == null ? String.Empty : (string)o; }
            set { this.ViewState["TestoDescrizioneSteps"] = value; }
        }

        protected string BookmarkSelezionato
        {
            get
            {
                var bookmark = this.ReadFacade.Domanda.Bookmarks.Bookmark;

                if (!String.IsNullOrEmpty(bookmark))
                {
                    return bookmark;
                }


                bookmark = this.Request.QueryString[Constants.QsParamSelezionaBookmark];

                if (String.IsNullOrEmpty(bookmark))
                {
                    throw new InvalidOperationException("Questa pagina è accessibile solamente tramite un bookmark");
                }

                return bookmark;
            }
        }


        protected void Page_Load(object sender, EventArgs e)
        {
            // il service si occupa del salvataggio dei dati
            this.Master.IgnoraSalvataggioDati = true;

            this.WorkflowService.ClearCacheDomanda(this.IdDomanda);

            this.Master.AssociaProprietaStepDaWorkflow();

            if (!this.IsPostBack)
                this.DataBind();
        }

        public override void OnBeforeExitStep()
        {
            this.BookmarksService.InizializzaIstanzaDaBookmark(this.IdDomanda, this.cmbComuni.SelectedValue, this.BookmarksService.GetDatiBookmark(this.BookmarkSelezionato));
        }

        public override void DataBind()
        {
            this.ltrTestoListaStep.Text = this.PreparaTestoPagina();

            var datiBookmark = this.BookmarksService.GetDatiBookmark(this.BookmarkSelezionato);
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

        public override bool CanExitStep()
        {
            if (!this._condizioneDiUscita.Verificata())
            {
                this.Errori.Add(Constants.MsgErroreComuneNonSelezionato);
                return false;
            }

            return true;
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