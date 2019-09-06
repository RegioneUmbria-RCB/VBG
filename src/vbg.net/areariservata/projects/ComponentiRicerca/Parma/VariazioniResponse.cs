using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Parma
{
    public class VariazioniResponse : AnagrafeResponse
    {
        public string tipo_variazione { get; set; }
        public string descrizione_variazione { get; set; }
        public DateTime data_evento { get; set; }

        public VariazioniResponse() : base()
        {

        }
    }
}
