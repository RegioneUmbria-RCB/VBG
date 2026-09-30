using SIGePro.SIT.Jesi.Request;
using System.Collections.Generic;
using System.Text.Json.Serialization;

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
        Civici_Cat_Jesi,
        Ind_Edi
    }

    public class RequestJSON
    {
        [JsonPropertyName("alias")]
        public string Alias { get; set; }

        [JsonPropertyName("parametri")]
        public List<IRequest> Parametri { get; set; }
    }

    public class RequestJSON<T>
    {
        [JsonPropertyName("alias")]
        public string Alias { get; set; }

        [JsonPropertyName("parametri")]
        public List<T> Parametri { get; set; }
    }

    //public class Parametro
    //{
    //    public string Chiave { get; set; }
    //    public string Valore { get; set; }
    //}
}
