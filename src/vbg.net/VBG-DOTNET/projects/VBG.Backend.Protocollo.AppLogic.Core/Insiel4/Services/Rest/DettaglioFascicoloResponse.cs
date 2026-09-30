using VBG.Backend.Protocollo.AppLogic.Core.Insiel4.Services.Rest.Entities;
using System.Collections.Generic;
using System.Text.Json.Serialization;

namespace VBG.Backend.Protocollo.AppLogic.Core.Insiel4.Services.Rest
{
    public class DettaglioFascicoloResponse
    {
        [JsonPropertyName("dettaglio")]
        public DettagliFascicolo Dettaglio { get; set; }

        [JsonPropertyName("uffici")]
        public IEnumerable<UfficioFascicolo> Uffici { get; set; }

        [JsonPropertyName("riferimentiAnagrafici")]
        public IEnumerable<RiferimentoAnagrafico> RiferimentiAnagrafici { get; set; }

        [JsonPropertyName("mnemonico")]
        public IEnumerable<MnemonicoView> Mnemonici { get; set; }
    }

}
