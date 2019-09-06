using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.SIGePro.Protocollo.Insiel3.Protocollazione.MittentiDestinatari
{
    public class TipoGestioneAnagraficaEnum
    {
        /// <summary>
        /// PEC: Aggiunge alla descrizione la pec, va a fare ricerche per descrizione, se non presenta ne inserisce una nuova.
        /// CODICE_FISCALE: Aggiunge il codice fiscale alla descrizione, va a cercare per descrizione e se non presente ne inserisce una nuova, se l'anagrafica viene trovata ma la pec manca o non è presente quella passata, aggiorna l'anagrafica aggiungendo il nuovo indirizzo pec.
        /// NORMALE: Non aggiunge niente al nominativo, ricerca per descrizione e se non presente ne inserisce una nuova, se l'anagrafica viene trovata ma la pec manca o non è presente quella passata, aggiorna l'anagrafica aggiungendo il nuovo indirizzo pec.
        /// NO_PEC: se impostato indica al web service che non deve inviare la pec.
        /// MONFALCONE: scrive la denominazione con le seguenti regole definite dal comune di monfalcone <COGNOME> <NOME> <LOCALITA_RESIDENZA> (<LOCALITA_SEDE_RESIDENZA> se azienda), se la località coincide con una provincia allora va indicata la sigla, quindi <COGNOME> <NOME> <SIGLAPROVINCIA>, se la località coincide con MONFALCONE allora va indicato il valore CITTA'', quindi <COGNOME> <NOME> CITTA
        /// </summary>
        public enum TipoGestione { PEC, CODICE_FISCALE, NORMALE, NO_PEC, MONFALCONE };

    }
}
