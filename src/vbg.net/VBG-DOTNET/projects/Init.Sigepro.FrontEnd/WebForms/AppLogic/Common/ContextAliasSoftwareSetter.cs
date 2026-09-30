using Init.Sigepro.FrontEnd.AppLogic.Common;
using VBG.Shared.Infrastructure.Caching;

namespace Init.Sigepro.FrontEnd.WebForms.AppLogic.Common
{
    public class ContextAliasSoftwareSetter : IAliasSoftwareSetter
    {
        private readonly IContextCache _contextCache;

        public ContextAliasSoftwareSetter(IContextCache contextCache)
        {
            this._contextCache = contextCache;
        }

        public void Set(string alias, string software = "")
        {
            this._contextCache.Set("IdComune", alias);
            this._contextCache.Set("Software", software ?? "");
        }
    }
}