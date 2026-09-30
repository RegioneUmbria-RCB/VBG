using Newtonsoft.Json;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace VBG.Backend.Protocollo.AppLogic.Core.CiviliaNext.Allegati.AggiungiAllegati
{
    public class AggiungiAllegatiRequest
    {
        [JsonProperty( PropertyName = "idPratica")]
        public long IdPratica { get; set; }

        [JsonProperty(PropertyName = "allegati")]
        public List<AllegatoWS> Allegati { get; set; }

        [JsonProperty(PropertyName = "idRegistro")]
        public long IdRegistro { get; set; }

        [JsonProperty(PropertyName = "generaMarcatura")]
        public bool GeneraMarcatura { get; set; }
    }
}
