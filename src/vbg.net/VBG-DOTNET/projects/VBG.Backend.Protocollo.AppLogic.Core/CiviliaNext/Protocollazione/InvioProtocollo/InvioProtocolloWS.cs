using Newtonsoft.Json;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace VBG.Backend.Protocollo.AppLogic.Core.CiviliaNext.Protocollazione.InvioProtocollo
{
    public class InvioProtocolloWS
    {
        [JsonProperty(PropertyName = "listaDestinatari")]
        public string[] ListaDestinatari { get; set; }
    }
}
