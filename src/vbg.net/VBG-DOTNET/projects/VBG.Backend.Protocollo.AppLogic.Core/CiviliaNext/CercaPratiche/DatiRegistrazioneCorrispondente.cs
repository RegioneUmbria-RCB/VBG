using Newtonsoft.Json;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace VBG.Backend.Protocollo.AppLogic.Core.CiviliaNext.CercaPratiche
{
    public class DatiRegistrazioneCorrispondente
    {
        [JsonProperty(PropertyName = "tipoProtocollo")]
        public string TipoProtocollo { get; set; }

        [JsonProperty(PropertyName = "dataProtocollo")]
        public DateTime? DataProtocollo { get; set; }

        [JsonProperty(PropertyName = "numeroProtocollo")]
        public long? NumeroProtocollo { get; set; }

        [JsonProperty(PropertyName = "idRegistroProtocollo")]
        public long? IdRegistroProtocollo { get; set; }

        [JsonProperty(PropertyName = "codiceRegistroProtocollo")]
        public string CodiceRegistroProtocollo { get; set; }

        [JsonProperty(PropertyName = "nomeRegistroProtocollo")]
        public string NomeRegistroProtocollo { get; set; }

    }
}
