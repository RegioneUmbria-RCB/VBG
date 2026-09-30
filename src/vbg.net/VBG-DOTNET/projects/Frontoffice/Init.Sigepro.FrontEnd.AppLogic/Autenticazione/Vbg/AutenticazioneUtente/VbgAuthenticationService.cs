using Init.Sigepro.FrontEnd.AppLogic.Adapters;
using Init.Sigepro.FrontEnd.AppLogic.Configurazione.V2;
using Init.Sigepro.FrontEnd.AppLogic.GestioneAnagrafiche.Backend;
using Init.Sigepro.FrontEnd.AppLogic.GestioneComuni;
using Init.Sigepro.FrontEnd.AppLogic.SigeproSecurityService;
using log4net;
using System;
using VBG.Shared.Infrastructure.Caching;

namespace Init.Sigepro.FrontEnd.AppLogic.Autenticazione.Vbg.AutenticazioneUtente
{
    public class VbgAuthenticationService : IVbgAuthenticationService
    {

        private readonly IAnagraficheBackendService _anagraficheService;
        private readonly IConfigurazione<ParametriLogin> _configurazione;
        private readonly SigeproSecurityProxy _sigeproSecurityProxy;
        private readonly IAreaRiservataAuthenticationService _areaRiservataAuthenticationService;
        private readonly ITimedCache _timedCache;
        private readonly IComuniService _comuniService;
        private readonly ILog _log = LogManager.GetLogger(typeof(VbgAuthenticationService));
        private readonly int _timeout;


        public VbgAuthenticationService(IAnagraficheBackendService anagraficheService, IConfigurazione<ParametriLogin> configurazione, SigeproSecurityProxy sigeproSecurityProxy, IConfigurazione<ParametriSigeproSecurity> parametriSigeproSecurity, IAreaRiservataAuthenticationService areaRiservataAuthenticationService, ITimedCache timedCache, IComuniService comuniService)
        {
            this._anagraficheService = anagraficheService;
            this._configurazione = configurazione;
            this._sigeproSecurityProxy = sigeproSecurityProxy;
            this._areaRiservataAuthenticationService = areaRiservataAuthenticationService;
            this._timedCache = timedCache;
            this._comuniService = comuniService;
            this._timeout = parametriSigeproSecurity.Parametri.TokenTimeout;
        }


        private string GetCacheKey(string token)
        {
            return $"UserTokenCache.{token}";
        }

        //public UserAuthenticationResult CheckToken(string token)
        //{
        //    var key = this.GetCacheKey(token);

        //    return this._timedCache.GetOrAdd(key, this._timeout, () =>
        //    {
        //        this._log.DebugFormat("il token utente {0} non è stato trovato in cache e verrà riletto dal servizio sigeprosecurity", token);

        //        return this.LeggiDatiToken(token);
        //    });
        //}

        public UserAuthenticationResult? CheckTokenIgnoreCache(string token)
        {
            var key = this.GetCacheKey(token);

            this._timedCache.Remove(key);

            return this.CheckToken(token);
        }

        public UserAuthenticationResult? CheckToken(string token)
        {
            var key = this.GetCacheKey(token);

            return this._timedCache.GetOrAdd(key, this._timeout, () =>
            {
                this._log.DebugFormat("il token utente {0} non è stato trovato in cache e verrà riletto dal servizio sigeprosecurity", token);

                return this.LeggiDatiToken(token);
            });
        }

        public void Logout(string token)
        {
            this._log.DebugFormat("Invalido il token {0}", token);

            this._areaRiservataAuthenticationService.SignOut();

            this._timedCache.Remove(this.GetCacheKey(token));
            this._sigeproSecurityProxy.Logout(token);
        }

        private UserAuthenticationResult? LeggiDatiToken(string token)
        {
            if (String.IsNullOrEmpty(token))
            {
                return null;
            }

            var result = this._sigeproSecurityProxy.CheckToken(token);

            if (!result.valid)
                return null;

            if (result.tokenInfo.contesto == ContestoType.APP)
            {
                // Token di operatore del back, probabilmente viene usato per rigenerare un certificato di invio.
                // Lo tratto come se fosse un utente anonimo ma senza un'anagrafica collegata
                return UserAuthenticationResult.Anonimo(token, result.tokenInfo.alias, result.tokenInfo.idcomune);
            }

            var datiAnagrafici = this.GetDatiAnagraficiByResult(result);

            if (datiAnagrafici == null)
            {
                this._log.ErrorFormat("Non è stato possibile leggere i dati dell'anagrafica con codice {0}", result.tokenInfo.userid);

                return null;
            }

            var anagraficaAdattata = new AnagrafeAdapter(datiAnagrafici, this._comuniService).ToAnagraficaUtente();

            var livelloAutenticazione = this._sigeproSecurityProxy.GetLivelloAutenticazione(token);

            return new UserAuthenticationResult(token, result.tokenInfo.alias, result.tokenInfo.idcomune, anagraficaAdattata!, livelloAutenticazione);
        }

        private VBG.Frontend.AppLogic.WsAnagraficheService.Anagrafe? GetDatiAnagraficiByResult(CheckTokenResponse result)
        {
            if (result.tokenInfo.contesto == ContestoType.UTEG)      // il login è stato effettuato da una persona giuridica
            {
                return this._anagraficheService.GetPersonaGiuridicaByUserId(result.tokenInfo.userid);
            }

            return this._anagraficheService.GetPersonaFisicaByUserId(result.tokenInfo.userid);
        }

        public string GetTokenPartnerApp(string token)
        {
            return this._sigeproSecurityProxy.GetTokenPartnerApp(token);
        }

        public void LoginAnonimo(string alias)
        {
            var user = this._configurazione.Parametri.UsernameUtenteAnonimo;
            var pass = this._configurazione.Parametri.PasswordUtenteAnonimo;

            if (String.IsNullOrEmpty(user))
            {
                throw new Exception("Username per l'utente anonimo non valido");
            }

            var tokenAnonimo = this._sigeproSecurityProxy.GetTokenAnonimo(alias, user, pass);

            this._areaRiservataAuthenticationService.AuthenticateUser(tokenAnonimo);
        }
    }
}
