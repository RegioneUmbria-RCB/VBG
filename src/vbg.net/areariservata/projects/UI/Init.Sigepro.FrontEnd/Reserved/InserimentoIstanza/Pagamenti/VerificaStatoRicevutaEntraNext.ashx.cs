using Init.Sigepro.FrontEnd.AppLogic.GestionePagamenti.EntraNext;
using Init.Sigepro.FrontEnd.Pagamenti.ENTRANEXT;
using Init.Sigepro.FrontEnd.Pagamenti.EntraNextService;
using log4net;
using Ninject;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.SessionState;

namespace Init.Sigepro.FrontEnd.Reserved.InserimentoIstanza.Pagamenti
{
    /// <summary>
    /// Summary description for VerificaStatoRicevutaEntraNext
    /// </summary>
    public class VerificaStatoRicevutaEntraNext : Ninject.Web.HttpHandlerBase, IRequiresSessionState
    {

        [Inject]
        protected PagamentiEntraNextService PagamentiService { get; set; }

        ILog _log = LogManager.GetLogger(typeof(VerificaStatoRicevutaEntraNext));

        protected override void DoProcessRequest(HttpContext context)
        {
            try
            {
                var idDomanda = context.Request.QueryString["idPresentazione"].ToString();
                var idTransazione = context.Request.QueryString["idTransaction"].ToString();

                PagamentiService.AggiornaStatoPagamentiInSospeso(Convert.ToInt32(idDomanda));
                var stato = PagamentiService.VerificaPagamento(Convert.ToInt32(idDomanda), idTransazione);

                context.Response.Write(stato ? "OK" : "DIFFERITO");
            }
            catch(Exception ex)
            {
                _log.Error(ex.Message);
                throw;
            }
        }

        public override bool IsReusable => false;
    }
}