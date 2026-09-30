using VBG.Backend.Protocollo.AppLogic.Core.Insiel4.Services.Rest.Entities;
using System;
using System.Text.Json.Serialization;

namespace VBG.Backend.Protocollo.AppLogic.Core.Insiel4.Services.Rest
{
    public class RiprotocollaFascicoloRequest
    {
        [JsonPropertyName("utente")]
        public Utente Utente { get; set; }

        [JsonPropertyName("fascicolo")]
        public RegistrazioneFascicolo Fascicolo { get; set; }

        [JsonPropertyName("codiceUfficioOperante")]
        public string CodiceUfficioOperante { get; set; }

        [JsonPropertyName("codiceUfficio")]
        public string CodiceUfficio { get; set; }

        [JsonPropertyName("codiceRegistro")]
        public Classifica CodiceRegistro { get; set; }

        [JsonPropertyName("data")]
        public DateTime? Data { get; set; }

        [JsonPropertyName("anno")]
        public int Anno { get; set; }

        [JsonPropertyName("numero")]
        public int Numero { get; set; }

        [JsonPropertyName("subNumero")]
        public string SubNumero { get; set; }

        [JsonPropertyName("numerazioneManuale")]
        public bool NumerazioneManuale { get; set; }
    }
}
