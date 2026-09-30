using ItCityService;
using VBG.Backend.Protocollo.AppLogic.Shared.WsDataClass;

namespace VBG.Backend.Protocollo.AppLogic.Core.ItCity.Protocollazione
{
    public class ProtocollazioneResponseAdapter
    {
        public ProtocollazioneResponseAdapter()
        {

        }

        public DatiProtocolloResponseType Adatta(ProtocolloOutput response)
        {
            DateTime data;

            var isParsableData = DateTime.TryParse(response.Protocollo.DataProtocollo, out data);
            if (!isParsableData)
            {
                throw new Exception($"La data {response.Protocollo.DataProtocollo}, relativa al protocollo numero {response.Protocollo.NumeroProtocollo} non ha un formato valido, l'operazione non è andata a buon fine ma il protocollo è stato creato, riportare i riferimenti o far annullare il protocollo");
            }

            return new DatiProtocolloResponseType
            {
                AnnoProtocollo = response.Protocollo.AnnoProtocollo,
                DataProtocollo = data.ToString("dd/MM/yyyy"),
                IdProtocollo = response.Protocollo.IdDocumento,
                NumeroProtocollo = response.Protocollo.NumeroProtocollo
            };
        }
    }
}
