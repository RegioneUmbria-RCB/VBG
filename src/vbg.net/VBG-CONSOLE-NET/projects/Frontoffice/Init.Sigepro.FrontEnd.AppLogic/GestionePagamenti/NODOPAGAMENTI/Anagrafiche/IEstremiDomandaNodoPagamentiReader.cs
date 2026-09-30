using Init.Sigepro.FrontEnd.AppLogic.GestionePagamenti.NODOPAGAMENTI.Configurazione;
using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda;

namespace Init.Sigepro.FrontEnd.AppLogic.GestionePagamenti.NODOPAGAMENTI.Anagrafiche
{
    public interface IEstremiDomandaNodoPagamentiReader
    {
        EstremiDomandaNodoPagamenti GetEstremiDomandaDaIdDomanda(int idDomanda, int lastWorkflowStep);
        EstremiDomandaNodoPagamenti GetEstremiDomandaDaDomanda(DomandaOnline domanda, int lastWorkflowStep);
    }
}