using Init.Sigepro.FrontEnd.AppLogic.Adapters;
using Init.Sigepro.FrontEnd.AppLogic.SalvataggioDomanda;
using Init.Sigepro.FrontEnd.AppLogic.WebServiceReferences.IstanzeService;
using Init.Sigepro.FrontEnd.QsParameters;
using Ninject;
using System.Web;
using System.Web.SessionState;

namespace Init.Sigepro.FrontEnd.Reserved.InserimentoIstanza
{
    /// <summary>
    /// Summary description for XmlDomanda
    /// </summary>
    public class XmlDomanda : Ninject.Web.HttpHandlerBase, IReadOnlySessionState
    {
        [Inject]
        public ISalvataggioDomandaStrategy _caricamentoDomandaStrategy { get; set; }
        [Inject]
        public IIstanzaSigeproAdapterService _istanzaSigeproAdapterService { get; set; }


        public override bool IsReusable => false;

        protected override void DoProcessRequest(HttpContext context)
        {
            var idDomanda = new QsIdDomandaOnline(context.Request.QueryString).Value;
            var domanda = this._caricamentoDomandaStrategy.GetById(idDomanda);

            var istanzaXml = this._istanzaSigeproAdapterService.ToIstanzaBackoffice(domanda.ReadInterface).ToXmlModelloRiepilogo(domanda.DataKey.ToString()); ;

            context.Response.ContentType = "text/xml";
            context.Response.ContentEncoding = System.Text.Encoding.UTF8;
            context.Response.Write(istanzaXml);
        }
    }
}