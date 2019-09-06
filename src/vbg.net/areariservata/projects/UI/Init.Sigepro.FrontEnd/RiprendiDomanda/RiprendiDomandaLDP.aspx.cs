using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda;
using Init.Sigepro.FrontEnd.AppLogic.GestioneIntegrazioneLDP;
using Init.Sigepro.FrontEnd.AppLogic.PresentazioneIstanze.Workflow;
using Init.Sigepro.FrontEnd.Infrastructure.UrlsAndPaths;
using Ninject;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;

namespace Init.Sigepro.FrontEnd.RiprendiDomanda
{
    public partial class RiprendiDomandaLDP : Ninject.Web.PageBase
    {
        [Inject]
        protected WorkflowFromConfigurazioneService _workflowService { get; set; }

        protected void Page_Load(object sender, EventArgs e)
        {
            var serializationCode = Page.RouteData.Values["identificativoDomanda"].ToString();
            var tokenPartnerApp = (Page.RouteData.Values["tokenPartnerApp"]??String.Empty).ToString();
            var dataKey = PresentazioneIstanzaDataKey.FromSerializationCode(serializationCode);

            HttpContext.Current.Items["alias"] = dataKey.IdComune;
            HttpContext.Current.Items["software"] = dataKey.Software;

            var stepId = _workflowService.GetCurrentWorkflow().TrovaIndiceStepDaUrlParziale("BenvenutoLDP.aspx");
            var ub = new UrlBuilder();

            var url = ub.Build("~/reserved/InserimentoIstanza/BenvenutoLDP.aspx", pars => {
                pars.Add("idComune", dataKey.IdComune);
                pars.Add("software", dataKey.Software);
                pars.Add("idPresentazione", dataKey.IdPresentazione);
                pars.Add("returning", "true");
                pars.Add("stepId", stepId);
            });

            Response.Redirect(url);
        }
    }
}