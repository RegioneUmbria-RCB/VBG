using Init.Sigepro.FrontEnd.AppLogic.GestioneComuni;
using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneOneri;
using Init.Sigepro.FrontEnd.AppLogic.Pagamenti.NodoPagamenti;
using Init.Sigepro.FrontEnd.AppLogic.Pagamenti.NodoPagamenti.Anagrafiche;
using Init.Sigepro.FrontEnd.AppLogic.Pagamenti.NodoPagamenti.NODOPAGAMENTI.GenerazioneUrlRitorno;
using Init.Sigepro.FrontEnd.AppLogic.SalvataggioDomanda;
using Init.Sigepro.FrontEnd.Infrastructure.UrlsAndPaths;
using Init.Sigepro.FrontEnd.QsParameters;
using Init.Sigepro.FrontEnd.QsParameters.Pagamenti;
using Ninject;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading;
using VBG.Pagamenti.NodoPagamenti.Attivazione;

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
        protected IResolveUrl _resolveUrl { get; set; }

        [Inject]
        protected IComuniService _comuniService { get; set; }

        [Inject]
        protected ISalvataggioDomandaStrategy _salvataggioDomandaStrategy { get; set; }

        [Inject]
        protected IEstremiDomandaNodoPagamentiReader _estremiDomandaNodoPagamentiReader { get; set; }


        public bool ErrorePagamento = false;

        public string UrlPagamenti { get; set; }
        public HttpMethodEnum HttpMethod { get; set; } = HttpMethodEnum.GET;
        public IEnumerable<HttpPostParameter> PostParameters { get; set; } = Enumerable.Empty<HttpPostParameter>();

        public string IdTransaction { get; set; }

        public QsPagamentoOnTheFly PagamentoOTFQueryParameter => new QsPagamentoOnTheFly(this.Request.QueryString);

        public string UrlPaginaIniziale => this.ResolveClientUrl(
            UrlBuilder.Url(
                "~/reserved/default.aspx",
                qs =>
                {
                    qs.Add(new QsAliasComune(this.IdComune));
                    qs.Add(new QsSoftware(this.Software));
                }));

        public PagamentoNodoPagamenti()
        {

        }

        protected void Page_Load(object sender, EventArgs e)
        {
            if (!this.IsPostBack)
            {
                this.DataBind();
            }
        }

        public override bool CanEnterStep()
        {
            return this._nodoPagamentiService.NodoPagamentoAttivo(this.IdDomanda) &&
                    this._nodoPagamentiService.DomandaRichiedePagamentoOnline(this.IdDomanda);
        }

        public override void DataBind()
        {
            try
            {
                this.pnlDettaglioPagamenti.Visible = false;

                if (!this._nodoPagamentiService.PagamentoAvviato(this.IdDomanda))
                {
                    var pagaDopoSupportato = this._nodoPagamentiService.PagoDopoAttivo(this.IdDomanda);
                    var forzaPagamentoOTF = this.PagamentoOTFQueryParameter.UtilizzaPagamentoOTF;

                    if (pagaDopoSupportato && !forzaPagamentoOTF)
                    {
                        this.AvviaRedirectAPagoDopo();
                    }
                    else
                    {
                        this.AvviaPagamentoOTF();
                    }

                    return;
                }

                this._nodoPagamentiService.AggiornaStatoPagamenti(this.IdDomanda);

                var stato = this._nodoPagamentiService.GetStatoPosizioni(this.IdDomanda);

                this.rptDettagliPosizioni.DataSource = stato.Pagamenti;
                this.rptDettagliPosizioni.DataBind();
                this.pnlDettaglioPagamenti.Visible = true;

                switch (stato.StatoGlobale)
                {
                    case StatoPagamentoOnereEnum.PagamentoRiuscito:
                        this.PagamentoRiuscito();
                        break;
                    case StatoPagamentoOnereEnum.PagamentoFallito:
                        this.PagamentoFallito();
                        break;
                    default:
                        this.PagamentoInAttesa();
                        break;
                }
            }
            catch (ThreadAbortException)
            {
                // Viene da una response.redirect. Ignoro l'errore
            }
            catch (Exception ex)
            {
                this.multiView.ActiveViewIndex = Constants.ViewPagamentoFallito;
                this.Errori.Add($"Pagamento fallito: {ex.Message}");
                this.Master.MostraBottoneAvanti = false;
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

            this.Response.Redirect(url);
        }

        private void PagamentoFallito()
        {
            this.multiView.ActiveViewIndex = Constants.ViewPagamentoFallito;
            this.Master.MostraPaginatoreSteps = false;
        }


        private void PagamentoInAttesa()
        {
            this.multiView.ActiveViewIndex = Constants.ViewPagamentoInAttesa;
            this.Master.MostraPaginatoreSteps = false;
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
                this.Master.MostraBottoneAvanti = false;
            }
        }

        private void AvviaPagamentoOTF()
        {
            try
            {
                this.Master.MostraBottoneAvanti = false;

                var estremiDomanda = this._estremiDomandaNodoPagamentiReader.GetEstremiDomandaDaIdDomanda(this.IdDomanda, this.Master.LastStep);

                var esitoAttivazione = this._nodoPagamentiService.AvviaPagamentoOnTheFly(estremiDomanda, new ArLegacyUrlRitornoPagamentiProvider(this._resolveUrl));

                if (esitoAttivazione.Esito)
                {
                    if (esitoAttivazione.HttpMethod == HttpMethodEnum.GET)
                    {
                        this.Response.Redirect(esitoAttivazione.UrlSistemaPagamenti);
                        this.Response.End();
                        return;
                    }

                    this.UrlPagamenti = esitoAttivazione.UrlSistemaPagamenti;
                    this.PostParameters = esitoAttivazione.PostParameters;
                    this.HttpMethod = esitoAttivazione.HttpMethod;

                    this.multiView.ActiveViewIndex = Constants.ViewNuovoPagamento;
                }
                else
                {
                    this.multiView.ActiveViewIndex = Constants.ViewPagamentoFallito;
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
            this.Response.Redirect(this.Request.RawUrl);
        }
    }
}