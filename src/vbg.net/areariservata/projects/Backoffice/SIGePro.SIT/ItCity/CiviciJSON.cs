using Newtonsoft.Json;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.SIGePro.Sit.ItCity
{
    public class CiviciJSON
    {
        [JsonProperty(PropertyName = "Id")]
        public string Id { get; set; }

        [JsonProperty(PropertyName = "Numero")]
        public string Numero { get; set; }

        [JsonProperty(PropertyName = "Sub")]
        public string Sub { get; set; }

        [JsonProperty(PropertyName = "Indir")]
        public string Indir { get; set; }

        [JsonProperty(PropertyName = "CodQuartiere")]
        public string CodQuartiere { get; set; }

        [JsonProperty(PropertyName = "DesQuartiere")]
        public string DesQuartiere { get; set; }

        [JsonProperty(PropertyName = "Cap")]
        public string Cap { get; set; }

        [JsonProperty(PropertyName = "ZonaAllerta")]
        public string ZonaAllerta { get; set; }

    }
}
