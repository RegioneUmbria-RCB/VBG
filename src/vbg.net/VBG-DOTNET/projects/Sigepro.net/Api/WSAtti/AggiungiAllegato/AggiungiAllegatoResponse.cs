using Newtonsoft.Json;
using System;
using WSAtti;

namespace Sigepro.net.Api.WSAtti.AggiungiAllegato
{
    public class AggiungiAllegatoResponse : Response
    {
        [JsonProperty("iddocumento")]
        public int? IdDocumento { get; set; }

        [JsonProperty("idallegato")]
        public string IdAllegato { get; set; }

        internal static AggiungiAllegatoResponse FromWSAttiAggiungiAllegatoResponse(WSAttiAggiungiAllegatoResponse response)
        {
            if (response is null)
            {
                throw new ArgumentNullException(nameof(response));
            }

            return new AggiungiAllegatoResponse
            {
                Esito = Esito.FromWSEsito(response.Esito),
                IdDocumento = response.Id,
                IdAllegato = response.IdAllegato
            };
        }
    }
}