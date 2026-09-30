using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda;
using Init.Sigepro.FrontEnd.AppLogic.PresentazioneIstanze.Workflow;
using Init.Sigepro.FrontEnd.AppLogic.PresentazioneIstanze.Workflow.V2;
using Init.Sigepro.FrontEnd.HttpModules;
using Init.Sigepro.FrontEnd.Infrastructure.UrlsAndPaths;
using Ninject;
using System;
using System.Web;

namespace Init.Sigepro.FrontEnd.RiprendiDomanda
{
    public partial class RiprendiDomandaLDP : Ninject.Web.PageBase
    {
        [Inject]
        protected IWorkflowService _workflowService { get; set; }

        protected void Page_Load(object sender, EventArgs e)
        {
            var serializationCode = this.Page.RouteData.Values["identificativoDomanda"].ToString();
            var dataKey = PresentazioneIstanzaDataKey.FromSerializationCode(serializationCode);

            HttpContext.Current.Items["alias"] = dataKey.IdComune;
            HttpContext.Current.Items["IdComune"] = dataKey.IdComune;
            HttpContext.Current.Items["Software"] = dataKey.Software;

            new AuthenticationHelper(HttpContext.Current).ForceCheckAuthentication();

            this._workflowService.ClearCacheDomanda(dataKey.IdPresentazione);

            var stepId = this._workflowService.GetWorkflowByIdDomanda(dataKey.IdPresentazione).GetIndiceStepByIdentifier(WorkflowSteps.BenvenutoLdp);
            var ub = new UrlBuilder();

            var url = ub.Build("~/reserved/InserimentoIstanza/BenvenutoLDP.aspx", pars =>
            {
                pars.Add("idComune", dataKey.IdComune);
                pars.Add("software", dataKey.Software);
                pars.Add("idPresentazione", dataKey.IdPresentazione);
                pars.Add("returning", "true");
                pars.Add("stepId", stepId);
            });

            this.Response.Redirect(url);
        }
    }
}