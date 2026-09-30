using VBG.Backend.Protocollo.AppLogic.Core.Insiel4.Services.Rest.Entities.Common;
using System.Text.Json.Serialization;

namespace VBG.Backend.Protocollo.AppLogic.Core.Insiel4.Services.Rest.Entities
{
    public class UfficioInsProto : Molteplicita
    {
        [JsonPropertyName("codice")]
        public string Codice { get; set; }

        [JsonPropertyName("descrizione")]
        public string Descrizione { get; set; }

        [JsonPropertyName("dataRiferim")]
        public string DataRiferimento { get; set; }

        [JsonPropertyName("numRiferim")]
        public string NumeroRiferimento { get; set; }

        [JsonPropertyName("note")]
        public string Note { get; set; }

        [JsonPropertyName("modalitaTrasmissione")]
        public string ModalitaTrasmissione { get; set; }

        [JsonPropertyName("tipo")]
        public string Tipo { get; set; }

        [JsonPropertyName("invioMail")]
        public bool InvioMail { get; set; }

        [JsonPropertyName("invioMailAllegati")]
        public bool InvioMailAllegati { get; set; }

        [JsonPropertyName("invioMailCc")]
        public bool InvioMailCc { get; set; }

        [JsonPropertyName("invioMailRr")]
        public bool InvioMailRr { get; set; }

        [JsonPropertyName("giaInviato")]
        public bool GiaInviato { get; set; }
    }
}
