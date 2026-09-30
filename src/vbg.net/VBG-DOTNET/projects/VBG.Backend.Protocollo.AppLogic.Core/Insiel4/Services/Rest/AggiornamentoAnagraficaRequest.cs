
using VBG.Backend.Protocollo.AppLogic.Core.Insiel4.Services.Rest.Entities;
using System.Text.Json.Serialization;

namespace VBG.Backend.Protocollo.AppLogic.Core.Insiel4.Services.Rest
{
    public class AggiornamentoAnagraficaRequest
    {
        [JsonPropertyName("utente")]
        public Utente Utente { get; set; }

        [JsonPropertyName("idAnagrafica")]
        public IdAnagrafica IdAnagrafica { get; set; }

        [JsonPropertyName("datiAggiornati")]
        public Anagrafica DatiAnagrafica { get; set; }
    }
}
