using Newtonsoft.Json;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace VBG.Backend.Protocollo.AppLogic.Core.CiviliaNext.Allegati.GetAllegato
{
    public class GetAllegatoRequest
    {
        [JsonProperty(PropertyName = "idOperatore")]
        public long IdOperatore { get; set; }

        [JsonProperty(PropertyName = "codiceLivelloOrganigramma")]
        public string CodiceLivelloOrganigramma { get; set; }

        [JsonProperty(PropertyName = "idAllegato")]
        public long IdAllegato { get; set; }

        [JsonProperty(PropertyName = "getByteArray")]
        public bool GetByteArray { get; set; }
    }
}
