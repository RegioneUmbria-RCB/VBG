using Init.Sigepro.FrontEnd.AppLogic.PresentazioneIstanze.Workflow;
using Init.Sigepro.FrontEnd.Infrastructure.UrlsAndPaths;
using Ninject;
using System;
using System.Web;

namespace Init.Sigepro.FrontEnd.RiprendiDomanda
{
    public partial class riprendi_domanda : Ninject.Web.PageBase
    {
        [Inject]
        protected IWorkflowService _workflowService { get; set; }

        protected void Page_Load(object sender, EventArgs e)
        {
            var idDomanda = this.Page.RouteData.Values["idDomanda"].ToString();
            var alias = this.Page.RouteData.Values["alias"].ToString();
            var software = this.Page.RouteData.Values["software"].ToString();

            HttpContext.Current.Items["alias"] = alias;
            HttpContext.Current.Items["software"] = software;

            var url = UrlBuilder.Url("~/reserved/InserimentoIstanza/benvenuto.aspx", pars =>
            {
                pars.Add("idComune", alias);
                pars.Add("software", software);
                pars.Add("idPresentazione", idDomanda);
                pars.Add("stepId", 0);
            });

            this.Response.Redirect(url);
        }
    }
}