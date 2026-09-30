using Init.Sigepro.FrontEnd.AppLogic.Pagamenti.NodoPagamenti;
using Init.Sigepro.FrontEnd.AppLogic.Pagamenti.NodoPagamenti.Anagrafiche;
using Init.Sigepro.FrontEnd.Infrastructure.UrlsAndPaths;
using Init.Sigepro.FrontEnd.QsParameters.Pagamenti;
using Ninject;
using System;

namespace Init.Sigepro.FrontEnd.Reserved.InserimentoIstanza.Pagamenti.pago_dopo
{
    public partial class scelta_pago_dopo : IstanzeStepPage
    {

        [Inject]
        protected IPagamentiNodoPagamentiService _nodoPagamentiService { get; set; }
        [Inject]
        protected IEstremiDomandaNodoPagamentiReader _estremiDomandaNodoPagamentiReader { get; set; }

        public bool SupportaPagaDopo { get; set; } = false;
        public bool SupportaPagamentoOtf { get; set; } = false;

        protected void Page_Load(object sender, EventArgs e)
        {
            this.SupportaPagamentoOtf = this._nodoPagamentiService.PagamentoOnlineAttivo(this.IdDomanda);
            this.SupportaPagaDopo = this._nodoPagamentiService.PagoDopoAttivo(this.IdDomanda);

            if (!this.IsPostBack)
            {
                this.DataBind();
            }

            this.Master.MostraBottoneAvanti = false;
            this.Master.ForzaTitoloStep = "Come vuoi pagare";
            this.Master.MostraDescrizioneStep = false;
        }

        public override void DataBind()
        {
            base.DataBind();
        }

        protected void lnkPagaDopo_Click(object sender, EventArgs e)
        {

        }

        protected void lnkPagaOnline_Click(object sender, EventArgs e)
        {
            var url = UrlBuilder.Url("~/reserved/inserimentoistanza/pagamenti/pagamentonodopagamenti.aspx", qs =>
            {
                qs.AddAlias().AddSoftware().AddIdPresentazione().AddStepId().Add(QsPagamentoOnTheFly.UsaPagamentoOTF);
            });

            this.Response.Redirect(url);
        }

        protected void bmConfermaCreazionePosizioneDebitoria_OkClicked(object sender, EventArgs e)
        {
            try
            {
                var estremiDomanda = this._estremiDomandaNodoPagamentiReader.GetEstremiDomandaDaIdDomanda(this.IdDomanda, this.Master.LastStep);
                var esito = this._nodoPagamentiService.AvviaPagamentoDifferito(estremiDomanda);

                if (!esito)
                {
                    this.Errori.Add("Si è verificato un errore durante l'apertura delle posizioni debitorie, si prega di riprovare in un secondo momento. Se il problema dovesse persistere si prega di contattare l'assistenza");

                    return;
                }

                var url = UrlBuilder.Url("~/reserved/inserimentoistanza/pagamenti/pago-dopo/pago-dopo-riepilogo.aspx", qs =>
                {
                    qs.AddAlias().AddSoftware().AddIdPresentazione().AddStepId();
                });

                this.Response.Redirect(url, false);
                this.Context.ApplicationInstance.CompleteRequest();
            }
            catch (Exception ex)
            {
                this.Errori.Add($"Si è verificato un errore durante l'apertura delle posizioni debitorie: {ex.Message}");
            }
        }
    }
}