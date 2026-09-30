using VBG.Backend.Protocollo.AppLogic.Core.Insiel4.Services.Rest.Entities;
using System.Collections.Generic;
using System.Text.Json.Serialization;

namespace VBG.Backend.Protocollo.AppLogic.Core.Insiel4.Services.Rest
{
    public class InterrogaAnagraficaResponse
    {
        [JsonPropertyName("anagrafiche")]
        public List<Anagrafica> Anagrafiche { get; set; }
    }
}
