using Newtonsoft.Json;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.SIGePro.Sit.Jesi
{
    public class ResponseJSON<T>
    {
        [JsonProperty(PropertyName = "success")]
        public bool Success { get; set; }

        [JsonProperty(PropertyName = "d")]
        public object[] Dettaglio { get; set; }

        public bool Esito { get; set; }

        public string MessaggioErrore { get; set; }

        public IEnumerable<T> Dati { get; set; }
    }
}
