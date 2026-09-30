using Newtonsoft.Json;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace VBG.Backend.Protocollo.AppLogic.Core.CiviliaNext.CercaPratiche
{
    public class Comune
    {
        [JsonProperty(PropertyName = "descrizione")]
        public string Descrizione { get; set; }

        [JsonProperty(PropertyName = "codiceIstat")]
        public string CodiceIstat { get; set; }

        [JsonProperty(PropertyName = "codiceFiscale")]
        public string CodiceFiscale { get; set; }

        [JsonProperty(PropertyName = "siglaProvincia")]
        public string SiglaProvincia { get; set; }

        [JsonProperty(PropertyName = "descrizioneTraslitterata")]
        public string DescrizioneTraslitterata { get; set; }

        [JsonProperty(PropertyName = "idEnte")]
        public long? IdEnte { get; set; }

        [JsonProperty(PropertyName = "codiceRegione")]
        public string CodiceRegione { get; set; }

        [JsonProperty(PropertyName = "id")]
        public long? Id { get; set; }
    }
}
