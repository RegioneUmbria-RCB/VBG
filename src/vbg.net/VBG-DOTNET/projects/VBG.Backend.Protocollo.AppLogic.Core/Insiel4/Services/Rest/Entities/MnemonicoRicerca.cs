using VBG.Backend.Protocollo.AppLogic.Core.Insiel4.Protocollazione.Enum;
using System.Text.Json.Serialization;

namespace VBG.Backend.Protocollo.AppLogic.Core.Insiel4.Services.Rest.Entities
{
    public class MnemonicoRicerca
    {
        [JsonPropertyName("codiceTipo")]
        public string CodiceTipo { get; set; }

        [JsonPropertyName("livello")]
        public MnemonicoRicercaLivello[] Livello { get; set; }

    }

    public class MnemonicoRicercaLivello
    {
        [JsonPropertyName("valore")]
        public string Valore { get; set; }

        [JsonPropertyName("relazione")]
        [JsonConverter(typeof(OperatoreRelazionaleUICFConverter))]
        public OperatoreRelazionaleUICF Relazione { get; set; }

        [JsonPropertyName("numero")]
        public int Numero { get; set; }
    }
}
