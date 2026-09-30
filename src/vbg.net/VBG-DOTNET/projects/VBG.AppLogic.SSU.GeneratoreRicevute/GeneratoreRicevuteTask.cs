using Microsoft.AspNetCore.SignalR;
using VBG.AppLogic.SSU.GeneratoreRicevute.DataAccess;
using VBG.AppLogic.SSU.GeneratoreRicevute.GenerazioneRicevuta;
using VBG.AppLogic.SSU.GeneratoreRicevute.Hubs;
using VBG.AppLogic.SSU.HubInterfaces;

namespace VBG.AppLogic.SSU.GeneratoreRicevute
{
    public class GeneratoreRicevuteTask
    {
        private readonly IServiceProvider _serviceProvider;
        private readonly IDomandeSsuDefaultAliasRepository _domandeSsuDefaultAlias;
        private readonly IHubContext<DomandeNotificationHub, IDomandeNotificationClient> _hubContext;
        private readonly ILogger<GeneratoreRicevuteTask> _logger;

        public GeneratoreRicevuteTask(
            IServiceProvider serviceProvider,
            IDomandeSsuDefaultAliasRepository domandeSsuDefaultAlias,
            IHubContext<DomandeNotificationHub, IDomandeNotificationClient> hubContext,
            ILogger<GeneratoreRicevuteTask> logger)
        {
            this._serviceProvider = serviceProvider;
            this._domandeSsuDefaultAlias = domandeSsuDefaultAlias;
            this._hubContext = hubContext;
            this._logger = logger;
        }

        public async Task RunAsync()
        {
            this._logger.LogDebug("Generatore running");
            await this._hubContext.Clients.All.SendGeneratoreStatusAsync(true);

            var domande = this._domandeSsuDefaultAlias.GetDomandeElaborabili();
            await this._hubContext.Clients.All.SendNumeroDomandeDaElaborareAsync(domande.Count);

            this._logger.LogDebug("Generatore ha trovato {count} domande da elaborare", domande.Count);

            foreach (var domanda in domande)
            {
                this._logger.LogInformation("Elaborazione della domanda {@alias}-{@software}-{@idDomanda}", domanda.Alias, domanda.Software, domanda.FkIdDomanda);

                using var scope = this._serviceProvider.CreateScope();
                var generatore = scope.ServiceProvider.GetRequiredService<GeneratoreRicevutaScoped>();

                await generatore.GeneraRicevutaAsync(domanda.Alias, domanda.Software, domanda.FkIdDomanda);
            }
        }
    }
}
