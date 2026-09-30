using VBG.Shared.Infrastructure.Caching;
using Init.SIGePro.Manager.DTO.TabelleDiBase;
using System.Collections.Generic;

namespace Init.Sigepro.FrontEnd.AppLogic.GestioneTabelleDiBase
{
    internal class WsElenchiProfessionaliRepository : IElenchiProfessionaliRepository
    {
        private static class Constants
        {
            public const string CacheKey = "Cache.ElenchiProfessionali";
        }

        private readonly IApplicationCache _applicationCache;
        private readonly TabelleDiBaseServiceCreator _serviceCreator;

        public WsElenchiProfessionaliRepository(IApplicationCache applicationCache, TabelleDiBaseServiceCreator serviceCreator)
        {
            this._applicationCache = applicationCache ?? throw new System.ArgumentNullException(nameof(applicationCache));
            this._serviceCreator = serviceCreator ?? throw new System.ArgumentNullException(nameof(serviceCreator));
        }

        public List<ElencoProfessionaleDto> GetList()
        {
            return this._applicationCache.GetOrAdd(Constants.CacheKey, () => this.GetListInternal());
        }

        private List<ElencoProfessionaleDto> GetListInternal()
        {
            return this._serviceCreator.Call(ws =>
            {
                return new List<ElencoProfessionaleDto>(ws.Service.GetElenchiProfessionali(ws.Token));
            });
        }
    }
}
