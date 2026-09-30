using Newtonsoft.Json;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.Sigepro.FrontEnd.Pagamenti.NODOPAGAMENTI.ServiziRest
{
    public class StatoPagamentoResponseType
    {
        public int Id { get; set; }
        public String Stato { get; set; }
        public string Descrizione { get; set; }
        public DateTime Data { get; set; }

        [JsonProperty(propertyName: "stato_pagamento_nativo")]
        public string StatoPagamentoNativo { get; set; }
    }
}
