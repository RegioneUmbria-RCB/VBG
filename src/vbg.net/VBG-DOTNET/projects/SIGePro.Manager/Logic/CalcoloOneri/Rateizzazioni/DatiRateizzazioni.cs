using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.SIGePro.Manager.Logic.CalcoloOneri.Rateizzazioni
{
    public class DatiRateizzazione
    {
        public int NumeroRata { get; set; }
        public DateTime? DataScadenza { get; set; }
        public decimal? Prezzo { get; set; }
        public decimal? Interesse { get; set; }
        public decimal? CapitaleResiduo { get; set; }
        public decimal? QuotaCapitale { get; set; }

        public DatiRateizzazione()
        {

        }
    }
}
