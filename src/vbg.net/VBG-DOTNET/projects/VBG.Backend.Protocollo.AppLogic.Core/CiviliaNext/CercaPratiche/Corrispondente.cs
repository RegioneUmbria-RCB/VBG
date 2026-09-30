using Newtonsoft.Json;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace VBG.Backend.Protocollo.AppLogic.Core.CiviliaNext.CercaPratiche
{
    public class Corrispondente
    {
        [JsonProperty(PropertyName = "denominazione")]
        public string Denominazione { get; set; }

        [JsonProperty(PropertyName = "cognome")]
        public string Cognome { get; set; }

        [JsonProperty(PropertyName = "nome")]
        public string Nome { get; set; }

        [JsonProperty(PropertyName = "email")]
        public string Email { get; set; }

        [JsonProperty(PropertyName = "indirizzo")]
        public string Indirizzo { get; set; }

        [JsonProperty(PropertyName = "codiceFiscale")]
        public string CodiceFiscale { get; set; }

        [JsonProperty(PropertyName = "partitaIva")]
        public string PartitaIva { get; set; }

        [JsonProperty(PropertyName = "cittaResidenza")]
        public string CittaResidenza { get; set; }

        [JsonProperty(PropertyName = "cittaNascita")]
        public string CittaNascita { get; set; }

        [JsonProperty(PropertyName = "dataNascita")]
        public DateTime? DataNascita { get; set; }

        [JsonProperty(PropertyName = "tipoIndividuoProtocollo")]
        public string TipoIndividuoProtocollo { get; set; }

        [JsonProperty(PropertyName = "tipoIndividuoNonCertificato")]
        public string TipoIndividuoNonCertificato { get; set; }

        [JsonProperty(PropertyName = "comune")]
        public Comune Comune { get; set; }

        [JsonProperty(PropertyName = "telefono")]
        public string Telefono { get; set; }

        [JsonProperty(PropertyName = "pec")]
        public string Pec { get; set; }

        [JsonProperty(PropertyName = "sesso")]
        public string Sesso { get; set; }

        [JsonProperty(PropertyName = "tipoIndividuoFG")]
        public string TipoIndividuoFG { get; set; }

        [JsonProperty(PropertyName = "tipoInvio")]
        public string TipoInvio { get; set; }

        [JsonProperty(PropertyName = "dataInizio")]
        public DateTime? DataInizio { get; set; }

        [JsonProperty(PropertyName = "dataFine")]
        public DateTime? DataFine { get; set; }

        [JsonProperty(PropertyName = "siglaProvincia")]
        public string SiglaProvincia { get; set; }

        [JsonProperty(PropertyName = "cap")]
        public string CAP { get; set; }

        [JsonProperty(PropertyName = "codiceIPAAmministrazione")]
        public string CodiceIPAAmministrazione { get; set; }

        [JsonProperty(PropertyName = "codiceIPAAOO")]
        public string CodiceIPAAOO { get; set; }

        [JsonProperty(PropertyName = "id")]
        public long? Id { get; set; }
    }
}
