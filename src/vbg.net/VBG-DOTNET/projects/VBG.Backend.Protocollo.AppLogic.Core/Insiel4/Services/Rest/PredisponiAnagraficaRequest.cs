using VBG.Backend.Protocollo.AppLogic.Core.Insiel4.Protocollazione.Enum;
using VBG.Backend.Protocollo.AppLogic.Core.Insiel4.Services.Rest.Entities;
using System;
using System.Collections.Generic;
using System.Text.Json.Serialization;

namespace VBG.Backend.Protocollo.AppLogic.Core.Insiel4.Services.Rest
{
    public class PredisponiAnagraficaRequest
    {
        [JsonPropertyName("utente")]
        public Utente Utente { get; set; }

        [JsonPropertyName("anagrafica")]
        public AnagraficaObj Anagrafica { get; set; }
    }

    public class AnagraficaObj
    {
        [JsonPropertyName("denominazione")]
        public string Denominazione { get; set; }

        [JsonPropertyName("cognome")]
        public string Cognome { get; set; }

        [JsonPropertyName("nome")]
        public string Nome { get; set; }

        [JsonPropertyName("codiceFiscale")]
        public string CodiceFiscale { get; set; }

        [JsonPropertyName("partitaIva")]
        public string PartitaIva { get; set; }

        [JsonPropertyName("titolo")]
        public string Titolo { get; set; }

        [JsonPropertyName("comune")]
        public string Comune { get; set; }

        [JsonPropertyName("indirizzo")]
        public string Indirizzo { get; set; }

        [JsonPropertyName("provincia")]
        public string Provincia { get; set; }

        [JsonPropertyName("cap")]
        public string Cap { get; set; }

        [JsonPropertyName("stato")]
        public string Stato { get; set; }

        [JsonPropertyName("dataNascita")]
        public DateTime? DataNascita { get; set; }

        [JsonPropertyName("localitaNascita")]
        public string LocalitaNascita { get; set; }

        [JsonPropertyName("provNascita")]
        public string ProvNascita { get; set; }

        [JsonPropertyName("sesso")]
        [JsonConverter(typeof(SessoConverter))]
        public Sesso? Sesso { get; set; }

        [JsonPropertyName("capEstero")]
        public string CapEstero { get; set; }

        [JsonPropertyName("partitaIvaEstero")]
        public string PartitaIvaEstero { get; set; }

        [JsonPropertyName("codiceFiscaleEstero")]
        public string CodiceFiscaleEstero { get; set; }

        [JsonPropertyName("emailList")]
        public List<EmailAnagrafica> EmailList { get; set; }
    }
}
