using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneAnagrafiche;
using System.Threading.Tasks;

namespace Init.Sigepro.FrontEnd.AppLogic.GestioneAnagrafiche.RicercaAnagrafiche
{
    public interface IRicercheAnagraficheService
    {
        AnagraficaDomanda RicercaAnagrafica(int idDomanda, TipoPersonaEnum tipoPersona, string codiceFiscalePartitaIva, bool ignoraRicercaBackofficePerPersoneFisiche);
        Task<AnagraficaDomanda> RicercaAnagraficaAsync(int idDomanda, TipoPersonaEnum tipoPersona, string codiceFiscalePartitaIva, bool ignoraRicercaBackofficePerPersoneFisiche);
    }
}