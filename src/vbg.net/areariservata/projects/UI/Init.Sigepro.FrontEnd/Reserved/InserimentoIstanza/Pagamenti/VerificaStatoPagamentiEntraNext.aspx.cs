using Init.Sigepro.FrontEnd.AppLogic.GestioneOneri;
using Init.Sigepro.FrontEnd.AppLogic.GestionePagamenti.EntraNext;
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
            //PagamentiEntraNextService.AnnullaPagamentiInSospeso(IdDomanda);

            //Master.cmdNextStep_Click(this, EventArgs.Empty);
        }
    }
}