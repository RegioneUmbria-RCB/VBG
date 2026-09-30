using Init.Sigepro.FrontEnd.AppLogic.GestioneComuni;
using Init.Sigepro.FrontEnd.AppLogic.GestioneOneri;
using Init.Sigepro.FrontEnd.AppLogic.GestionePagamenti.NODOPAGAMENTI;
using Init.Sigepro.FrontEnd.AppLogic.GestionePagamenti.NODOPAGAMENTI.Anagrafiche;
using Init.Sigepro.FrontEnd.AppLogic.SalvataggioDomanda;
using Init.Sigepro.FrontEnd.Infrastructure.UrlsAndPaths;
using Init.Sigepro.FrontEnd.QsParameters;
using Init.Sigepro.FrontEnd.QsParameters.Pagamenti;
using Ninject;
using System;
using System.Threading;

namespace Init.Sigepro.FrontEnd.Reserved.InserimentoIstanza.Pagamenti
{
    public partial class PagamentoNodoPagamenti : IstanzeStepPage
    {
        private static class Constants
        {
            public const int ViewNuovoPagamento = 0;
            public const int ViewPagamentoFallito = 1;
            public const int ViewPagamentoRiuscito = 2;
            public const int ViewPagamentoInAttesa = 3;
        }

        [Inject]
        protected IPagamentiNodoPagamentiService _nodoPagamentiService { get; set; }

        [Inject]
        protected IComuniService _comuniService { get; set; }

        [Inject]
        protected ISalvataggioDomandaStrategy _salvataggioDomandaStrategy { get; set; }

        [Inject]
        protected IEstremiDomandaNodoPagamentiReader _estremiDomandaNodoPagamentiReader { get; set; }


        public bool ErrorePagamento = false;

        public string UrlPagamenti { get; set; }

        public string IdTransaction { get; set; }

        public QsPagamentoOnTheFly PagamentoOTFQueryParameter => new QsPagamentoOnTheFly(Request.QueryString);

        public string UrlPaginaIniziale => ResolveClientUrl(
            UrlBuilder.Url(
                "~/reserved/default.aspx",
                qs =>
                {
                    qs.Add(new QsAliasComune(IdComune));
                    qs.Add(new QsSoftware(Software));
                }));

        public PagamentoNodoPagamenti()
        {

        }

        protected void Page_Load(object sender, EventArgs e)
        {
            if (!IsPostBack)
            {
                DataBind();
            }
        }

        public override bool CanEnterStep()
        {
            return this._nodoPagamentiService.NodoPagamentoAttivo(IdDomanda) &&
                    this._nodoPagamentiService.DomandaRichiedePagamentoOnline(IdDomanda);
        }

        public override void DataBind()
        {
            try
            {
                pnlDettaglioPagamenti.Visible = false;

                if (!this._nodoPagamentiService.PagamentoAvviato(IdDomanda))
                {
                    var pagaDopoSupportato = this._nodoPagamentiService.PagoDopoAttivo(this.IdDomanda);
                    var forzaPagamentoOTF = PagamentoOTFQueryParameter.UtilizzaPagamentoOTF;

                    if (pagaDopoSupportato && !forzaPagamentoOTF)
                    {
                        AvviaRedirectAPagoDopo();
                    } 
                    else
                    {
                        AvviaPagamentoOTF();
                    }
                    
                    return;
                }

                this._nodoPagamentiService.AggiornaStatoPagamenti(IdDomanda);

                var stato = this._nodoPagamentiService.GetStatoPosizioni(IdDomanda);

                rptDettagliPosizioni.DataSource = stato.Pagamenti;
                rptDettagliPosizioni.DataBind();
                pnlDettaglioPagamenti.Visible = true;

                switch (stato.StatoGlobale)
                {
                    case StatoPagamentoOnereEnum.PagamentoRiuscito:
                        PagamentoRiuscito();
                        break;
                    case StatoPagamentoOnereEnum.PagamentoFallito:
                        PagamentoFallito();
                        break;
                    default:
                        PagamentoInAttesa();
                        break;
                }
            }
            catch (ThreadAbortException)
            {
                // Viene da una response.redirect. Ignoro l'errore
            }
            catch (Exception ex)
            {
                multiView.ActiveViewIndex = Constants.ViewPagamentoFallito;
                this.Errori.Add($"Pagamento fallito: {ex.Message}");
                Master.MostraBottoneAvanti = false;
            }
        }

        private void AvviaRedirectAPagoDopo()
        {
            var url = UrlBuilder.Url("~/reserved/inserimentoistanza/pagamenti/pago-dopo/scelta-pago-dopo.aspx", qs =>
            {
                qs.AddAlias()
                  .AddSoftware()
                  .AddIdPresentazione()
                  .AddStepId();
            });

            Response.Redirect(url);
        }

        private void PagamentoFallito()
        {
            this.multiView.ActiveViewIndex = Constants.ViewPagamentoFallito;
            Master.MostraPaginatoreSteps = false;
        }


        private void PagamentoInAttesa()
        {
            this.multiView.ActiveViewIndex = Constants.ViewPagamentoInAttesa;
            Master.MostraPaginatoreSteps = false;
        }

        private void PagamentoRiuscito()
        {
            try
            {
                // this._nodoPagamentiService.AggiornaStatoPagamento(this.IdDomanda);
                this.multiView.ActiveViewIndex = Constants.ViewPagamentoRiuscito;
            }
            catch (Exception ex)
            {
                this.multiView.ActiveViewIndex = Constants.ViewPagamentoFallito;
                this.Errori.Add($"{ex.Message}");
                Master.MostraBottoneAvanti = false;
            }
        }

        private void AvviaPagamentoOTF()
        {
            try
            {
                Master.MostraBottoneAvanti = false;

                var estremiDomanda = this._estremiDomandaNodoPagamentiReader.GetEstremiDomandaDaIdDomanda(this.IdDomanda, Master.LastStep);

                this.UrlPagamenti = this._nodoPagamentiService.InizializzaPagamento(estremiDomanda);

                if (!String.IsNullOrEmpty(this.UrlPagamenti))
                {
                    multiView.ActiveViewIndex = Constants.ViewNuovoPagamento;
                }
                else
                {
                    multiView.ActiveViewIndex = Constants.ViewPagamentoFallito;
                }
            }
            catch (Exception ex)
            {
                this.Errori.Add(ex.Message);
                this.ErrorePagamento = true;
            }
        }

        protected void cmdTornaIndietro_Click(object sender, EventArgs e)
        {
            this._nodoPagamentiService.AnnullaPagamenti(this.IdDomanda, FlagEliminazionePagamenti.PagamentiFalliti);
            this.Master.cmdPrevStep_Click(this, EventArgs.Empty);
        }

        protected void cmdRitentaPagamento_Click(object sender, EventArgs e)
        {
            this._nodoPagamentiService.AnnullaPagamenti(this.IdDomanda, FlagEliminazionePagamenti.PagamentiFalliti);
            Response.Redirect(Request.RawUrl);
        }
    }
}