using AreaRiservataCore.Pages.MiePratiche.Componenti;
using Init.Sigepro.FrontEnd.AppLogic.Autenticazione.Vbg.AutenticazioneUtente;
using Init.Sigepro.FrontEnd.AppLogic.Common;
using Init.Sigepro.FrontEnd.AppLogic.WebServiceReferences.IstanzeService;

namespace AreaRiservataCore.Pages.MiePratiche
{
    public class RichiestaListaPraticheService
    {
        private readonly IAuthenticationDataResolver _authenticationDataResolver;

        private readonly ISoftwareResolver _softwareResolver;

        public RichiestaListaPraticheService(IAuthenticationDataResolver authenticationDataResolver, ISoftwareResolver softwareResolver)
        {
            this._authenticationDataResolver = authenticationDataResolver;
            this._softwareResolver = softwareResolver;
        }

        public RichiestaListaPraticheV3 ConfiguraDatiRichiesta(ViewMode mode, RichiestaListaPraticheV3 richiesta)
        {
            richiesta.Software = this._softwareResolver.Software;

            if (mode == ViewMode.IstanzePresentate)
            {
                richiesta.PersonaAventeTitolo = new FiltroPersonaAventeTitoloDiVisura
                {
                    CodiceFiscale = this._authenticationDataResolver.DatiAutenticazione.DatiUtente.Codicefiscale
                };
            }

            return richiesta;
        }
    }
}
