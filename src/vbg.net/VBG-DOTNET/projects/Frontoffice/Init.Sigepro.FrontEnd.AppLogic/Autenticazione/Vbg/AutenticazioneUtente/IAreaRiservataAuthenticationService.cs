namespace Init.Sigepro.FrontEnd.AppLogic.Autenticazione.Vbg.AutenticazioneUtente
{
    public interface IAreaRiservataAuthenticationService
    {
        void AuthenticateUser(string token);

        void SignOut();

        string GetCurrentUserIdentity();
    }
}
