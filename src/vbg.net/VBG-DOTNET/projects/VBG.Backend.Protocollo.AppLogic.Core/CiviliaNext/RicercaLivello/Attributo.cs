using Newtonsoft.Json;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace VBG.Backend.Protocollo.AppLogic.Core.CiviliaNext.RicercaLivello
{
    public class Attributo
    {
        [JsonProperty(PropertyName = "Codice")]
        public string Codice { get; set; }

        [JsonProperty(PropertyName = "Valore")]
        public string Valore { get; set; }
    }
}
