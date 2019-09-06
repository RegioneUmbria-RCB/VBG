using Init.SIGePro.Protocollo.ProtocolloItCityService;
using Init.SIGePro.Protocollo.WsDataClass;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.SIGePro.Protocollo.ItCity.Protocollazione
{
    public class ProtocollazioneResponseAdapter
    {
        public ProtocollazioneResponseAdapter()
        {

        }

        public DatiProtocolloRes Adatta(ProtocolloOutput response)
        {
            DateTime data;

            var isParsableData = DateTime.TryParse(response.Protocollo.DataProtocollo, out data);
            if (!isParsableData)
            {
                throw new Exception($"La data {response.Protocollo.DataProtocollo}, relativa al protocollo numero {response.Protocollo.NumeroProtocollo} non ha un formato valido, l'operazione non è andata a buon fine ma il protocollo è stato creato, riportare i riferimenti o far annullare il protocollo");
            }

            return new DatiProtocolloRes
            {
                AnnoProtocollo = response.Protocollo.AnnoProtocollo,
                DataProtocollo = data.ToString("dd/MM/yyyy"),
                IdProtocollo = response.Protocollo.IdDocumento,
                NumeroProtocollo = response.Protocollo.NumeroProtocollo
            };
        }
    }
}
