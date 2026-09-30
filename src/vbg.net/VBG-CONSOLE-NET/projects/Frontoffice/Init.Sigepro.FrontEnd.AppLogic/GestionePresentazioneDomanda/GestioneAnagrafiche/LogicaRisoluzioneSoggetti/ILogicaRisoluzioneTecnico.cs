using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneAnagrafiche.LogicaRisoluzioneSoggetti
{
    public interface ILogicaRisoluzioneTecnico
    {
        AnagraficaDomanda Risolvi(IEnumerable<AnagraficaDomanda> anagrafichedomanda);
    }
}
