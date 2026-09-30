using Init.Sigepro.FrontEnd.AppLogic.Autenticazione.Vbg.TokenApplicazione;
using Init.Sigepro.FrontEnd.CoreServices.Autenticazione;

namespace VBG.AppLogic.SSU.GeneratoreRicevute.GenerazioneRicevuta
{
    public class GeneratoreRicevutaScoped
    {
        private readonly IServiceProvider _serviceProvider;

        public GeneratoreRicevutaScoped(IServiceProvider serviceProvider)
        {
            this._serviceProvider = serviceProvider;
        }

        public async Task GeneraRicevutaAsync(string alias, string software, int idDomanda)
        {
            using var scope = this._serviceProvider.CreateScope();  // Creo uno scope separato per utilizzare un altro alias/software

            var aliasSoftwareProvider = scope.ServiceProvider.GetRequiredService<AliasSoftwareProvider>();
            aliasSoftwareProvider.AliasComune = alias;
            aliasSoftwareProvider.Software = software;

            var tokenService = scope.ServiceProvider.GetRequiredService<ITokenApplicazioneService>();
            var tokenProvider = scope.ServiceProvider.GetRequiredService<TokenProvider>();

            tokenProvider.Token = tokenService.GetToken(alias);  // Ottengo un token valido per l'alias/software specificato

            var generatoreRicevuta = scope.ServiceProvider.GetRequiredService<GenerazioneRicevutaService>();

            await generatoreRicevuta.ElaboraPraticaAsync(idDomanda);
        }
    }
}
