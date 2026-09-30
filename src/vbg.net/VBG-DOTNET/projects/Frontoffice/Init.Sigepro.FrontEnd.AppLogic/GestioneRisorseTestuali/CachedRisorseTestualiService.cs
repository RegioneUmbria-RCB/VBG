using Init.Sigepro.FrontEnd.AppLogic.Autenticazione.Vbg.AutenticazioneUtente;
using Init.Sigepro.FrontEnd.AppLogic.Common;
using VBG.Shared.Infrastructure.Caching;
using System;
using System.Collections.Generic;

namespace Init.Sigepro.FrontEnd.AppLogic.GestioneRisorseTestuali
{
    public class CachedRisorseTestualiService : RisorseTestualiService
    {
        private readonly IAliasSoftwareResolver _aliasSoftwareResolver;
        private readonly IApplicationCache _cache;

        public CachedRisorseTestualiService(RisorseTestualiServiceCreator serviceCreator, IAliasSoftwareResolver aliasSoftwareResolver, IAuthenticationDataResolver authDataResolver, IApplicationCache webCache)
            : base(serviceCreator, aliasSoftwareResolver, authDataResolver)
        {
            this._aliasSoftwareResolver = aliasSoftwareResolver;
            this._cache = webCache;
        }

        public override Dictionary<string, string> GetListaRisorse()
        {
            var cacheKey = this.GetCacheKey();

            return this._cache.GetOrAdd(cacheKey, () => base.GetListaRisorse());
        }

        public override void AggiornaRisorsa(string id, string valore)
        {
            var key = RisorseTestualiService.Constants.Prefix + id;

            base.AggiornaRisorsa(id, valore);

            if (String.IsNullOrEmpty(valore))
            {
                this.GetListaRisorse().Remove(key);
                return;
            }

            this.GetListaRisorse()[key] = valore;
        }

        private string GetCacheKey()
        {
            return $"risorse-testuali.{this._aliasSoftwareResolver.AliasComune}.{this._aliasSoftwareResolver.Software}";
        }
    }
}
