using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda;
using Init.Sigepro.FrontEnd.AppLogic.PresentazioneIstanze.Workflow;
using Init.Sigepro.FrontEnd.AppLogic.PresentazioneIstanze.Workflow.V2;
using Init.Sigepro.FrontEnd.AppLogic.SalvataggioDomanda;
using Init.Sigepro.FrontEnd.CoreServices.Autenticazione;
using Microsoft.AspNetCore.Mvc;

namespace AreaRiservataCore.Endpoints.Ldp
{
    [Route("riprendiDomandaLdp"/*, "~/riprendiDomanda/riprendiDomandaLDP.aspx"*/)]
    public class RiprendiDomandaLdpController : Controller
    {
        private readonly AliasSoftwareProvider _aliasSoftwareProvider;
        private readonly IWorkflowService _workflowService;
        private readonly ISalvataggioDomandaStrategy _salvataggioDomandaStrategy;

        public RiprendiDomandaLdpController(AliasSoftwareProvider aliasSoftwareProvider, IWorkflowService workflowService,
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

            var domanda = this._salvataggioDomandaStrategy.GetById(idDomanda);

            this._workflowService.ClearCacheDomanda(dataKey.IdPresentazione);

            var wf = this._workflowService.GetWorkflowByIdDomanda(idDomanda);

            var stepId = wf.GetIndiceStepByIdentifier(WorkflowSteps.BenvenutoLdp);

            var returnUrl = $"~/{dataKey.IdComune}/{dataKey.Software}/";

            if (stepId != -1)
            {
                returnUrl += $"inserimento-istanza/benvenuto-ldp/{dataKey.IdPresentazione}/{stepId}";
            }

            if (!String.IsNullOrEmpty(tokenPartnerApp))
            {
                returnUrl = $"{returnUrl}?token={tokenPartnerApp}";
            }


            return this.Redirect(returnUrl);
        }
    }
}
