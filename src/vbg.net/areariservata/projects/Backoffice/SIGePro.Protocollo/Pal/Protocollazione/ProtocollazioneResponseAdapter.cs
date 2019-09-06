using Init.SIGePro.Protocollo.WsDataClass;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.SIGePro.Protocollo.Pal.Protocollazione
{
    public class ProtocollazioneResponseAdapter
    {
        public ProtocollazioneResponseAdapter()
        {

        }

        public DatiProtocolloRes Adatta(RootObject response)
        {
            var data = new DateTime();
            var isDate = DateTime.TryParse(response.dataProtocollo, out data);
            if (!isDate)
                data = DateTime.Now;

            return new DatiProtocolloRes
            {
                AnnoProtocollo = response.annoProtocollo.ToString(),
                DataProtocollo = data.ToString("dd/MM/yyyy"),
                IdProtocollo = response.id.ToString(),
                NumeroProtocollo = response.numeroProtocollo.ToString()
            };
        }
    }
}
