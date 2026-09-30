using Init.Sigepro.FrontEnd.AppLogic.Autenticazione.Vbg;
using Init.Sigepro.FrontEnd.AppLogic.Autenticazione.Vbg.AutenticazioneUtente;

namespace Init.Sigepro.FrontEnd.CoreServices.Autenticazione
{
    public class AuthenticationDataResolver : IAuthenticationDataResolver, IAuthenticationDataStore
    {
        protected static class Constants
        {
            public const string UserAuthenticationResultItemName = "UserAuthenticationResult";
        }

        private UserAuthenticationResult? _datiAutenticazione;

        public AuthenticationDataResolver()
        {
        }


        #region IAuthenticationDataResolver Members

        public UserAuthenticationResult DatiAutenticazione
        {
            get
            {
                if (this._datiAutenticazione != null)
                    return this._datiAutenticazione;

                if (this._datiAutenticazione == null)
                    throw new InvalidOperationException("UserAuthenticationResult non trovato nel contesto corrente");

                return this._datiAutenticazione;
            }
        }

        public bool IsAuthenticated => this._datiAutenticazione != null;

        public void Save(UserAuthenticationResult userAuthenticationResult)
        {
            this._datiAutenticazione = userAuthenticationResult;
        }

        public void LogOut()
        {
            this._datiAutenticazione = null;
        }

        #endregion
    }
}
