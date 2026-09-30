using Newtonsoft.Json;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace VBG.Backend.Protocollo.AppLogic.Core.CiviliaNext.CercaPratiche
{
    public class DatiRegistrazioneRepertorio
    {
        [JsonProperty(PropertyName = "dataRegistrazione")]
        public DateTime? DataRegistrazione { get; set; }

        [JsonProperty(PropertyName = "numeroRegistrazione")]
        public long? NumeroRegistrazione { get; set; }

        [JsonProperty(PropertyName = "idRegistroRepertorio")]
        public long? IdRegistroRepertorio { get; set; }

        [JsonProperty(PropertyName = "codiceRegistroRepertorio")]
        public string CodiceRegistroRepertorio { get; set; }

        [JsonProperty(PropertyName = "nomeRegistroRepertorio")]
        public string NomeRegistroRepertorio { get; set; }

    }
}
