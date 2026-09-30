using Init.Sigepro.FrontEnd.AppLogic.Autenticazione.Vbg.AutenticazioneUtente;

namespace Init.Sigepro.FrontEnd.CoreServices.Autenticazione
{
    public class AreaRiservataAuthenticationService : IAreaRiservataAuthenticationService
    {
        private string _token = null;

        public string Token => this._token;

        public void AuthenticateUser(string token)
        {
            this._token = token;
        }

        public string GetCurrentUserIdentity()
        {
            return this._token;
        }

        public void SignOut()
        {
            this._token = null;
        }
    }
}
