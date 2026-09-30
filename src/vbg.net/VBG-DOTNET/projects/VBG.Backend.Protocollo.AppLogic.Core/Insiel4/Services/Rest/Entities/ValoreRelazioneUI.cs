using VBG.Backend.Protocollo.AppLogic.Core.Insiel4.Protocollazione.Enum;
using System.Text.Json.Serialization;

namespace VBG.Backend.Protocollo.AppLogic.Core.Insiel4.Services.Rest.Entities
{
    public class ValoreRelazioneUI
    {
        [JsonPropertyName("valore")]
        public string Valore { get; set; }

        [JsonPropertyName("relazione")]
        [JsonConverter(typeof(OperatoreRelazionaleUIConverter))]
        public OperatoreRelazionaleUI Relazione { get; set; }
    }

    public class ValoreRelazioneUIC
    {
        [JsonPropertyName("valore")]
        public string Valore { get; set; }

        [JsonPropertyName("relazione")]
        [JsonConverter(typeof(OperatoreRelazionaleUICConverter))]
        public OperatoreRelazionaleUIC Relazione { get; set; }
    }

    public class ValoreRelazioneUICF
    {
        [JsonPropertyName("valore")]
        public string Valore { get; set; }

        [JsonPropertyName("relazione")]
        [JsonConverter(typeof(OperatoreRelazionaleUICFConverter))]
        public OperatoreRelazionaleUICF Relazione { get; set; }
    }
}
