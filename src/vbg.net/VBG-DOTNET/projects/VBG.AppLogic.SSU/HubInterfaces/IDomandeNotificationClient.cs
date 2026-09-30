
using VBG.AppLogic.SSU.DataAccess.Dto;

namespace VBG.AppLogic.SSU.HubInterfaces
{
    public interface IDomandeNotificationClient
    {
        Task SendDomandaElaborabileAsync(string idComune, int idDomanda, bool isElaborabile);
        Task SendGeneratoreStatusAsync(bool isRunning);
        Task SendNumeroDomandeDaElaborareAsync(int n);
        Task SendNuovoErroreDomandaAsync(string idComune, int idDomanda);
        Task SendNuovoStatoDomandaAsync(string idComune, int idDomanda, int vecchioStato, int nuovoStato);
    }
}
