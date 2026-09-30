namespace Init.Sigepro.FrontEnd.AppLogic.Autenticazione.Vbg.AutenticazioneUtente
{
    public interface IAuthenticationUrlBuilder
    {
        string BuildAuthenticationUrl(string idComune, string software, string returnTo);
    }
}
