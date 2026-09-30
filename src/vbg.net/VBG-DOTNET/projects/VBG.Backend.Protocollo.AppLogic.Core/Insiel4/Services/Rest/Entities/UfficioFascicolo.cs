using VBG.Backend.Protocollo.AppLogic.Core.Insiel4.Services.Rest.Entities.Common;
using System;
using System.Collections.Generic;
using System.Text.Json.Serialization;

namespace VBG.Backend.Protocollo.AppLogic.Core.Insiel4.Services.Rest.Entities
{
    public class UfficiFascicolo
    {
        [JsonPropertyName("ufficio")]
        public IEnumerable<UfficioFascicolo> Ufficio { get; set; }
    }

    public class UfficiFascicoloAgg : Molteplicita
    {
        [JsonPropertyName("ufficio")]
        public IEnumerable<UfficioFascicoloAgg> Ufficio { get; set; }
    }

    public class UfficioFascicolo
    {
        [JsonPropertyName("codice")]
        public string Codice { get; set; }

        [JsonPropertyName("descrizione")]
        public string Descrizione { get; set; }

        [JsonPropertyName("dataRiferim")]
        public DateTime DataRiferim { get; set; }

        [JsonPropertyName("modalitaTrasmCod")]
        public string ModalitaTrasmCod { get; set; }

        [JsonPropertyName("modalitaTrasmDesc")]
        public string ModalitaTrasmDesc { get; set; }

        [JsonPropertyName("note")]
        public string Note { get; set; }

    }

    public class UfficioFascicoloAgg : UfficioFascicolo
    {
        [JsonPropertyName("elimina")]
        public bool Elimina { get; set; }

    }
}
