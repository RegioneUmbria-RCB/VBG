using Init.Sigepro.FrontEnd.AppLogic.GestioneOneri;
using Init.Sigepro.FrontEnd.AppLogic.GestionePagamenti.MIP;
using Ninject;
using System;
using System.Collections.Generic;
using System.Configuration;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;

namespace Init.Sigepro.FrontEnd.Reserved.InserimentoIstanza.Pagamenti
{
    public partial class VerificaStatoPagamenti : IstanzeStepPage
    {
        [Inject]
        protected PagamentiMIPService PagamentiMIPService { get; set; }

        [Inject]
        protected OneriDomandaService OneriDomandaService { get; set; }

        public string MessaggioErrore
        {
            get { object o = this.ViewState["MessaggioErrore"]; return o == null ? "[INSERIRE NEL WORKFLOW UN MESSAGGIO DI ERRORE]" : (string)o; }
            set { this.ViewState["MessaggioErrore"] = value; }
        }

        public string ControlProperty
        {
            get { return cmdProcedi.Text; }
            set { cmdProcedi.Text = value; }
        }
        

      

        protected void Page_Load(object sender, EventArgs e)
        {
            this.Master.MostraBottoneAvanti = false;

            if(!IsPostBack)
            {
                DataBind();
            }
        }

        public override void OnInitializeStep()
        {
            var tipoPagamentoDefault = this.PagamentiMIPService.GetTipoPagamentoDefault();

            if (tipoPagamentoDefault == null)
            {
                throw new ConfigurationException("Non è stato configurato un tipo pagamento. Verificare la configurazione del modulo PAGAMENTI_MIP_RPCSUAP e riprovare");
            }
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
            PagamentiMIPService.AggiornaStatoPagamentiInSospeso(IdDomanda);

            var pagamentiInSospeso = PagamentiMIPService.GetPagamentiInSospeso(IdDomanda);

            rptOperazioni.DataSource = pagamentiInSospeso;
            rptOperazioni.DataBind();

            return pagamentiInSospeso.Count() != 0;
        }

        protected void AnnullaPagamentiInSospeso(object sender, EventArgs e)
        {
            PagamentiMIPService.AnnullaPagamentiInSospeso(IdDomanda);

            Master.cmdNextStep_Click(this, EventArgs.Empty);
        }
    }
}