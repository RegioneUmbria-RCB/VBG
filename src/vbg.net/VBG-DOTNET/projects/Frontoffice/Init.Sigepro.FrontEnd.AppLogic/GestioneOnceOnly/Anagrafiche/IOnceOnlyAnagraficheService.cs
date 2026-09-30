using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda;
using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneAnagrafiche;
using System.Threading.Tasks;
using VBG.Frontend.AppLogic.WsAnagraficheService;

namespace Init.Sigepro.FrontEnd.AppLogic.GestioneOnceOnly.Anagrafiche
{
    public interface IOnceOnlyAnagraficheService : IOnceOnlyService
    {
        Task SalvaSoggettiDomandaAsync(IDomandaOnlineReadInterface domanda);
        Task<Anagrafe> TrovaAnagraficaByCodiceFiscaleAsync(TipoPersonaEnum tipoPersona, string codiceFiscale);
        Task<ElementoRubricaOnceOnly[]> GetAnagraficheInRubricaAsync(TipoPersonaEnum tipoPersona);
    }
}
