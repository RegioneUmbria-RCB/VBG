using Newtonsoft.Json;
using System;
using WSAtti;

namespace Sigepro.net.Api.WSAtti.NumeraDetermina
{
    public class NumeraDeterminaResponse : Response
    {
        [JsonProperty("iddocumento")]
        public int? IdDocumento { get; set; }

        [JsonProperty("numero")]
        public int? Numero { get; set; }

        [JsonProperty("anno")]
        public int? Anno { get; set; }

        [JsonProperty("data")]
        public DateTime? Data { get; set; }

        internal static NumeraDeterminaResponse FromWSAttiNumeraDeterminaResponse(WSAttiNumeraDeterminaResponse response)
        {
            if (response is null)
            {
                throw new ArgumentNullException(nameof(response));
            }

            return new NumeraDeterminaResponse
            {
                Esito = Esito.FromWSEsito(response.Esito),
                IdDocumento = response.Id,
                Anno = response.Anno,
                Numero = response.Numero,
                Data = response.Data
            };
        }
    }
}