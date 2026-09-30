using Init.Sigepro.FrontEnd.AppLogic.Common;
using Init.Sigepro.FrontEnd.AppLogic.GestioneTabelleDiBase;
using VBG.Shared.Infrastructure.Caching;
using Init.SIGePro.Manager.DTO.TabelleDiBase;

namespace Init.Sigepro.FrontEnd.AppLogic.Repositories.WebServices
{
    internal class WsTitoliRepository : ITitoliRepository
    {
        private const string CACHE_KEY = "TITOLI_CACHE_KEY_";
        private readonly TabelleDiBaseServiceCreator _serviceCreator;
        private readonly IApplicationCache _webCache;
        private readonly IAliasResolver _aliasResolver;

        public WsTitoliRepository(TabelleDiBaseServiceCreator serviceCreator, IApplicationCache webCache, IAliasResolver aliasResolver)
        {
            if (serviceCreator == null)
                throw new System.ArgumentNullException(nameof(serviceCreator));
            //Condition.Requires(serviceCreator, "serviceCreator").IsNotNull();

            this._serviceCreator = serviceCreator;
            this._webCache = webCache;
            this._aliasResolver = aliasResolver;
        }

        public TitoloDto[] GetList()
        {
            var cacheKey = CACHE_KEY + this._aliasResolver.AliasComune;

            return this._webCache.GetOrAdd(cacheKey, () =>
            {
                return this._serviceCreator.Call(ws => ws.Service.GetListaTitoli(ws.Token));
            });
        }
    }
}
