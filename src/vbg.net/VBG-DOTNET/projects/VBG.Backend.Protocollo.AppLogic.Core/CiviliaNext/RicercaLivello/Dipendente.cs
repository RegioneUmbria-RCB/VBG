using Newtonsoft.Json;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace VBG.Backend.Protocollo.AppLogic.Core.CiviliaNext.RicercaLivello
{
    public class Dipendente
    {
        [JsonProperty(PropertyName = "IdIndividuo")]
        public long IdIndividuo { get; set; }

        [JsonProperty(PropertyName = "Account")]
        public string Account { get; set; }
    }
}
