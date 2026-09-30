using Microsoft.AspNetCore.SignalR;
using VBG.AppLogic.SSU.HubInterfaces;

namespace VBG.AppLogic.SSU.GeneratoreRicevute.Hubs
{
    public sealed class DomandeNotificationHub : Hub<IDomandeNotificationClient>
    {
        public async Task SendDomandaElaborabileAsync(string idComune, int idDomanda, bool isElaborabile)
        {
            await Clients.All.SendDomandaElaborabileAsync(idComune, idDomanda, isElaborabile);
        }

        public async Task SendGeneratoreStatusAsync(bool isRunning)
        {
            await Clients.All.SendGeneratoreStatusAsync(isRunning);
        }

        public async Task SendNumeroDomandeDaElaborareAsync(int n)
        {
            await Clients.All.SendNumeroDomandeDaElaborareAsync(n);
        }

        public async Task SendNuovoErroreDomandaAsync(string idComune, int idDomanda)
        {
            await Clients.All.SendNuovoErroreDomandaAsync(idComune, idDomanda);
        }

        public async Task SendNuovoStatoDomandaAsync(string idComune, int idDomanda, int vecchioStato, int nuovoStato)
        {
            await Clients.All.SendNuovoStatoDomandaAsync(idComune, idDomanda, vecchioStato, nuovoStato);
        }
    }
}
