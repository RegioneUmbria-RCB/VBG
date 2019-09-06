using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.Sigepro.FrontEnd.GestioneMovimenti.GestioneMovimento
{
    /// <summary>
    /// Riferimenti dell'istanza collegata ad un movimento
    /// </summary>
    public class RiferimentiIstanza
    {
        public string IdComune { get; set; }
        public int CodiceIstanza { get; set; }
        public string NumeroIstanza { get; set; }
        public DateTime DataIstanza { get; set; }
        public DatiProtocolloMovimento Protocollo { get; set; }
    }
}
