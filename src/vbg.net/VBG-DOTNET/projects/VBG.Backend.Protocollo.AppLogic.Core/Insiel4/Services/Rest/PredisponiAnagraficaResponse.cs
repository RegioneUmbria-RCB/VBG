using System.Collections.Generic;
using System;
using System.Text.Json.Serialization;
using VBG.Backend.Protocollo.AppLogic.Core.Insiel4.Services.Rest.Entities;

namespace VBG.Backend.Protocollo.AppLogic.Core.Insiel4.Services.Rest
{
    public class PredisponiAnagraficaResponse
    {
        [JsonPropertyName("anagraficheTrovate")]
        public int AnagraficheTrovate { get; set; }

        [JsonPropertyName("anagrafica")]
        public Anagrafica Anagrafica { get; set; }
    }
}
