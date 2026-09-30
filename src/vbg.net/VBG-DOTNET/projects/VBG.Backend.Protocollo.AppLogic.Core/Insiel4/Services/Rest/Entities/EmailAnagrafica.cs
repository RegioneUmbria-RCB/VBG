using VBG.Backend.Protocollo.AppLogic.Core.Insiel4.Protocollazione.Enum;
using System.Text.Json.Serialization;

namespace VBG.Backend.Protocollo.AppLogic.Core.Insiel4.Services.Rest.Entities
{
    public class EmailAnagrafica
    {
        [JsonPropertyName("email")]
        public string Email { get; set; }

        [JsonPropertyName("tipo")]
        [JsonConverter(typeof(TipoEmailAnagraficaConverter))]
        public TipoEmailAnagrafica Tipo { get; set; } = TipoEmailAnagrafica.pec;

        [JsonPropertyName("note")]
        public string Note { get; set; }

        [JsonPropertyName("principale")]
        public bool Principale { get; set; }
    }
}
