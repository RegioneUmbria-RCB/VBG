using Init.Sigepro.FrontEnd.AppLogic.GestionePagamenti.EntraNext;
using Ninject;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.SessionState;

namespace Init.Sigepro.FrontEnd.Public.pagamenti.EntraNext
{
    /// <summary>
    /// Summary description for NotificaPagamentoAccettatoEntraNext
    /// </summary>
    public class NotificaPagamentoAccettatoEntraNext : Ninject.Web.HttpHandlerBase, IRequiresSessionState
    {
        [Inject]
        protected PagamentiEntraNextService PagamentiService { get; set; }

        protected override void DoProcessRequest(HttpContext context)
        {
            var idDomanda = context.Request.QueryString["idPresentazione"].ToString();
            var idTransaction = context.Request.QueryString["idTransaction"].ToString();
            var codicePagamento = context.Request.QueryString["codicePagamento"].ToString();

            this.PagamentiService.SalvaPagamentoNotificato(Convert.ToInt32(idDomanda), codicePagamento, idTransaction);
        }

        public override bool IsReusable => false;
    }
}