using Init.Sigepro.FrontEnd.AppLogic.Pagamenti.NodoPagamenti;
using Init.Sigepro.FrontEnd.AppLogic.Pagamenti.NodoPagamenti.Configurazione;
using Init.Sigepro.FrontEnd.AppLogic.PresentazioneIstanze.Workflow;
using Init.Sigepro.FrontEnd.AppLogic.PresentazioneIstanze.Workflow.V2;
using Init.Sigepro.FrontEnd.Infrastructure.UrlsAndPaths;
using Init.Sigepro.FrontEnd.QsParameters;
using log4net;
using Ninject;
using System;

namespace Init.Sigepro.FrontEnd.Reserved.InserimentoIstanza.Pagamenti
{
    public partial class VerificaStatoPagamentiNodoPagamenti : IstanzeStepPage
    {
        [Inject]
        protected IPagamentiNodoPagamentiService _nodoPagamentiService { get; set; }

        [Inject]
        protected IVerificaConfigurazioneNodoPagamentiService _verificaConfigurazioneNodoPagamentiService { get; set; }

        [Inject]
        protected IWorkflowService _workflowService { get; set; }

        private readonly ILog _log = LogManager.GetLogger(nameof(VerificaStatoPagamentiNodoPagamenti));

        public static class Constants
        {
            public const string UrlPagamentoNodoPagamenti = "PagamentoNodoPagamenti.aspx";
        }

        public string MessaggioErrore
        {
            get { object o = this.ViewState["MessaggioErrore"]; return o == null ? "[INSERIRE NEL WORKFLOW UN MESSAGGIO DI ERRORE]" : (string)o; }
            set { this.ViewState["MessaggioErrore"] = value; }
        }

        public string TestoBottoneProcedi
        {
            get { return this.cmdProcedi.Text; }
            set { this.cmdProcedi.Text = value; }
        }

        public bool PermetteForzaturaAnnullamentoPagamentiAttivati
        {
            get
            {
                object o = this.ViewState["PermetteForzaturaAnnullamentoPagamentiAttivati"];
                return o == null ? false : Convert.ToBoolean(o);
            }
            set => this.ViewState["PermetteForzaturaAnnullamentoPagamentiAttivati"] = value;
        }


        protected void Page_Load(object sender, EventArgs e)
        {
            this.Master.MostraBottoneAvanti = false;

            if (!this.IsPostBack)
                this.DataBind();

        }


        public override bool CanEnterStep()
        {
            if (!this._nodoPagamentiService.NodoPagamentoAttivo(this.IdDomanda))
            {
                return false;
            }

            var configurazioneValida = this._verificaConfigurazioneNodoPagamentiService.ConfigurazioneValida(this.IdDomanda);

            if (!configurazioneValida)
                throw new Exception($"L'integrazione con il sistema dei pagamenti non è correttamente configurata. Controllare i parametri della verticalizzazione NODO_PAGAMENTI");

            this._nodoPagamentiService.AggiornaStatoPagamenti(this.IdDomanda);
            this._nodoPagamentiService.AnnullaPagamenti(this.IdDomanda, FlagEliminazionePagamenti.PagamentiFalliti);

            var pagamenti = this._nodoPagamentiService.GetStatoPagamentiInSospeso(this.IdDomanda);

            return pagamenti.EsistonoPagamentiInSospeso;
        }

        public override void DataBind()
        {
            var pagamentiInSospeso = this._nodoPagamentiService.GetStatoPagamentiInSospeso(this.IdDomanda);

            this.statoPosizioniCtrl.IdDomanda = this.IdDomanda;
            this.statoPosizioniCtrl.DataBind();

            this.cmdVerificaPagamento.Visible = pagamentiInSospeso.EsistonoPagamentiInSospeso;
            this.cmdProcedi.Visible = !pagamentiInSospeso.EsistonoPagamentiAttivatiNonCompletati;

            if (this.PermetteForzaturaAnnullamentoPagamentiAttivati && pagamentiInSospeso.EsistonoPagamentiAttivatiNonCompletati)
            {
                this.cmdProcedi.Visible = true;
            }
        }


        protected void AggiornaStatoPagamenti(object sender, EventArgs e)
        {
            var wf = this._workflowService.GetWorkflowByIdDomanda(this.IdDomanda);
            var idx = wf.GetIndiceStepByIdentifier(WorkflowSteps.Pagamento);

            var url = UrlBuilder.Url(Constants.UrlPagamentoNodoPagamenti, x =>
            {
                x.Add(new QsAliasComune(this._aliasSoftwareResolver.AliasComune));
                x.Add(new QsSoftware(this._aliasSoftwareResolver.Software));
                x.Add(new QsIdDomandaOnline(this.IdDomanda));
                x.Add(new QsStepId(idx));
            });

            this.Response.Redirect(url);
        }

        protected void bmConfermaAnnullamentoPagamento_OkClicked(object sender, EventArgs e)
        {
            this._log.ErrorFormat($"L'utente {this.UserAuthenticationResult.DatiUtente.Codicefiscale} ha richiesto l'annullamento del pagamento per la domanda {this.IdDomanda} dalla pagina di verifica stato pagamento");

            this._nodoPagamentiService.AnnullaPagamenti(this.IdDomanda, FlagEliminazionePagamenti.PagamentiFallitiOInAttesaDiRisposta);

            this.Master.cmdNextStep_Click(this, EventArgs.Empty);
        }
    }
}