using Newtonsoft.Json;
using System.Collections.Generic;

namespace Init.SIGePro.Sit.ItCity
{
    public class ResponseCivici
    {
        public bool Esito { get; set; }

        public string MessaggioErrore { get; set; }

        public List<CiviciJSON> Dati { get; set; }
    }
}
