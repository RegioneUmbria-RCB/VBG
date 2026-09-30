using Init.Sigepro.FrontEnd.AppLogic.Autenticazione.Vbg;
using Init.Sigepro.FrontEnd.AppLogic.Autenticazione.Vbg.AutenticazioneUtente;
using VBG.Shared.Infrastructure.Caching;
using System;

namespace Init.Sigepro.FrontEnd.WebForms.AppLogic.Autenticazione
{
    internal class ContextAuthenticationDataResolver : IAuthenticationDataResolver
    {
        protected static class Constants
        {
            public const string UserAuthenticationResultItemName = "UserAuthenticationResult";
        }

        private UserAuthenticationResult _datiAutenticazione;
        private readonly IContextCache _cache;

        public ContextAuthenticationDataResolver(IContextCache cache)
        {
            this._cache = cache;
        }


        #region IAuthenticationDataResolver Members

        public UserAuthenticationResult DatiAutenticazione
        {
            get
            {
                if (this._datiAutenticazione != null)
                    return this._datiAutenticazione;

                this._datiAutenticazione = this.GetFromCache();

                if (this._datiAutenticazione == null)
                    throw new InvalidOperationException("UserAuthenticationResult non trovato nel contesto corrente");

                return this._datiAutenticazione;
            }
        }

        private UserAuthenticationResult GetFromCache() => this._cache.GetOrAdd(Constants.UserAuthenticationResultItemName, () => (UserAuthenticationResult)null);

        public bool IsAuthenticated => this.GetFromCache() != null;

        #endregion
    }
}