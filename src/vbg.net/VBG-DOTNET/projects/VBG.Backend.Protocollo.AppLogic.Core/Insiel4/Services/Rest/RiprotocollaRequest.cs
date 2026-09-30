using VBG.Backend.Protocollo.AppLogic.Core.Insiel4.Protocollazione.Enum;
using VBG.Backend.Protocollo.AppLogic.Core.Insiel4.Services.Rest.Entities;
using System;
using System.Collections.Generic;
using System.Text.Json.Serialization;

namespace VBG.Backend.Protocollo.AppLogic.Core.Insiel4.Services.Rest
{
    public class RiprotocollaRequest
    {
        [JsonPropertyName("utente")]
        public Utente Utente { get; set; }

        [JsonPropertyName("registrazione")]
        public RegistrazioneID Registrazione { get; set; }

        [JsonPropertyName("codiceUfficioOperante")]
        public string CodiceUfficioOperante { get; set; }

        [JsonPropertyName("codiceUfficio")]
        public string CodiceUfficio { get; set; }

        [JsonPropertyName("codiceRegistro")]
        public string CodiceRegistro { get; set; }

        [JsonPropertyName("verso")]
        [JsonConverter(typeof(VersoConverter))]
        public Verso Verso { get; set; } = Verso.arrivo;

        [JsonPropertyName("mittenti")]
        public IEnumerable<MittenteInsProto> Mittenti { get; set; }

        [JsonPropertyName("destinatari")]
        public IEnumerable<DestinatarioInsProto> Destinatari { get; set; }

        [JsonPropertyName("uffici")]
        public IEnumerable<UfficioInsProto> Uffici { get; set; }

        [JsonPropertyName("classifiche")]
        public IEnumerable<Classifica> Classifiche { get; set; }

        [JsonPropertyName("dataRicezioneSpedizione")]
        public DateTime? DataRicezioneSpedizione { get; set; }

        [JsonPropertyName("tipoMittenteMail")]
        public TipoMittenteMail TipoMittenteMail { get; set; } = TipoMittenteMail.UfficioOperante;

        [JsonPropertyName("inserisciInfascicolo")]
        public IEnumerable<FascicoloId> InserisciInfascicolo { get; set; }

        [JsonPropertyName("mnemonici")]
        public IEnumerable<Mnemonico> Mnemonici { get; set; }

        [JsonPropertyName("oggettoProtocollo")]
        public string OggettoProtocollo { get; set; }
    }
}
