using Init.Sigepro.FrontEnd.AppLogic.SalvataggioDomanda;

namespace Init.Sigepro.FrontEnd.AppLogic.PresentazioneIstanze.Workflow
{
    public class WorkflowService : IWorkflowService
    {
        private readonly ISalvataggioDomandaStrategy _salvataggioDomandaStrategy;
        private readonly IWorkflowLoaderService _workflowLoader;

        public WorkflowService(ISalvataggioDomandaStrategy salvataggioDomandaStrategy, IWorkflowLoaderService workflowLoader)
        {
            this._salvataggioDomandaStrategy = salvataggioDomandaStrategy;
            this._workflowLoader = workflowLoader;
        }

        public virtual void ClearCacheDomanda(int idDomanda)
        {
        }

        public virtual IWorkflowDomandaOnline GetWorkflowByIdDomanda(int idDomandaOnline)
        {
            var domanda = this._salvataggioDomandaStrategy.GetById(idDomandaOnline);
            var idIntervento = domanda?.ReadInterface?.AltriDati?.Intervento?.Codice;

            return this._workflowLoader.GetMergedWorkflowByIdIntervento(idIntervento, useCachedDefaultWorkflow: true);
        }



    }
}
