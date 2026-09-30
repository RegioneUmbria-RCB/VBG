using Newtonsoft.Json;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace VBG.Backend.Protocollo.AppLogic.Core.CiviliaNext.Titolario
{
    public class EstraiTitolarioResponse
    {
        [JsonProperty(PropertyName = "AreaOmogeneaOrganizzativa")]
        public string AreaOmogeneaOrganizzativa { get; set; }

        [JsonProperty(PropertyName = "DataRiferimento")]
        public DateTime? DataRiferimento { get; set; }

        [JsonProperty(PropertyName = "IncludiDatiOperatore")]
        public bool IncludiDatiOperatore { get; set; }

        [JsonProperty(PropertyName = "Titoli")]
        public List<Titolo> Titoli { get; set; }

        [JsonProperty(PropertyName = "TotaleTitoli")]
        public int TotaleTitoli { get; set; }

        [JsonProperty(PropertyName = "IsOk")]
        public bool IsOk { get; set; }
    }
}
