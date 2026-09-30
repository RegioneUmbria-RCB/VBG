using Init.Sigepro.FrontEnd.AppLogic.GestioneOneri;
using Init.Sigepro.FrontEnd.AppLogic.GestionePagamenti.EntraNext;
using Ninject;
using System;
using System.Linq;

namespace Init.Sigepro.FrontEnd.Reserved.InserimentoIstanza.Pagamenti
{
    public partial class VerificaStatoPagamentiEntraNext : IstanzeStepPage
    {
        [Inject]
        protected PagamentiEntraNextService PagamentiEntraNextService { get; set; }

        [Inject]
        protected OneriDomandaService OneriDomandaService { get; set; }

        public string MessaggioErrore
        {
            get { object o = this.ViewState["MessaggioErrore"]; return o == null ? "[INSERIRE NEL WORKFLOW UN MESSAGGIO DI ERRORE]" : (string)o; }
            set { this.ViewState["MessaggioErrore"] = value; }
        }

        //public string ControlProperty
        //{
        //    get { return cmdProcedi.Text; }
        //    set { cmdProcedi.Text = value; }
        //}

        public bool PermettiAnnullamento
        {
            get => this.cmdProcedi.Visible;
            set => this.cmdProcedi.Visible = value;
        }




        protected void Page_Load(object sender, EventArgs e)
        {
            this.Master.MostraBottoneAvanti = false;

            if (!IsPostBack)
            {
                DataBind();
            }
        }

        public override void OnInitializeStep()
        {

        }

        public override void DataBind()
        {
            EsistonoPagamentiInSospeso();
        }

        public override bool CanEnterStep()
        {
            return EsistonoPagamentiInSospeso();
        }

        private bool EsistonoPagamentiInSospeso()
        {
            PagamentiEntraNextService.AggiornaStatoPagamentiInSospeso(IdDomanda);

            var pagamentiInSospeso = PagamentiEntraNextService.GetPagamentiInSospeso(IdDomanda);

            rptOperazioni.DataSource = pagamentiInSospeso;
            rptOperazioni.DataBind();

            return pagamentiInSospeso != null && pagamentiInSospeso.Count() != 0;
        }

        protected void AnnullaPagamentiInSospeso(object sender, EventArgs e)
        {
            PagamentiEntraNextService.AnnullaPagamento(IdDomanda);

            Master.cmdNextStep_Click(this, EventArgs.Empty);
        }
    }
}