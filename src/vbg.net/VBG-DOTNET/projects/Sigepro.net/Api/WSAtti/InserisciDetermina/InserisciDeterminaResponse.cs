using Newtonsoft.Json;
using System;
using WSAtti;

namespace Sigepro.net.Api.WSAtti.InserisciDetermina
{
    public class InserisciDeterminaResponse : Response
    {
        [JsonProperty("iddocumento")]
        public int? IdDocumento { get; set; }

        [JsonProperty("numero")]
        public int? Numero { get; set; }

        [JsonProperty("anno")]
        public int? Anno { get; set; }

        internal static InserisciDeterminaResponse FromWSAttiInserisciDeterminaResponse(WSAttiInserisciDeterminaResponse response)
        {
            if (response is null)
            {
                throw new ArgumentNullException(nameof(response));
            }

            return new InserisciDeterminaResponse
            {
                Esito = Esito.FromWSEsito(response.Esito),
                IdDocumento = response.Id,
                Anno = response.Anno,
                Numero = response.Numero,
            };
        }
    }
}