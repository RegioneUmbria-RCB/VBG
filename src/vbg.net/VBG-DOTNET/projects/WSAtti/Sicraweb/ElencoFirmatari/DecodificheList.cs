using Newtonsoft.Json;
using System.Collections.Generic;

namespace WSAtti.Sicraweb.ElencoFirmatari
{
    public class DecodificheList
    {
        [JsonProperty(PropertyName = "decodificheList")]
        public List<Decodifiche> Decodifiche { get; set; }
    }
}