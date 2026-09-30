using EliosWSProtocollazioneSoapClient;
using VBG.Backend.Protocollo.AppLogic.Shared.WsDataClass;

namespace VBG.Backend.Protocollo.AppLogic.Core.Elios
{
    public class GetAllegatoResponse
    {
        public string Key { get; private set; }
        public byte[] Content { get; private set; }
        public string NomeFile { get; private set; }

        internal static GetAllegatoResponse FromWSResponse(wReAllegato response)
        {
            if (response == null)
            {
                throw new Exception("La risposta ottenuta dal WS è nulla");
            }

            if (response.Esito != 0)
            {
                throw new Exception($"{response.MessaggioEsito} Codice: {response.Esito}");
            }

            return new GetAllegatoResponse
            {
                Key = response.Key,
                Content = Convert.FromBase64String(response.Contenuto),
                NomeFile = response.Nome,
            };

        }

        internal AllegatoResponseType ToAllOut()
        {
            return new AllegatoResponseType
            {
                IDBase = this.Key,
                Image = this.Content,
                Serial = this.NomeFile,
                Commento = this.NomeFile,
            };

        }
    }
}