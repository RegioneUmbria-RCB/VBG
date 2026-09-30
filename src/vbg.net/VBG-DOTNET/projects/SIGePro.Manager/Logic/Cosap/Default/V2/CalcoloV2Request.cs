using Newtonsoft.Json;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Init.SIGePro.Manager.Logic.Cosap.Default.V2
{
    public class CalcoloV2Request
    {
        [JsonProperty(PropertyName = "dati")]
        public List<DatoV2> Dati { get; set; }
    }
}
