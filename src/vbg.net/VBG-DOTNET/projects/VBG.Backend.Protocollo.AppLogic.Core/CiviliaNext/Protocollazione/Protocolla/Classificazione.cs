using Newtonsoft.Json;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace VBG.Backend.Protocollo.AppLogic.Core.CiviliaNext.Protocollazione.Protocolla
{
    public class Classificazione
    {
        [JsonProperty(PropertyName = "CodiceTitolo")]
        public string CodiceTitolo { get; set; }

        [JsonProperty(PropertyName = "CodiceClasse")]
        public string CodiceClasse { get; set; }

        [JsonProperty(PropertyName = "CodiceSottoClasse")]
        public string CodiceSottoClasse { get; set; }
    }
}
