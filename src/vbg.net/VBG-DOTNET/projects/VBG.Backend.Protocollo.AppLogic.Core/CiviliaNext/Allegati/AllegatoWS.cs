using Newtonsoft.Json;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace VBG.Backend.Protocollo.AppLogic.Core.CiviliaNext.Allegati
{
    public class AllegatoWS
    {
        [JsonProperty(PropertyName = "NomeFile")]
        public string NomeFile { get; set; }

        [JsonProperty(PropertyName = "File")]
        public string File { get; set; }

        [JsonProperty(PropertyName = "MimeType")]
        public string MimeType { get; set; }

        [JsonProperty(PropertyName = "Titolo")]
        public string Titolo { get; set; }

        [JsonProperty(PropertyName = "Descrizione")]
        public string Descrizione { get; set; }

        [JsonProperty(PropertyName = "IsPrincipale")]
        public bool IsPrincipale { get; set; }

        [JsonProperty(PropertyName = "IdSingolaFattura")]
        public string IdSingoloFattura { get; set; }

        [JsonProperty(PropertyName = "DescrizioneFascicolo")]
        public string DescrizioneFascicolo { get; set; }

        [JsonProperty(PropertyName = "Percorso")]
        public string Percorso { get; set; }

        [JsonProperty(PropertyName = "Id")]
        public int? Id { get; set; }

    }
}
