using Init.Sigepro.FrontEnd.AppLogic.Configurazione.V2;
using Init.Sigepro.FrontEnd.AppLogic.GestioneBookmarks;
using Init.Sigepro.FrontEnd.AppLogic.PresentazioneIstanze.Workflow;
using Init.Sigepro.FrontEnd.AppLogic.Services.Domanda;
using Init.Sigepro.FrontEnd.Reserved.InserimentoIstanza.CondizioniUscitaSteps;
using Ninject;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;

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

        public string TestoDescrizioneSteps
        {
            get { object o = this.ViewState["TestoDescrizioneSteps"]; return o == null ? String.Empty : (string)o; }
            set { this.ViewState["TestoDescrizioneSteps"] = value; }
        }

        protected string BookmarkSelezionato
        {
            get 
            {
                var bookmark = ReadFacade.Domanda.Bookmarks.Bookmark;

                if (!String.IsNullOrEmpty(bookmark))
                {
                    return bookmark;
                }


                bookmark = Request.QueryString[Constants.QsParamSelezionaBookmark];

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

            WorkflowService.ResetWorkflowDaBookmark(BookmarksService.GetDatiBookmark(BookmarkSelezionato));

            this.Master.AssociaProprietaStepDaWorkflow();

            if (!IsPostBack)
                DataBind();
        }

        public override void OnBeforeExitStep()
        {
            BookmarksService.InizializzaIstanzaDaBookmark(IdDomanda, cmbComuni.SelectedValue, BookmarksService.GetDatiBookmark(BookmarkSelezionato));
        }

        public override void DataBind()
        {
            ltrTestoListaStep.Text = PreparaTestoPagina();

            var datiBookmark = BookmarksService.GetDatiBookmark(BookmarkSelezionato);
            var listaComuni = GetComuniAssociatiBindingSource();
            
            cmbComuni.DataSource = listaComuni;
            cmbComuni.DataBind();

            var idComuneAssociatoSelezionato = ReadFacade.Domanda.AltriDati.CodiceComune;

            if (!String.IsNullOrEmpty(idComuneAssociatoSelezionato))
                cmbComuni.SelectedValue = idComuneAssociatoSelezionato;

            pnlSelezioneComune.Visible = true;

            if (listaComuni.Count() == 2)	// La prima riga è qella che riporta il testo "Selezionare...", la seconda è la riga del comune corrente
            {
                pnlSelezioneComune.Visible = false;
                cmbComuni.SelectedIndex = 1;
            }
        }

        public string PreparaTestoPagina()
        {
            string modelloTesto = TestoDescrizioneSteps;

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

        public override bool CanExitStep()
        {
            if (!_condizioneDiUscita.Verificata())
            {
                this.Errori.Add(Constants.MsgErroreComuneNonSelezionato);
                return false;
            }

            return true;
        }


        public IEnumerable<KeyValuePair<string, string>> GetComuniAssociatiBindingSource()
        {
            var l = ReadFacade.Comuni.GetComuniAssociati();

            if (l.Count() == 0)
                throw new ApplicationException("La tabella comuniassociati non contiene righe");

            yield return new KeyValuePair<string, string>(String.Empty, "Selezionare...");

            foreach (var comune in l)
                yield return new KeyValuePair<string, string>(comune.CodiceComune, comune.Comune);
        }

    }
}