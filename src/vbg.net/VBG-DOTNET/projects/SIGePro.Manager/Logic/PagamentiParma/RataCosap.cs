using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.SIGePro.Manager.Logic.PagamentiParma
{
    public class RataCosap
    {
        public readonly decimal Importo;
        public readonly DateTime DataScadenza;
        //public readonly int AnnoRiferimento;

        public RataCosap(DateTime dataScadenza, decimal importo/*, int annoRiferimento*/)
        {
            this.Importo = importo;
            this.DataScadenza = dataScadenza;
            //this.AnnoRiferimento = annoRiferimento;
        }
    }
}
