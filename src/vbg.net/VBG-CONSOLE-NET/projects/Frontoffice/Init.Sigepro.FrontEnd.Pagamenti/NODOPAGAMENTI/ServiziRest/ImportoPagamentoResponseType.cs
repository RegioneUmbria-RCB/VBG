using Newtonsoft.Json;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.Sigepro.FrontEnd.Pagamenti.NODOPAGAMENTI.ServiziRest
{
    public class ImportoPagamentoResponseType
    {
        [JsonProperty(propertyName: "id")]
        public int Id { get; set; }

        [JsonProperty(propertyName: "importo")]
        public decimal Importo { get; set; }

        [JsonProperty(propertyName: "descrizione_causale")]
        public string DescrizioneCausale { get; set; }

        [JsonProperty(propertyName: "dati_riscossione")]
        public string DatiRiscossione { get; set; }

        [JsonProperty(propertyName: "anno_accertamento")]
        public int AnnoAccertamento { get; set; }

        [JsonProperty(propertyName: "numero_accertamento")]
        public string NumeroAccertamento { get; set; }
    }
}
