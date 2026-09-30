using Newtonsoft.Json;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Init.SIGePro.Manager.Logic.Cosap.Default.V2
{
    public class DatoV2
    {
        [JsonProperty(PropertyName = "chiave")]
        public string Chiave { get; set; }

        [JsonProperty(PropertyName = "valore")]
        public string Valore { get; set; }
    }
}
