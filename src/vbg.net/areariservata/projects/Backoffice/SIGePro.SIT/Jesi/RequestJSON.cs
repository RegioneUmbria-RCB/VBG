using Newtonsoft.Json;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.SIGePro.Sit.Jesi
{
    public enum AliasEnum
    {
        Cat_Terr_Jesi_F,
        Cat_Terr_Jesi_FN,
        Cat_Urbano_Jesi_FN,
        Cat_Urbano_Jesi_FNS,
        Civici_Jesi_S,
        Civici_Jesi_SC,
        Stradario_Jesi,
        Cat_PCS_Jesi,
        Cat_Edifici_Jesi,
        Cat_Civici_Jesi,
        Civici_Cat_Jesi
    }

    public class RequestJSON
    {
        [JsonProperty(PropertyName = "alias")]
        public string Alias { get; set; }

        [JsonProperty(PropertyName = "pwd")]
        public string Pwd { get; set; }

        [JsonProperty(PropertyName = "parametri")]
        public Dictionary<string, string> Parametri { get; set; }
    }
}
