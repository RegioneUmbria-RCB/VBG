using Newtonsoft.Json;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.SIGePro.Sit.Jesi
{
    public class ResponseCatastoUrbanoJSON
    {
        [JsonProperty(PropertyName = "foglio")]
        public string Foglio { get; set; }

        [JsonProperty(PropertyName = "numero")]
        public string NumeroParticella { get; set; }

        [JsonProperty(PropertyName = "idstrada")]
        public string CodiceVia { get; set; }

        [JsonProperty(PropertyName = "denominazione")]
        public string DenominazioneVia { get; set; }

        [JsonProperty(PropertyName = "ncivico")]
        public string NumeroCivico { get; set; }

        [JsonProperty(PropertyName = "esponente")]
        public string Esponente { get; set; }
    }
}
