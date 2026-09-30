using Init.Sigepro.FrontEnd.AppLogic.Common;
using Init.Sigepro.FrontEnd.AppLogic.ServiceCreators;
using Init.Sigepro.FrontEnd.Infrastructure.Caching;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.Sigepro.FrontEnd.AppLogic.GestioneRisorseTestuali
{
    public class CachedRisorseTestualiService: RisorseTestualiService
    {
        IAliasResolver _aliasResolver;
        ISoftwareResolver _softwareResolver;

        internal CachedRisorseTestualiService(AreaRiservataServiceCreator serviceCreator, IAliasResolver aliasResolver, ISoftwareResolver softwareResolver)
            :base(serviceCreator, softwareResolver)
        {
            this._aliasResolver = aliasResolver;
            this._softwareResolver = softwareResolver;
        }

        public override Dictionary<string, string> GetListaRisorse()
        {
            var cacheKey = GetCacheKey();

            return CacheHelper.GetOrAdd(cacheKey, () => base.GetListaRisorse());
        }

        public override void AggiornaRisorsa(string id, string valore)
        {
            base.AggiornaRisorsa(id, valore);

            GetListaRisorse()[RisorseTestualiService.Constants.Prefix + id] = valore;
        }

        private string GetCacheKey()
        {
            return $"risorse-testuali.{this._aliasResolver.AliasComune}.{this._softwareResolver.Software}";
        }
    }
}
