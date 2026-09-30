using System.Collections.Generic;

namespace Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneAnagrafiche.LogicaRisoluzioneSoggetti
{
    public interface ILogicaRisoluzioneTecnico
    {
        AnagraficaDomanda? Risolvi(IEnumerable<AnagraficaDomanda> anagrafichedomanda);
    }
}
