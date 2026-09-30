using VBG.Backend.Protocollo.AppLogic.Core.Insiel4.Protocollazione.Enum;
using VBG.Backend.Protocollo.AppLogic.Core.Insiel4.Services.Rest.Entities;
using System;
using System.Collections.Generic;
using System.Text.Json.Serialization;

namespace VBG.Backend.Protocollo.AppLogic.Core.Insiel4.Services.Rest
{

    public class InserimentoProtocolloRequest
    {
        [JsonPropertyName("utente")]
        public Utente Utente { get; set; }

        [JsonPropertyName("codiceUfficioOperante")]
        public string CodiceUfficioOperante { get; set; }

        [JsonPropertyName("codiceUfficio")]
        public string CodiceUfficio { get; set; }

        [JsonPropertyName("codiceRegistro")]
        public string CodiceRegistro { get; set; }

        [JsonPropertyName("verso")]
        [JsonConverter(typeof(VersoConverter))]
        public Verso Verso { get; set; } = Verso.arrivo;

        [JsonPropertyName("oggettoDocumento")]
        public OggettoDocumento OggettoDocumento { get; set; }

        [JsonPropertyName("oggettoProtocollo")]
        public string OggettoProtocollo { get; set; }

        [JsonPropertyName("mittenti")]
        public IEnumerable<MittenteInsProto> Mittenti { get; set; }

        [JsonPropertyName("destinatari")]
        public IEnumerable<DestinatarioIOPInsProto> Destinatari { get; set; }

        [JsonPropertyName("uffici")]
        public IEnumerable<UfficioInsProto> Uffici { get; set; }

        [JsonPropertyName("documenti")]
        public IEnumerable<DocumentoInsProto> Documenti { get; set; }

        [JsonPropertyName("classifiche")]
        public IEnumerable<Classifica> Classifiche { get; set; }

        [JsonPropertyName("estremiDocumento")]
        public EstremiDocumento EstremiDocumento { get; set; }

        [JsonPropertyName("dataRicezioneSpedizione")]
        public DateTime? DataRicezioneSpedizione { get; set; }

        [JsonPropertyName("tipoMittenteMail")]
        public string TipoMittenteMail { get; set; }

        [JsonPropertyName("sequenzaCodice")]
        public string SequenzaCodice { get; set; }

        [JsonPropertyName("fascicoli")]
        public IEnumerable<Pratica> Fascicoli { get; set; }

        [JsonPropertyName("precedenti")]
        public IEnumerable<PrecedenteInsProto> Precedenti { get; set; }

        [JsonPropertyName("mnemonici")]
        public string Mnemonici { get; set; }

        [JsonPropertyName("attivaInvioTelematico")]
        public bool AttivaInvioTelematico { get; set; }

        [JsonPropertyName("casellaAOOmittente")]
        public string CasellaAOOmittente { get; set; }

        [JsonPropertyName("iterattiEsame")]
        public string IterattiEsame { get; set; }

        [JsonPropertyName("disattivaSegnaturaDocPrim")]
        public string DisattivaSegnaturaDocPrim { get; set; }

        [JsonPropertyName("disattivaCtrlDocumenti")]
        public bool DisattivaCtrlDocumenti { get; set; }
    }
}
