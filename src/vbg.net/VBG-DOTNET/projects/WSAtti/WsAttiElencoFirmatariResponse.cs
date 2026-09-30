using Newtonsoft.Json;
using System.Collections.Generic;
using System.Linq;
using WSAtti.Sicraweb.ElencoFirmatari;

namespace WSAtti
{
    public class WsAttiElencoFirmatariResponse
    {
        [JsonProperty(PropertyName = "firmatari")]
        public IEnumerable<WsAttiFirmatario> Firmatari { get; set; }

        internal static WsAttiElencoFirmatariResponse fromElencoFirmatariResponse(ElencoFirmatariResponse response)
        {
            if (response == null || response.DecodificheList == null || response.DecodificheList.Decodifiche == null)
            {
                return new WsAttiElencoFirmatariResponse();
            }

            return new WsAttiElencoFirmatariResponse
            {
                Firmatari = response
                .DecodificheList
                .Decodifiche
                .Select(x => new WsAttiFirmatario
                {
                    Codice = x.Chiave,
                    Descrizione = x.Valore
                })
                .OrderBy(x => x.Descrizione)
            };
        }
    }
}