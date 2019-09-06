using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.SIGePro.Manager.Logic.AidaSmart.GestioneStradario
{
    public class SdeProxyStradarioDto
    {
        public class SdeProxyStradarioId
        {
            public string idcomune { get; set; }
            public int codice { get; set; }
        }

        public SdeProxyStradarioId Id { get; set; }
        public string descrizione { get; set; }
        public string prefisso { get; set; }
        public string codviario { get; set; }
    }
}
