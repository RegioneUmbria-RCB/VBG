using EliosWSProtocollazioneSoapClient;
using VBG.Backend.Protocollo.AppLogic.Shared.WsDataClass;

namespace VBG.Backend.Protocollo.AppLogic.Core.Elios
{
    public class InsertResponse
    {
        public int Anno;
        public int Numero;

        internal static InsertResponse FromWSResponse(wReProtocollo response)
        {
            if (response == null)
            {
                throw new Exception("La risposta ottenuta dal WS è nulla");
            }

            if (response.Esito != 0)
            {
                throw new Exception($"{response.MessaggioEsito} Codice: {response.Esito}");
            }

            return new InsertResponse
            {
                Anno = response.Anno,
                Numero = response.Numero,
            };
        }

        internal DatiProtocolloResponseType ToDatiProtocolloRes()
        {
            return new DatiProtocolloResponseType
            {
                AnnoProtocollo = this.Anno.ToString(),
                NumeroProtocollo = this.Numero.ToString(),
            };
        }
    }
}
