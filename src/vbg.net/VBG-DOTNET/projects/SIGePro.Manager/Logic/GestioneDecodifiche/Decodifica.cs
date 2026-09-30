using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.SIGePro.Manager.Logic.GestioneDecodifiche
{
    public class DecodificaDTO
    {
        public string Idcomune { get; set; }
        public string Tabella { get; set; }
        public string Chiave { get; set; }
        public string Valore { get; set; }
        public bool FlgDisabilitato { get; set; }
        public string Raggruppamento { get; set; }
        public int? Ordine { get; set; }
    }
}
