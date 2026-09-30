using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneOneri;
using Init.Sigepro.FrontEnd.AppLogic.Pagamenti.NodoPagamenti;
using log4net;
using Ninject;
using System;
using System.Web;
using System.Web.SessionState;

namespace Init.Sigepro.FrontEnd.Reserved.InserimentoIstanza.Pagamenti
{
    /// <summary>
    /// Summary description for VerificaStatoRicevutaNodoPagamenti
    /// </summary>
    public class VerificaStatoRicevutaNodoPagamenti : Ninject.Web.HttpHandlerBase, IRequiresSessionState
    {
        [Inject]
        protected IPagamentiNodoPagamentiService _pagamentiService { get; set; }

        private readonly ILog _log = LogManager.GetLogger(typeof(VerificaStatoRicevutaNodoPagamenti));

        protected override void DoProcessRequest(HttpContext context)
        {
            try
            {
                var idDomanda = Convert.ToInt32(context.Request.QueryString["idPresentazione"].ToString());
                // var idTransazione = context.Request.QueryString["idTransaction"].ToString();

                this._pagamentiService.AggiornaStatoPagamenti(idDomanda);

                var statoPagamento = this._pagamentiService.GetStatoPosizioni(idDomanda);

                if (statoPagamento.StatoGlobale == StatoPagamentoOnereEnum.PagamentoRiuscito)
                {
                    context.Response.Write("PAGATO");
                    return;
                }

                if (statoPagamento.StatoGlobale == StatoPagamentoOnereEnum.PagamentoFallito)
                {
                    context.Response.Write("ANNULLATO");
                    return;
                }

                context.Response.Write("NON_PAGATO");
            }
            catch (Exception ex)
            {
                this._log.Error(ex.Message);
                throw;
            }
        }

        public override bool IsReusable => false;
    }
}