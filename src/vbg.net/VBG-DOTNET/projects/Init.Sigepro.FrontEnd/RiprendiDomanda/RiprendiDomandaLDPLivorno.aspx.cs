using Init.Sigepro.FrontEnd.AppLogic.Autenticazione.Vbg.AutenticazioneUtente;
using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda;
using Init.Sigepro.FrontEnd.AppLogic.PresentazioneIstanze.Workflow;
using Init.Sigepro.FrontEnd.AppLogic.PresentazioneIstanze.Workflow.V2;
using Init.Sigepro.FrontEnd.AppLogic.SalvataggioDomanda;
using Init.Sigepro.FrontEnd.Infrastructure.UrlsAndPaths;
using Ninject;
using System;
using System.Web;

namespace Init.Sigepro.FrontEnd.RiprendiDomanda
{
    public partial class RiprendiDomandaLDPLivorno : Ninject.Web.PageBase
    {
        [Inject]
        protected IWorkflowService _workflowService { get; set; }

        [Inject]
        protected ISalvataggioDomandaStrategy _salvataggioStrategy { get; set; }

        [Inject]
        protected IAreaRiservataAuthenticationService _areaRiservataAuthenticationService { get; set; }

        protected void Page_Load(object sender, EventArgs e)
        {
            var serializationCode = this.Page.RouteData.Values["identificativoDomanda"].ToString();
            var token = (this.Page.RouteData.Values["token"] ?? String.Empty).ToString();
            var dataKey = PresentazioneIstanzaDataKey.FromSerializationCode(serializationCode);

            HttpContext.Current.Items["alias"] = dataKey.IdComune;
            HttpContext.Current.Items["software"] = dataKey.Software;

            var domanda = this._salvataggioStrategy.GetById(dataKey.IdPresentazione);

            this._workflowService.ClearCacheDomanda(dataKey.IdPresentazione);

            var stepId = this._workflowService.GetWorkflowByIdDomanda(dataKey.IdPresentazione).GetIndiceStepByIdentifier(WorkflowSteps.IntegrazioneLdpLivorno);

            var url = UrlBuilder.Url("~/reserved/InserimentoIstanza/ldp-livorno/integrazione-ldp-livorno.aspx", pars =>
            {
                pars.Add("idComune", dataKey.IdComune);
                pars.Add("software", dataKey.Software);
                pars.Add("idPresentazione", dataKey.IdPresentazione);
                pars.Add("returning", "true");
                pars.Add("stepId", stepId);
            });

            this._areaRiservataAuthenticationService.AuthenticateUser(token);

            this.Response.Redirect(url);
        }
    }
}