using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda;
using System.Collections.Generic;

namespace Init.Sigepro.FrontEnd.AppLogic.DatiDinamici.SchedeCollegate
{
    public interface ISchedeCollegateService
    {
        /// <summary>
        /// Contrassegna tutte le schede già compilate collegate alla scheda passata come non compilate.
        /// Non salva i dati della domanda
        /// </summary>
        /// <param name="domanda"></param>
        /// <param name="idModello"></param>
        /// <returns>Lista di id di schede che devono essere ricompilate</returns>
        IEnumerable<int> MarcaSchedeCollegateComeNonCompilate(DomandaOnline domanda, int idModello);
    }
}
