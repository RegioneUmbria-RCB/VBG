using Newtonsoft.Json;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace VBG.Backend.Protocollo.AppLogic.Core.CiviliaNext.Protocollazione.InvioProtocollo
{
    public class DestinatariWS
    {
        [JsonProperty(PropertyName = "indirizzo")]
        public string Indirizzo { get; set; }
    }
}
