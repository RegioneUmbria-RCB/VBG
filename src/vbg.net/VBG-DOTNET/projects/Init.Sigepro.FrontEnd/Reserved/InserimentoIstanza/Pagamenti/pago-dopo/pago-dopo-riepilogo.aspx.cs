using Init.Sigepro.FrontEnd.AppLogic.Pagamenti.NodoPagamenti;
using Init.Sigepro.FrontEnd.Infrastructure.UrlsAndPaths;
using Ninject;
using System;

namespace Init.Sigepro.FrontEnd.Reserved.InserimentoIstanza.Pagamenti.pago_dopo
{
    public partial class pago_dopo_riepilogo : IstanzeStepPage
    {
        [Inject]
        protected IPagamentiNodoPagamentiService _nodoPagamentiService { get; set; }


        protected void Page_Load(object sender, EventArgs e)
        {
            this.Master.ForzaTitoloStep = "Riepilogo pagamenti";
            this.Master.MostraDescrizioneStep = false;
            this.Master.MostraPaginatoreSteps = false;

            if (!this.IsPostBack)
            {
                this.DataBind();
            }
        }

        public override void DataBind()
        {
            this._nodoPagamentiService.AggiornaStatoPagamenti(this.IdDomanda);

            this.statoPosizioniControl.IdDomanda = this.IdDomanda;
            this.statoPosizioniControl.DataBind();
        }

        protected void cmdTornaAllaHome_Click(object sender, EventArgs e)
        {
            var url = UrlBuilder.Url("~/reserved/default.aspx", qs => qs.AddAlias().AddSoftware());
            this.Response.Redirect(url);
        }
    }
}