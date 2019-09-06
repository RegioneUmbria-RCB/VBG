using Newtonsoft.Json;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.SIGePro.Sit.Jesi
{
    public class ResponseNumerazioneCivicaJSON
    {
        [JsonProperty(PropertyName = "id_acc_pc")]
        public string Id { get; set; }

        [JsonProperty(PropertyName = "den_completa")]
        public string Denominazione { get; set; }

        [JsonProperty(PropertyName = "ncivico")]
        public string NumeroCivico { get; set; }

        [JsonProperty(PropertyName = "lettera")]
        public string Lettera { get; set; }

        [JsonProperty(PropertyName = "inidataefficacia")]
        public string InizioDataEfficacia { get; set; }

        [JsonProperty(PropertyName = "idstrada")]
        public string IdStrada { get; set; }
    }
}
