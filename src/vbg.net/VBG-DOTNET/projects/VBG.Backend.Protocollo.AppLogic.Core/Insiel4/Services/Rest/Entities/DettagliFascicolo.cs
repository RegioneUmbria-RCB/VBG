using VBG.Backend.Protocollo.AppLogic.Core.Insiel4.Protocollazione.Enum;
using System;
using System.Text.Json.Serialization;

namespace VBG.Backend.Protocollo.AppLogic.Core.Insiel4.Services.Rest.Entities
{
    public class DettagliFascicolo
    {
        [JsonPropertyName("codiceUfficio")]
        public string CodiceUfficio { get; set; }

        [JsonPropertyName("descrizioneUfficio")]
        public string DescrizioneUfficio { get; set; }

        [JsonPropertyName("codiceRegistro")]
        public string CodiceRegistro { get; set; }

        [JsonPropertyName("descrizioneRegistro")]
        public string DescrizioneRegistro { get; set; }

        [JsonPropertyName("anno")]
        public int Anno { get; set; }

        [JsonPropertyName("numero")]
        public int Numero { get; set; }

        [JsonPropertyName("subNumero")]
        public string SubNumero { get; set; }

        [JsonPropertyName("data")]
        public DateTime Data { get; set; }

        [JsonPropertyName("oggetto")]
        public string Oggetto { get; set; }

        [JsonPropertyName("note")]
        public string Note { get; set; }

        [JsonPropertyName("codiceTipoPratica")]
        public string CodiceTipoPratica { get; set; }

        [JsonPropertyName("dataChiusura")]
        public DateTime DataChiusura { get; set; }

        [JsonPropertyName("progDoc")]
        public long ProgDoc { get; set; }

        [JsonPropertyName("progMovi")]
        public int ProgMovi { get; set; }

        [JsonPropertyName("stato")]
        public int Stato { get; set; }

        [JsonPropertyName("moviLivSegretezza")]
        public int MoviLivSegretezza { get; set; }

        [JsonPropertyName("docLivSegretezza")]
        public int DocLivSegretezza { get; set; }

        [JsonPropertyName("codiceUfficioOperante")]
        public string CodiceUfficioOperante { get; set; }

        [JsonPropertyName("descUfficioOperante")]
        public string DescUfficioOperante { get; set; }

        [JsonPropertyName("regDaClassifica")]
        public RegistroDaClassifica RegDaClassifica { get; set; }

        [JsonPropertyName("praticaAc")]
        [JsonConverter(typeof(PraticaACConverter))]
        public PraticaAC? PraticaAc { get; set; }

        [JsonPropertyName("praticaDataAc")]
        public DateTime PraticaDataAc { get; set; }

        [JsonPropertyName("datiUltimoAggiornamento")]
        public DatiUltimoAggiornamento DatiUltimoAggiornamento { get; set; }

        [JsonPropertyName("indCollClas")]
        public int IndCollClas { get; set; }

        [JsonPropertyName("flAnno")]
        public int FlAnno { get; set; }

        [JsonPropertyName("flLegislatura")]
        public int FlLegislatura { get; set; }

        [JsonPropertyName("primoUfficio")]
        public string PrimoUfficio { get; set; }
    }



}
