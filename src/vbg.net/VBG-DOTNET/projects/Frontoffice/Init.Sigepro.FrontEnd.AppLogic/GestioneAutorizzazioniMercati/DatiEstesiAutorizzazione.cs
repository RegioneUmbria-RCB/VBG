using Init.Sigepro.FrontEnd.AppLogic.GestioneAnagrafiche;
using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneAnagrafiche;
using Init.Sigepro.FrontEnd.AppLogic.SalvataggioDomanda;
using System;
using System.Collections.Generic;
using System.Linq;

namespace Init.Sigepro.FrontEnd.AppLogic.GestioneAutorizzazioniMercati
{

    public class DatiEstesiAutorizzazione
    {
        public int Codice { get; set; }
        public string Numero { get; set; }
        public System.DateTime Data { get; set; }
        public DatiEnteAutorizzazione Ente { get; set; }
        public int NumeroPresenze { get; set; }
    }
}
