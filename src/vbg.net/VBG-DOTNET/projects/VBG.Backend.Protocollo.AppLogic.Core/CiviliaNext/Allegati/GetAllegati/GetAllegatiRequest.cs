using Newtonsoft.Json;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace VBG.Backend.Protocollo.AppLogic.Core.CiviliaNext.Allegati.GetAllegati
{
    public class GetAllegatiRequest
    {
        [JsonProperty(PropertyName = "idPratica")]
        public long IdPratica { get; set; }

        [JsonProperty(PropertyName = "pagina")]
        public int Pagina { get; set; }

        [JsonProperty(PropertyName = "getByteArray")]
        public bool GetByteArray { get; set; }

        [JsonProperty(PropertyName = "isMarcato")]
        public bool IsMarcato { get; set; }

        [JsonProperty(PropertyName = "codiceLivelloOraganigramma")]
        public string CodiceLivelloOrganigramma { get; set; }

        [JsonProperty(PropertyName = "idOperatore")]
        public long IdOperatore { get; set; }

    }
}
