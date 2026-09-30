using Init.Sigepro.FrontEnd.AppLogic.Autenticazione.Vbg.AutenticazioneUtente;
using Init.Sigepro.FrontEnd.AppLogic.GestioneRisorseTestuali;
using VBG.BlazorComponentsLibrary.RisorseTestuali;

namespace Init.Sigepro.FrontEnd.CoreServices.Shared
{
    public class RisorseTestualiService : ISharedComponentsRisorseService
    {
        private readonly IAuthenticationDataResolver _authenticationDataResolver;
        private readonly IRisorseTestualiService _risorseTestualiService;

        public RisorseTestualiService(IAuthenticationDataResolver authenticationDataResolver, IRisorseTestualiService risorseTestualiService)
        {
            this._authenticationDataResolver = authenticationDataResolver;
            this._risorseTestualiService = risorseTestualiService;
        }

        public bool UtenteCorrentePuoModificareRisorsa => this._authenticationDataResolver.DatiAutenticazione?.DatiUtente?.UtenteTester ?? false;

        public Task<string> GetTestoRisorsaAsync(string idRisorsa)
        {
            var valore = this._risorseTestualiService.GetRisorsa(idRisorsa);

            return Task.FromResult(valore);
        }

        public Task<string> ImpostaValoreRisorsaAsync(string idRisorsa, string nuovoValore)
        {
            this._risorseTestualiService.AggiornaRisorsa(idRisorsa, nuovoValore);

            return Task.FromResult(nuovoValore);
        }
    }
}
