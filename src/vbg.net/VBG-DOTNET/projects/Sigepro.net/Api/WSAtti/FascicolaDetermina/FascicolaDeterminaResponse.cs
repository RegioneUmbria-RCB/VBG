using Init.SIGePro.Protocollo.WsDataClass;
using it.gruppoinit.Protocollazione;
using Newtonsoft.Json;
using System;

namespace Sigepro.net.Api.WSAtti.FascicolaDetermina
{
    public class FascicolaDeterminaResponse
    {
        [JsonProperty("numero")]
        public string Numero { get; private set; }

        [JsonProperty("datafascicolo")]
        public string DataFascicolo { get; private set; }

        internal static FascicolaDeterminaResponse FromDatiFascicolo(DatiFascicoloResponseType response)
        {
            if (response is null)
            {
                throw new ArgumentNullException(nameof(response));
            }

            return new FascicolaDeterminaResponse
            {
                Numero = response.NumeroFascicolo,
                DataFascicolo = response.DataFascicolo
            };
        }

        internal static FascicolaDeterminaResponse FromDatiProtocolloFascicolato(DatiProtocolloFascicolatoResponseType response)
        {
            if (response is null)
            {
                throw new ArgumentNullException(nameof(response));
            }

            return new FascicolaDeterminaResponse
            {
                Numero = response.NumeroFascicolo,
                DataFascicolo = response.DataFascicolo
            };
        }
    }
}