using VBG.Backend.Protocollo.AppLogic.Core.Insiel4.Protocollazione.Enum;
using System;
using System.Collections.Generic;
using System.Text.Json.Serialization;

namespace VBG.Backend.Protocollo.AppLogic.Core.Insiel4.Services.Rest.Entities
{
    public class Anagrafica
    {
        [JsonPropertyName("codAna")]
        public string CodiceAnagrafica { get; set; }

        [JsonPropertyName("descAna")]
        public string DescrizioneAnagrafica { get; set; }

        [JsonPropertyName("codTipoAna")]
        [JsonConverter(typeof(CodiceTipoAnagraficaConverter))]
        public CodiceTipoAnagrafica CodTipoAna { get; set; }

        [JsonPropertyName("cognome")]
        public string Cognome { get; set; }

        [JsonPropertyName("nome")]
        public string Nome { get; set; }

        [JsonPropertyName("titolo")]
        public string Titolo { get; set; }

        [JsonPropertyName("denominaz")]
        public string Denominaz { get; set; }

        [JsonPropertyName("indirizzo")]
        public string Indirizzo { get; set; }

        [JsonPropertyName("localita")]
        public string Localita { get; set; }

        [JsonPropertyName("provincia")]
        public string Provincia { get; set; }

        [JsonPropertyName("cap")]
        public string Cap { get; set; }

        [JsonPropertyName("stato")]
        public string Stato { get; set; }

        [JsonPropertyName("codfis")]
        public string Codfis { get; set; }

        [JsonPropertyName("piva")]
        public string Piva { get; set; }

        [JsonPropertyName("codQualDipe")]
        public string CodQualDipe { get; set; }

        [JsonPropertyName("sigla")]
        public string Sigla { get; set; }

        [JsonPropertyName("datanasc")]
        public DateTime? Datanasc { get; set; }

        [JsonPropertyName("locnasc")]
        public string Locnasc { get; set; }

        [JsonPropertyName("provnasc")]
        public string Provnasc { get; set; }

        [JsonPropertyName("sesso")]
        [JsonConverter(typeof(SessoConverter))]
        public Sesso? Sesso { get; set; }

        [JsonPropertyName("eMail")]
        public string EMail { get; set; }

        [JsonPropertyName("codTipolAna")]
        public string CodTipolAna { get; set; }

        [JsonPropertyName("codIop")]
        public string CodIop { get; set; }

        [JsonPropertyName("codAmm")]
        public string CodAmm { get; set; }

        [JsonPropertyName("eMailIop")]
        public string EMailIop { get; set; }

        [JsonPropertyName("disattivata")]
        public bool Disattivata { get; set; }

        [JsonPropertyName("certificata")]
        public bool Certificata { get; set; }

        [JsonPropertyName("emailList")]
        public List<EmailAnagrafica> EmailList { get; set; }
    }
}