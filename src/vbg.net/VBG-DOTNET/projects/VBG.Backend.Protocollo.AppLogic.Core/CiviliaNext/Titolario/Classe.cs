using Newtonsoft.Json;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace VBG.Backend.Protocollo.AppLogic.Core.CiviliaNext.Titolario
{
    public class Classe
    {
        [JsonProperty(PropertyName = "SottoClassi")]
        public List<SottoClasse> SottoClassi { get; set; }

        [JsonProperty(PropertyName = "TotaleSottoClassi")]
        public int TotaleSottoClassi { get; set; }

        [JsonProperty(PropertyName = "CreatoDa")]
        public string CreatoDa { get; set; }

        [JsonProperty(PropertyName = "DataCreazione")]
        public DateTime? DataCreazione { get; set; }

        [JsonProperty(PropertyName = "ModificatoDa")]
        public string ModificatoDa { get; set; }

        [JsonProperty(PropertyName = "DataUltimaModifica")]
        public DateTime? DataUltimaModifica { get; set; }

        [JsonProperty(PropertyName = "CodicePadre")]
        public string CodicePadre { get; set; }

        [JsonProperty(PropertyName = "Codice")]
        public string Codice { get; set; }

        [JsonProperty(PropertyName = "Progressivo")]
        public long? Progressivo { get; set; }

        [JsonProperty(PropertyName = "Descrizione")]
        public string Descrizione { get; set; }

        [JsonProperty(PropertyName = "Note")]
        public string Note { get; set; }

        [JsonProperty(PropertyName = "AreeOmogeneeOrganizzative")]
        public List<AreaOmogeneaOrganizzativa> AOO { get; set; }

        [JsonProperty(PropertyName = "Eliminato")]
        public bool Eliminato { get; set; }

    }
}
