using Newtonsoft.Json;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace VBG.Backend.Protocollo.AppLogic.Core.CiviliaNext.Assegnazione
{
    public class Assegnatario
    {
        [JsonProperty(PropertyName = "codiceLivelloOrganigrammaAssegnatario")]
        public string CodiceLivelloOrganigrammaAssegnatario { get; set; }

        [JsonProperty(PropertyName = "tipoAssegnazione")]
        public int TipoAssegnazione { get; set; }

        [JsonProperty(PropertyName = "tipoAssegnatario")]
        public int TipoAssegnatario { get; set; }

        [JsonProperty(PropertyName = "ruoloAssegnatario")]
        public string RuoloAssegnatario { get; set; }

        [JsonProperty(PropertyName = "isAssegnatario")]
        public bool IsAssegnatario { get; set; }

    }
}
