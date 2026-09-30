using Init.Sigepro.FrontEnd.AppLogic.Configurazione.V2;
using Init.Sigepro.FrontEnd.Infrastructure.UrlsAndPaths;

namespace Init.Sigepro.FrontEnd.AppLogic.Autenticazione.Vbg.AutenticazioneUtente
{

    public class AuthenticationUrlBuilder : IAuthenticationUrlBuilder
    {
        private readonly IConfigurazione<ParametriLogin> _configurazione;

        public AuthenticationUrlBuilder(IConfigurazione<ParametriLogin> configurazione)
        {
            this._configurazione = configurazione;
        }

        public string BuildAuthenticationUrl(string idComune, string software, string returnTo)
        {
            return UrlBuilder.Url(this._configurazione.Parametri.UrlLogin, x =>
            {
                x.Add("idcomunealias", idComune);
                x.Add("software", software);
                x.Add("contesto", "UTE");
                x.Add("return_to", returnTo);
            });
        }
    }
}
