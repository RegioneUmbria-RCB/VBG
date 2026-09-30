using VBG.Backend.Protocollo.AppLogic.Core.Insiel4.Protocollazione.Enum;
using System.Text.Json.Serialization;

namespace VBG.Backend.Protocollo.AppLogic.Core.Insiel4.Services.Rest.Entities
{
    public class Precedente
    {
        [JsonPropertyName("codUff")]
        public string CodiceUfficio { get; set; }

        [JsonPropertyName("codReg")]
        public string CodiceRegistro { get; set; }

        [JsonPropertyName("anno")]
        public int Anno { get; set; }

        [JsonPropertyName("num")]
        public int Numero { get; set; }

        [JsonPropertyName("subn")]
        public string SubNumero { get; set; }

        [JsonPropertyName("verso")]
        public Verso Verso { get; set; }

        [JsonPropertyName("tipoLegame")]
        public string TipoLegame { get; set; }

        [JsonPropertyName("tipoLegameDesc")]
        public string TipoLegameDescrizione { get; set; }

        [JsonPropertyName("estremiProto")]
        public string EstremiProtocollo { get; set; }

        [JsonPropertyName("idProt")]
        public IdRegistrazione IdProtocollo { get; set; }

        [JsonPropertyName("progPrecedente")]
        public int ProgPrecedente { get; set; }

        [JsonPropertyName("descTipoRife")]
        public string DescrizioneTipoRiferimento { get; set; }

        [JsonPropertyName("codTipoRife")]
        public string CodiceTipoRiferimento { get; set; }

        [JsonPropertyName("codTipoRifeCol")]
        public string CodiceTipoRiferiemntoCol { get; set; }
    }
}
