using Newtonsoft.Json;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Init.SIGePro.Manager.Logic.Cosap.Default.V2
{
    public class CalcoloResponse
    {
        [JsonProperty(PropertyName = "importo")]
        public double Importo { get; set; }

        [JsonProperty(PropertyName = "calcolo")]
        public string Calcolo { get; set; }
    }
}
