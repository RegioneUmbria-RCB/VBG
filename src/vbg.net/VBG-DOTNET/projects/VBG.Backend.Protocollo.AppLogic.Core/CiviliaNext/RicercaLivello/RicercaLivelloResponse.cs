using Newtonsoft.Json;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace VBG.Backend.Protocollo.AppLogic.Core.CiviliaNext.RicercaLivello
{
    public class RicercaLivelloResponse
    {
        [JsonProperty(PropertyName = "NomeLivello")]
        public string NomeLivello { get; set; }

        [JsonProperty(PropertyName = "CodiceAttributoLivello")]
        public string CodiceAttributoLivello { get; set; }

        [JsonProperty(PropertyName = "CodiceAOO")]
        public string CodiceAOO { get; set; }

        [JsonProperty(PropertyName = "AccountUtente")]
        public string AccountUtente { get; set; }

        [JsonProperty(PropertyName = "CodiceFiscaleIndividuo")]
        public string CodiceFiscaleIndividuo { get; set; }

        [JsonProperty(PropertyName = "MostraAccountIndividuo")]
        public bool MostraAccountIndividuo { get; set; }

        [JsonProperty(PropertyName = "Result")]
        public DettagliOperatore DettagliOperatore { get; set; }

        [JsonProperty(PropertyName = "CurrentPage")]
        public int CurrentPage { get; set; }

        [JsonProperty(PropertyName = "PageSize")]
        public int PageSize { get; set; }

        [JsonProperty(PropertyName = "IsOk")]
        public bool IsOk { get; set; }

    }
}
