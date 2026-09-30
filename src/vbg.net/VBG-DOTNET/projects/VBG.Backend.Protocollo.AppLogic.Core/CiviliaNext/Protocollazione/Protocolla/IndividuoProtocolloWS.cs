using Newtonsoft.Json;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace VBG.Backend.Protocollo.AppLogic.Core.CiviliaNext.Protocollazione.Protocolla
{
    public class IndividuoProtocolloWS
    {
        [JsonProperty(PropertyName = "Denominazione")]
        public string Denominazione { get; set; }
        
        [JsonProperty(PropertyName = "Email")]
        public string Email { get; set; }

        [JsonProperty(PropertyName = "TipoIndividuoProtocollo")]
        public string TipoIndividuoProtocollo { get; set; }

    }
}
