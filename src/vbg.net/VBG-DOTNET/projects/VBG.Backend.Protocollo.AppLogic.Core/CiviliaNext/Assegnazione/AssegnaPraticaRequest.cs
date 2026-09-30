using Newtonsoft.Json;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace VBG.Backend.Protocollo.AppLogic.Core.CiviliaNext.Assegnazione
{
    public class AssegnaPraticaRequest
    {
        [JsonProperty(PropertyName = "idPratica")]
        public long IdPratica { get; set; }

        [JsonProperty(PropertyName = "idOperatore")]
        public long IdOperatore { get; set; }

        [JsonProperty(PropertyName = "assegnatari")]
        public List<Assegnatario> Assegnatari { get; set; }

        /*
        [JsonProperty(PropertyName = "codiceProfilo")]
        public string CodiceProfilo { get; set; }

        [JsonProperty(PropertyName = "note")]
        public string Note { get; set; }
        */
    }
}
