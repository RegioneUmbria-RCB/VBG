namespace Init.Sigepro.FrontEnd.AppLogic.Autenticazione.Vbg.AutenticazioneUtente
{
    public interface IAuthenticationDataResolver
    {
        UserAuthenticationResult DatiAutenticazione { get; }
        bool IsAuthenticated { get; }
    }
}
