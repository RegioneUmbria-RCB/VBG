using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.Sigepro.FrontEnd.AppLogic.GestioneTransiti
{
    public interface IAutorizzazioniTransitiProxy
    {
        AutorizzazioneTransito TrovaAutorizzazione(string codiceFiscale, string partitaIva, string numeroAutorizzazione, DateTime dataAutorizzazione);
    }
}
