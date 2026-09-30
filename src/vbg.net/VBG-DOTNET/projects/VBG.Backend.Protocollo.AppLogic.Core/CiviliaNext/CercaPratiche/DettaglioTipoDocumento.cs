using Newtonsoft.Json;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace VBG.Backend.Protocollo.AppLogic.Core.CiviliaNext.CercaPratiche
{
    public class DettaglioTipoDocumento
    {
        [JsonProperty(PropertyName = "nomeTipoDocumento")]
        public string NomeTipoDocumento { get; set; }

        [JsonProperty(PropertyName = "idTipoDocumento")]
        public long? IdTipoDocumento { get; set; }
    }
}
