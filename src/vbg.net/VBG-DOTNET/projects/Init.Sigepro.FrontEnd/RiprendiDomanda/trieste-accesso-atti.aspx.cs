using Init.Sigepro.FrontEnd.AppLogic.Autenticazione.Vbg.AutenticazioneUtente;
using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda;
using Init.Sigepro.FrontEnd.AppLogic.PresentazioneIstanze.Workflow;
using Init.Sigepro.FrontEnd.AppLogic.PresentazioneIstanze.Workflow.V2;
using Init.Sigepro.FrontEnd.AppLogic.SalvataggioDomanda;
using Init.Sigepro.FrontEnd.HttpModules;
using Init.Sigepro.FrontEnd.Infrastructure.UrlsAndPaths;
using Ninject;
using System;
using System.Web;

namespace Init.Sigepro.FrontEnd.RiprendiDomanda
{
    public partial class trieste_accesso_atti : System.Web.UI.Page
    {
        [Inject]
        protected IWorkflowService _workflowService { get; set; }

        [Inject]
        protected ISalvataggioDomandaStrategy _salvataggioStrategy { get; set; }
        [Inject]
        protected IAreaRiservataAuthenticationService _areaRiservataAuthenticationService { get; set; }

        protected void Page_Load(object sender, EventArgs e)
        {
            var serializationCode = this.Page.RouteData.Values["idDomanda"].ToString();
            var token = (this.Page.RouteData.Values["token"] ?? String.Empty).ToString();
            var dataKey = PresentazioneIstanzaDataKey.FromSerializationCode(serializationCode);

            HttpContext.Current.Items["alias"] = dataKey.IdComune;
            HttpContext.Current.Items["IdComune"] = dataKey.IdComune;
            HttpContext.Current.Items["Software"] = dataKey.Software;

            new AuthenticationHelper(HttpContext.Current).ForceCheckAuthentication();

            var domanda = this._salvataggioStrategy.GetById(dataKey.IdPresentazione);

            this._workflowService.ClearCacheDomanda(dataKey.IdPresentazione);

            var stepId = this._workflowService.GetWorkflowByIdDomanda(dataKey.IdPresentazione).GetIndiceStepByIdentifier(WorkflowSteps.GestioneAccessoAttiTrieste);

            var url = UrlBuilder.Url("~/reserved/inserimentoistanza/triesteaccessoatti.aspx", pars =>
            {
                pars.Add("idComune", dataKey.IdComune);
                pars.Add("software", dataKey.Software);
                pars.Add("idPresentazione", dataKey.IdPresentazione);
                pars.Add("returning", "1");
                pars.Add("stepId", stepId);
            });

            this._areaRiservataAuthenticationService.AuthenticateUser(token);

            this.Response.Redirect(url);
        }
    }
}