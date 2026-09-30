using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda;
using Init.Sigepro.FrontEnd.AppLogic.PresentazioneIstanze.Workflow;
using Init.Sigepro.FrontEnd.AppLogic.PresentazioneIstanze.Workflow.V2;
using Init.Sigepro.FrontEnd.AppLogic.SalvataggioDomanda;
using Init.Sigepro.FrontEnd.CoreServices.Autenticazione;
using Microsoft.AspNetCore.Mvc;

namespace AreaRiservataCore.Endpoints.Trieste
{
    [Route("endpoints/trieste/accesso-atti")]
    public class AccessoAttiController : Controller
    {
        private readonly ISalvataggioDomandaStrategy _salvataggioDomandaStrategy;
        private readonly IWorkflowService _workflowService;
        private readonly AliasSoftwareProvider _aliasSoftwareProvider;

        public AccessoAttiController(ISalvataggioDomandaStrategy salvataggioDomandaStrategy, IWorkflowService workflowService, AliasSoftwareProvider aliasSoftwareProvider)
        {
            this._salvataggioDomandaStrategy = salvataggioDomandaStrategy;
            this._workflowService = workflowService;
            this._aliasSoftwareProvider = aliasSoftwareProvider;
        }


        [HttpGet("{idDomanda}/{token}")]
        public IActionResult Index(string idDomanda, string token)
        {
            var dataKey = PresentazioneIstanzaDataKey.FromSerializationCode(idDomanda);
            var idPresentazione = dataKey.IdPresentazione;

            this._aliasSoftwareProvider.AliasComune = dataKey.IdComune;
            this._aliasSoftwareProvider.Software = dataKey.Software;

            var domanda = this._salvataggioDomandaStrategy.GetById(dataKey.IdPresentazione);

            this._workflowService.ClearCacheDomanda(idPresentazione);

            var wf = this._workflowService.GetWorkflowByIdDomanda(idPresentazione);

            var stepId = wf.GetIndiceStepByIdentifier(WorkflowSteps.GestioneAccessoAttiTrieste);
            var redirect = $"~/{dataKey.IdComune}/{dataKey.Software}/";

            if (stepId != -1)
            {
                redirect += $"inserimento-istanza/gestione-accesso-atti-trieste/{dataKey.IdPresentazione}/{stepId}?returning=1&token={token}";
            }

            return this.Redirect(redirect);
        }
    }
}
