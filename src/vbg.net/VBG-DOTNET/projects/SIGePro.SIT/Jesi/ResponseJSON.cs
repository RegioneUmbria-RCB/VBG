using System;
using System.Collections.Generic;
using System.Text.Json.Serialization;

namespace Init.SIGePro.Sit.Jesi
{
    [Obsolete]
    public class ResponseJSON<T>
    {
        [JsonPropertyName("success")]
        public bool Success { get; set; }

        [JsonPropertyName("d")]
        public object[] Dettaglio { get; set; }

        public bool Esito { get; set; }

        public string MessaggioErrore { get; set; }

        public IEnumerable<T> Dati { get; set; }
    }
}
