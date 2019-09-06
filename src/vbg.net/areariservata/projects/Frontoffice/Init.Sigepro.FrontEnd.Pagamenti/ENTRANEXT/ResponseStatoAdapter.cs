using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.Sigepro.FrontEnd.Pagamenti.ENTRANEXT
{
    public class ResponseStatoAdapter
    {
        string _codiceErrore;
        IEnumerable<KeyValuePair<string, string>> Errori;

        public ResponseStatoAdapter(string codiceErrore)
        {
            this._codiceErrore = codiceErrore;

            Errori.ToList().Add(new KeyValuePair<string, string>("OK", "Elaborazione terminata con successo"));
            Errori.ToList().Add(new KeyValuePair<string, string>("FO_RICHIEDENTE_ERRATO", "Identificativo richiedente non valido"));
            Errori.ToList().Add(new KeyValuePair<string, string>("O_SYSTEM_ERROR", "Errore generico"));
            Errori.ToList().Add(new KeyValuePair<string, string>("FO_AUTENTICAZIONE", "Errore di autenticazione"));
            Errori.ToList().Add(new KeyValuePair<string, string>("FO_ELABORAZIONE_PARZIALE", "Errore restituito nel caso in cui le richieste da elaborare non siano state tutte completate (risultato accettato solo nel metodo InviaPagamenti"));
            Errori.ToList().Add(new KeyValuePair<string, string>("FO_AUTENTICAZIONE_SCADUTA", "Il ticket utilizzato non è più valido"));
            Errori.ToList().Add(new KeyValuePair<string, string>("FO_DIRITTI_INSUFFICIENTI", "Diritti non sufficienti per utilizzare la funzione"));
        }

        public KeyValuePair<string, string> GetStato()
        {
            return Errori.Where(x => x.Key == this._codiceErrore).First();
        }
    }
}
