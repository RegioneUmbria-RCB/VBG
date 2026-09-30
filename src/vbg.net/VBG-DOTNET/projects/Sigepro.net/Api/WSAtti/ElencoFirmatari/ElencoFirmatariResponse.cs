using Newtonsoft.Json;
using System;
using System.Collections.Generic;
using System.Linq;
using WSAtti;

namespace Sigepro.net.Api.WSAtti.ElencoFirmatari
{
    public class ElencoFirmatariResponse
    {
        [JsonProperty("firmatari")]
        public IEnumerable<Firmatario> Firmatari { get; set; }

        internal static ElencoFirmatariResponse FromWsAttiElencoFirmatariResponse(WsAttiElencoFirmatariResponse response)
        {
            if (response is null)
            {
                throw new ArgumentNullException(nameof(response));
            }

            return new ElencoFirmatariResponse
            {
                Firmatari = response
                                .Firmatari
                                .Select(x => new Firmatario
                                {
                                    Codice = x.Codice,
                                    Descrizione = x.Descrizione
                                })
            };
        }
    }
}