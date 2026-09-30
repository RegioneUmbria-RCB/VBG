namespace Init.Sigepro.FrontEnd.AppLogic.Autenticazione.Vbg.AutenticazioneUtente
{
    public interface IVbgAuthenticationService
    {
        UserAuthenticationResult? CheckToken(string token);
        UserAuthenticationResult? CheckTokenIgnoreCache(string token);
        string GetTokenPartnerApp(string token);
        void LoginAnonimo(string alias);
        void Logout(string token);
    }
}