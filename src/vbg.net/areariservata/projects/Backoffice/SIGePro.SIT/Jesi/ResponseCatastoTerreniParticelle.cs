using Newtonsoft.Json;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.SIGePro.Sit.Jesi
{
    public class ResponseCatastoTerreniParticelle
    {
        [JsonProperty(PropertyName = "foglio")]
        public string Foglio { get; set; }

        [JsonProperty(PropertyName = "numero")]
        public string NumeroParticella { get; set; }

        [JsonProperty(PropertyName = "ettari")]
        public string Ettari { get; set; }

        [JsonProperty(PropertyName = "are")]
        public string Are { get; set; }

        [JsonProperty(PropertyName = "centiare")]
        public string Centiare { get; set; }

        [JsonProperty(PropertyName = "qualita")]
        public string Qualita { get; set; }
    }
}
