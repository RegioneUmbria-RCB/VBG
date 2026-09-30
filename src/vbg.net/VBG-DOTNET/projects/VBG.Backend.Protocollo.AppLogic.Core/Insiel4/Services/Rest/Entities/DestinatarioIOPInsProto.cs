using System.Text.Json.Serialization;

namespace VBG.Backend.Protocollo.AppLogic.Core.Insiel4.Services.Rest.Entities
{
    public class DestinatarioIOPInsProto : DestinatarioInsProto
    {
        [JsonPropertyName("invioTelemIop")]
        public bool InvioTelematicoInteroperabilita { get; set; }
        [JsonPropertyName("invioTelemIopRa")]
        public bool InvioTelematicoInteroperabilitaRichiestaAggiornamento { get; set; }
        [JsonPropertyName("invioTelemIopRc")]
        public bool InvioTelematicoInteroperabilitaRichiestaConferma { get; set; }
        [JsonPropertyName("invioTelemPec")]
        public bool InvioTelematicoTramitePec { get; set; }
        [JsonPropertyName("invioTelemMail")]
        public string InvioTelematicoTramiteMail { get; set; }
    }
}
