using Init.Sigepro.FrontEnd.AppLogic.GestionePagamenti.NODOPAGAMENTI;
using Init.Sigepro.FrontEnd.Infrastructure.UrlsAndPaths;
using Ninject;
using Ninject.Web;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;

namespace Init.Sigepro.FrontEnd.Reserved.InserimentoIstanza.Pagamenti
{
    public partial class stato_posizioni_ctrl : UserControlBase
    {
        [Inject]
        protected IPagamentiNodoPagamentiService _nodoPagamentiService { get; set; }

        public string UrlBaseAvvisoPagamento
        {
            get => this.ViewState["UrlBaseAvvisoPagamento"]?.ToString();
            set => this.ViewState["UrlBaseAvvisoPagamento"] = value;
        }

        public int IdDomanda
        {
            get => Convert.ToInt32(this.ViewState["IdDomanda"] ?? -1);
            set => this.ViewState["IdDomanda"] = value;
        }

        protected void Page_Load(object sender, EventArgs e)
        {

        }

        public override void DataBind()
        {
            var datiPosizioni = this._nodoPagamentiService.GetDatiPosizioniDebitorie(IdDomanda);

            rptDettagliPosizioni.DataSource = datiPosizioni;
            rptDettagliPosizioni.DataBind();

            if (String.IsNullOrEmpty(UrlBaseAvvisoPagamento))
            {
                this.UrlBaseAvvisoPagamento = ResolveClientUrl(UrlBuilder.Url(
                                                "~/reserved/inserimentoistanza/pagamenti/pago-dopo/download-avviso-pagamento.ashx",
                                                qs => qs.AddAlias()
                                                        .AddSoftware()
                                                        .AddIdPresentazione()));
            }
        }
    }
}