using log4net;
using VBG.Shared.Infrastructure.Caching;

namespace Init.Sigepro.FrontEnd.AppLogic.Autenticazione.Vbg.TraduzioneIdComune
{
    public class AliasToIdComuneRepository
    {
        private readonly SigeproSecurityProxy _sigeproSecurityProxy;
        private readonly ITimedCache _timedCache;
        private readonly ILog _log = LogManager.GetLogger(typeof(AliasToIdComuneRepository));

        public AliasToIdComuneRepository(SigeproSecurityProxy sigeproSecurityProxy, ITimedCache timedCache)
        {
            this._sigeproSecurityProxy = sigeproSecurityProxy;
            this._timedCache = timedCache;
        }

        public string GetIdComuneDaAliasComune(string aliasComune)
        {
            var cacheKey = "AliasComuneToIdComune." + aliasComune;
            var timeout = 24 * 60; //La cache dura un giorno

            return this._timedCache.GetOrAdd(cacheKey, timeout, () =>
            {
                this._log.Debug($"L'id comune per l'alias {aliasComune} non è stato trovato in cache e verrà riletto");

                return this.LeggiIdComune(aliasComune);
            });
        }

        private string LeggiIdComune(string aliasComune)
        {
            var token = this._sigeproSecurityProxy.GetApplicationToken(aliasComune);
            var appInfo = this._sigeproSecurityProxy.CheckToken(token);

            return appInfo.tokenInfo.idcomune;
        }
    }
}
