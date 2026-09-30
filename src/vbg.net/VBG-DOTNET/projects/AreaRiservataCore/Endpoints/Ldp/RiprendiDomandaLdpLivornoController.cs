using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda;
using Init.Sigepro.FrontEnd.AppLogic.PresentazioneIstanze.Workflow;
using Init.Sigepro.FrontEnd.AppLogic.PresentazioneIstanze.Workflow.V2;
using Init.Sigepro.FrontEnd.AppLogic.SalvataggioDomanda;
using Init.Sigepro.FrontEnd.CoreServices.Autenticazione;
using Microsoft.AspNetCore.Mvc;

namespace AreaRiservataCore.Endpoints.Ldp
{
    [Route("riprendiDomandaLdpLivorno"/*, "~/riprendiDomanda/riprendiDomandaLDP.aspx"*/)]
    public class RiprendiDomandaLdpLivornoController : Controller
    {
        private readonly AliasSoftwareProvider _aliasSoftwareProvider;
        private readonly IWorkflowService _workflowService;
        private readonly ISalvataggioDomandaStrategy _salvataggioDomandaStrategy;

        public RiprendiDomandaLdpLivornoController(AliasSoftwareProvider aliasSoftwareProvider, IWorkflowService workflowService,
            ISalvataggioDomandaStrategy salvataggioDomandaStrategy)
        {
            this._aliasSoftwareProvider = aliasSoftwareProvider;
            this._workflowService = workflowService;
            this._salvataggioDomandaStrategy = salvataggioDomandaStrategy;
        }

        [HttpGet("{serializationCode}/{tokenPartnerApp?}")]
        public IActionResult Index(string serializationCode, string? tokenPartnerApp)
        {
            var dataKey = PresentazioneIstanzaDataKey.FromSerializationCode(serializationCode);

            var idDomanda = dataKey.IdPresentazione;

            this._aliasSoftwareProvider.AliasComune = dataKey.IdComune;
            this._aliasSoftwareProvider.Software = dataKey.Software;

            var domanda = this._salvataggioDomandaStrategy.GetById(dataKey.IdPresentazione);

            this._workflowService.ClearCacheDomanda(idDomanda);

            var wf = this._workflowService.GetWorkflowByIdDomanda(idDomanda);

            var stepId = wf.GetIndiceStepByIdentifier(WorkflowSteps.IntegrazioneLdpLivorno);

            var returnUrl = $"~/{dataKey.IdComune}/{dataKey.Software}/";

            if (stepId != -1)
            {
                returnUrl += $"inserimento-istanza/integrazione-ldp-livorno/{dataKey.IdPresentazione}/{stepId}/returning?token={tokenPartnerApp}";
            }

            return this.Redirect(returnUrl);
        }
    }
}
