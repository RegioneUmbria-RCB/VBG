using VBG.Backend.Protocollo.AppLogic.Core.Insiel4.Protocollazione.Enum;
using VBG.Backend.Protocollo.AppLogic.Core.Insiel4.Services.Rest.Entities;
using System;
using System.Text.Json.Serialization;

namespace VBG.Backend.Protocollo.AppLogic.Core.Insiel4.Services.Rest
{
    public class AggiornamentoProtocolloRequest
    {
        [JsonPropertyName("utente")]
        public Utente Utente { get; set; }

        [JsonPropertyName("registrazione")]
        public RegistrazioneID Registrazione { get; set; }

        [JsonPropertyName("oggetto")]
        public string Oggetto { get; set; }

        [JsonPropertyName("oggettoProtocollo")]
        public string OggettoProtocollo { get; set; }

        [JsonPropertyName("codiceUfficioOperante")]
        public string CodiceUfficioOperante { get; set; }

        [JsonPropertyName("disattivaCtrlDocumenti")]
        public bool DisattivaCtrlDocumenti { get; set; }

        [JsonPropertyName("provvedimento")]
        public EstremiProvvedimento Provvedimento { get; set; }

        [JsonPropertyName("tipoMittenteMail")]
        public TipoMittenteMail TipoMittenteMail { get; set; } = TipoMittenteMail.UfficioOperante;

        [JsonPropertyName("dataRicezioneSpedizione")]
        public DateTime? DataRicezioneSpedizione { get; set; }

        [JsonPropertyName("estremiDocumento")]
        public EstremiDocumento EstremiDocumento { get; set; }

        [JsonPropertyName("mittenti")]
        public MittentiAgg Mittenti { get; set; }

        [JsonPropertyName("destinatari")]
        public DestinatariAgg Destinatari { get; set; }

        [JsonPropertyName("uffici")]
        public UfficiAgg Uffici { get; set; }

        [JsonPropertyName("documenti")]
        public DocumentiAgg Documenti { get; set; }

        [JsonPropertyName("classifiche")]
        public ClassificheAgg Classifiche { get; set; }

        [JsonPropertyName("fascicoli")]
        public FascicoliAgg Fascicoli { get; set; }

        [JsonPropertyName("precedenti")]
        public PrecedentiAgg Precedenti { get; set; }
    }
}
