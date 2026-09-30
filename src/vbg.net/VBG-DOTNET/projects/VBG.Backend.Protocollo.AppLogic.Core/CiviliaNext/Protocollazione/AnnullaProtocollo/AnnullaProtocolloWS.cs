using Newtonsoft.Json;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace VBG.Backend.Protocollo.AppLogic.Core.CiviliaNext.Protocollazione.AnnullaProtocollo
{
    public class AnnullaProtocolloWS
    {
        [JsonProperty(PropertyName = "idPratica")]
        public long IdPratica { get; set; }

        [JsonProperty(PropertyName = "idRegistro")]
        public long? IdRegistro { get; set; }

        [JsonProperty(PropertyName = "idOperatore")]
        public long? IdOperatore { get; set; }

        [JsonProperty(PropertyName = "codiceRegistro")]
        public string CodiceRegistro { get; set; }

        [JsonProperty(PropertyName = "numeroRegistrazione")]
        public long? NumeroRegistrazione { get; set; }

        [JsonProperty(PropertyName = "dataRegistrazione")]
        public DateTime? DataRegistrazione { get; set; }

        [JsonProperty(PropertyName = "idCodiceAOO")]
        public long IdCodiceAOO { get; set; }

        [JsonProperty(PropertyName = "motivoAnnullamento")]
        public string MotivoAnnullamento { get; set; }

        [JsonProperty(PropertyName = "dataAnnullamento")]

        public DateTime DataAnnullamento { get; set; }
    }
}
