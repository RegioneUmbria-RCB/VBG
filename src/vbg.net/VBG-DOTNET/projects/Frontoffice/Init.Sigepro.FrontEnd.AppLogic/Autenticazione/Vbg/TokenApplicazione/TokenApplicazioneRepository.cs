using Init.Sigepro.FrontEnd.AppLogic.Configurazione.V2;
using log4net;
using VBG.Shared.Infrastructure.Caching;

namespace Init.Sigepro.FrontEnd.AppLogic.Autenticazione.Vbg.TokenApplicazione
{
    public class TokenApplicazioneRepository
    {
        private readonly ILog _log = LogManager.GetLogger(typeof(TokenApplicazioneRepository));
        private readonly SigeproSecurityProxy _sigeproSecurityProxy;
        private readonly ITimedCache _timedCache;
        private readonly int _cacheTimeout;

        public TokenApplicazioneRepository(SigeproSecurityProxy sigeproSecurityProxy, IConfigurazione<ParametriSigeproSecurity> configurazione, ITimedCache timedCache)
        {
            this._sigeproSecurityProxy = sigeproSecurityProxy;
            this._timedCache = timedCache;
            this._cacheTimeout = configurazione.Parametri.TokenTimeout;
        }


        public string GetTokenByAliasComune(string aliasComune)
        {
            var key = $"TokenApplicazioneCache.{aliasComune}";

            return this._timedCache.GetOrAdd(key, this._cacheTimeout, () =>
            {
                this._log.Debug($"impossibile leggere un token dalla cache http per l'alias {aliasComune}, il token verrà ricaricato");

                var attivo = this._sigeproSecurityProxy.IsComuneAttivo(aliasComune);

                if (!attivo)
                {
                    throw new ComuneNonAttivoException($"Il comune con alias {aliasComune} non è attivo");
                }

                return this._sigeproSecurityProxy.GetApplicationToken(aliasComune);
            });
        }
    }
}
