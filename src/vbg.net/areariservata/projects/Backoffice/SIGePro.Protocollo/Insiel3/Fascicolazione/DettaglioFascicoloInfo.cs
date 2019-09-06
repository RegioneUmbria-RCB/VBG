using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.SIGePro.Protocollo.Insiel3.Fascicolazione
{
    public class DettaglioFascicoloInfo
    {
        public string Numero { get; private set; }
        public string Anno { get; private set; }
        public string CodiceRegistro { get; private set; }
        public string CodiceUfficio { get; private set; }

        public DettaglioFascicoloInfo(string numeroFascicolo, string annoFascicolo, string codiceRegistro, string codiceUfficio)
        {
            this.Numero = numeroFascicolo;
            this.Anno = annoFascicolo;
            this.CodiceRegistro = codiceRegistro;
            this.CodiceUfficio = codiceUfficio;
        }
    }
}
