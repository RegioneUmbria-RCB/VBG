using Init.Sigepro.FrontEnd.AppLogic.Common;
using VBG.Shared.Infrastructure.Caching;

namespace Init.Sigepro.FrontEnd.AppLogic.PresentazioneIstanze.Workflow
{
    public class CachedWorkflowService : IWorkflowService
    {
        private readonly ISessionCache _sessionCache;
        private readonly WorkflowService _workflowService;
        private readonly IAliasResolver _aliasResolver;

        public CachedWorkflowService(ISessionCache sessionCache, IAliasResolver aliasResolver, WorkflowService workflowService)
        {
            this._sessionCache = sessionCache;
            this._aliasResolver = aliasResolver;
            this._workflowService = workflowService;
        }

        public IWorkflowDomandaOnline GetWorkflowByIdDomanda(int idDomandaOnline)
        {
            var cacheKey = $"WF-{this._aliasResolver.AliasComune}-{idDomandaOnline}";

            return this._sessionCache.GetOrAdd(cacheKey, () => this._workflowService.GetWorkflowByIdDomanda(idDomandaOnline));
        }

        public void ClearCacheDomanda(int idDomanda)
        {
            var cacheKey = $"WF-{this._aliasResolver.AliasComune}-{idDomanda}";

            this._sessionCache.Remove(cacheKey);
        }
    }
}
