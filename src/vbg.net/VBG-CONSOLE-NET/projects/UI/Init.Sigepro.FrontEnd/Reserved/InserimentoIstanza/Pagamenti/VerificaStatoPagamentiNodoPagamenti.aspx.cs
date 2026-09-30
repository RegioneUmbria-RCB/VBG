using Init.Sigepro.FrontEnd.AppLogic.GestionePagamenti.NODOPAGAMENTI;
using Init.Sigepro.FrontEnd.AppLogic.GestionePagamenti.NODOPAGAMENTI.Configurazione;
using Init.Sigepro.FrontEnd.AppLogic.PresentazioneIstanze.Workflow;
using Init.Sigepro.FrontEnd.Infrastructure.UrlsAndPaths;
using Init.Sigepro.FrontEnd.QsParameters;
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
        protected WorkflowFromConfigurazioneService _workflowService { get; set; }

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
            get { return cmdProcedi.Text; }
            set { cmdProcedi.Text = value; }
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

            if (!IsPostBack)
                DataBind();

        }


        public override bool CanEnterStep()
        {
            if (!this._nodoPagamentiService.NodoPagamentoAttivo(IdDomanda))
            {
                return false;
            }

            var configurazioneValida = this._verificaConfigurazioneNodoPagamentiService.ConfigurazioneValida(IdDomanda);

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

            if (PermetteForzaturaAnnullamentoPagamentiAttivati && pagamentiInSospeso.EsistonoPagamentiAttivatiNonCompletati)
            {
                this.cmdProcedi.Visible = true;
            }
        }


        protected void AggiornaStatoPagamenti(object sender, EventArgs e)
        {
            var url = UrlBuilder.Url(Constants.UrlPagamentoNodoPagamenti, x =>
            {
                x.Add(new QsAliasComune(this._aliasSoftwareResolver.AliasComune));
                x.Add(new QsSoftware(this._aliasSoftwareResolver.Software));
                x.Add(new QsIdDomandaOnline(IdDomanda));
                x.Add(new QsStepId(this._workflowService.GetCurrentWorkflow().TrovaIndiceStepDaUrlParziale(Constants.UrlPagamentoNodoPagamenti)));
            });

            Response.Redirect(url);
        }

        protected void bmConfermaAnnullamentoPagamento_OkClicked(object sender, EventArgs e)
        {
            this._nodoPagamentiService.AnnullaPagamenti(IdDomanda, FlagEliminazionePagamenti.PagamentiFallitiOInAttesaDiRisposta);

            Master.cmdNextStep_Click(this, EventArgs.Empty);
        }
    }
}