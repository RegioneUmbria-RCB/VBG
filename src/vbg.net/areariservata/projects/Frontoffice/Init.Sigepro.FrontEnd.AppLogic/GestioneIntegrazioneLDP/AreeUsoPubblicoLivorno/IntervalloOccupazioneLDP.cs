using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.Sigepro.FrontEnd.AppLogic.GestioneIntegrazioneLDP.AreeUsoPubblicoLivorno
{
    public class IntervalloOccupazioneLDP
    {
        public readonly DateTime Inizio;
        public readonly DateTime Fine;
        public readonly string Descrizione;

        public IntervalloOccupazioneLDP(DateTime inizio, DateTime fine, string descrizione)
        {
            this.Inizio = inizio;
            this.Fine = fine;
            this.Descrizione = descrizione;
        }
    }
}
