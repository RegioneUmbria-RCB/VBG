using Init.Sigepro.FrontEnd.Pagamenti.ESED;
using log4net;
using Ninject;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using Init.Sigepro.FrontEnd.Infrastructure.Serialization;
using Init.Sigepro.FrontEnd.AppLogic.GestionePagamenti.ESED;
using System.Web.SessionState;

namespace Init.Sigepro.FrontEnd.Public.pagamenti
{
    /// <summary>
    /// Summary description for callbackpagamentiESED
    /// </summary>
    public class callbackpagamentiESED : Ninject.Web.HttpHandlerBase, IRequiresSessionState
    {
        ILog _log = LogManager.GetLogger(typeof(callbackpagamentiESED));

        [Inject]
        public PagamentiESEDService _service { get; set; }


        protected override void DoProcessRequest(HttpContext context)
        {
            _log.InfoFormat("querystring: {0}", context.Request.QueryString);
            _log.InfoFormat("form: {0}", context.Request.Form);

            string idDomanda = context.Request.QueryString["idpresentazione"];
            string buffer = context.Request.Form["buffer"];

            try
            {
                var commitMsg = this._service.NotificaPagamento(buffer, idDomanda);

                var xmlCommitMsg = commitMsg.ToXmlString().Replace(" xmlns:i=\"http://www.w3.org/2001/XMLSchema-instance\"", "");
                _log.InfoFormat("commit msg da inserire nella response: {0}", xmlCommitMsg);

                context.Response.ContentType = "text/plain";
                context.Response.Write(xmlCommitMsg);
            }
            catch (Exception ex)
            {
                _log.ErrorFormat("ERRORE GENERATO DURANTE LA NOTIFICA DEL PAGAMENTO, DOMANDA: {0}, BUFFER: {1}, ERRORE: {2}", idDomanda, buffer, ex.ToString());
            }
        }

        public override bool IsReusable
        {
            get { return false; }
        }
    }
}