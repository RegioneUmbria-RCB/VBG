using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.Sigepro.FrontEnd.AppLogic.GestioneIntegrazioneLDP.AreeUsoPubblicoLivorno
{
    public class DatiOccupazioneLDP
    {
        public readonly string StringaRipetizioni;
        public readonly IEnumerable<IntervalloOccupazioneLDP> IntervalloOccupazione;

        public DatiOccupazioneLDP(string stringaRipetizioni, IEnumerable<IntervalloOccupazioneLDP> intervalloOccupazione)
        {
            this.StringaRipetizioni = stringaRipetizioni;
            this.IntervalloOccupazione = intervalloOccupazione;
        }
    }
}
