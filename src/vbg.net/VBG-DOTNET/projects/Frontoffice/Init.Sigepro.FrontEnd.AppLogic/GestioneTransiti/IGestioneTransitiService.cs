using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.Sigepro.FrontEnd.AppLogic.GestioneTransiti
{

    public interface IGestioneTransitiService
    {
        AutorizzazioneTransito TrovaAutorizzazione(string codiceFiscale, string partitaIva, string numeroAutorizzazione, DateTime dataAutorizzazione);
        void SalvaDatiAutorizzazioneTrovata(int idDomanda, DatiAutorizzazioneTrovata datiAutorizzazione);
        RiferimentiPraticaCercata GetRiferimentiPRaticaCercata(int idDomanda);
    }
}
