using VBG.Backend.Protocollo.AppLogic.Core.Insiel4.Services.Rest.Entities;
using System.Collections.Generic;
using System.Text.Json.Serialization;

namespace VBG.Backend.Protocollo.AppLogic.Core.Insiel4.Services.Rest
{
    public class DettaglioProtocolloResponse
    {
        [JsonPropertyName("infoGenerali")]
        public DettagliProtocollo InfoGenerali { get; set; }

        [JsonPropertyName("mittenti")]
        public Corrispondente[] Mittenti { get; set; }

        [JsonPropertyName("destinatari")]
        public DestinatarioDettaglioProtocollo[] Destinatari { get; set; }

        [JsonPropertyName("uffici")]
        public Corrispondente[] Uffici { get; set; }

        /*
         * Classe commentanta perchè mai usata e creava dei problemi di conversione riguardanti il verso in fase di deserializzazione.
        [JsonPropertyName("precedenti")]
        public Precedente[] Precedenti { get; set; }
        */

        [JsonPropertyName("classifiche")]
        public ClassificaView[] Classifiche { get; set; }

        [JsonPropertyName("documenti")]
        public DocumentoAllegato[] Documenti { get; set; }

        [JsonPropertyName("fascicoli")]
        public IEnumerable<Pratica> Fascicoli { get; set; }

        [JsonPropertyName("mnemonici")]
        public MnemonicoView[] Mnemonici { get; set; }

        [JsonPropertyName("allegati")]
        public Allegato[] Allegati { get; set; }

        [JsonPropertyName("riptotocollazioni")]
        public Entities.Protocollo[] Riprotocollazioni { get; set; }

        [JsonPropertyName("Sigle")]
        public Sigle Sigle { get; set; }
    }
}
