using Newtonsoft.Json;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.SIGePro.Sit.Jesi
{
    public class ResponseDettaglioCatastoUrbanoJSON
    {
        [JsonProperty(PropertyName = "Immobile")]
        public string Immobile { get; set; }

        [JsonProperty(PropertyName = "prog_immobile")]
        public string ProgressivoImmobile { get; set; }

        [JsonProperty(PropertyName = "foglio")]
        public string Foglio { get; set; }

        [JsonProperty(PropertyName = "numero")]
        public string NumeroParticella { get; set; }

        [JsonProperty(PropertyName = "sub")]
        public string Subalterno { get; set; }

        [JsonProperty(PropertyName = "categoria")]
        public string Categoria { get; set; }

        [JsonProperty(PropertyName = "piano1")]
        public string Piano1 { get; set; }

        [JsonProperty(PropertyName = "piano2")]
        public string Piano2 { get; set; }

        [JsonProperty(PropertyName = "piano3")]
        public string Piano3 { get; set; }

        [JsonProperty(PropertyName = "piano4")]
        public string Piano4 { get; set; }

        [JsonProperty(PropertyName = "data_fine")]
        public string _dataFine { get; set; }

        public DateTime? DataFine
        {
            get
            {
                if (String.IsNullOrEmpty(this._dataFine))
                {
                    return null;
                }

                DateTime data;

                var isParsable = DateTime.TryParse(this._dataFine, out data);

                if (!isParsable)
                {
                    return null;
                }

                return data;
            }
        }
    }
}
