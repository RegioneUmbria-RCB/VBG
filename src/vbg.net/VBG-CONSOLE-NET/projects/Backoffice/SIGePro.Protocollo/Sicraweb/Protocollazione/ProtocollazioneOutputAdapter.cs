using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Protocollo.Sicraweb.Services;
using Init.SIGePro.Protocollo.Data;
using Init.SIGePro.Protocollo.WsDataClass;

namespace Init.SIGePro.Protocollo.Sicraweb.Protocollazione
{
    public class ProtocollazioneOutputAdapter
    {
        ProtocollazioneRet _response;

        public ProtocollazioneOutputAdapter(ProtocollazioneRet response)
        {
            _response = response;
        }

        public DatiProtocolloRes Adatta()
        {
            return new DatiProtocolloRes
            {
                AnnoProtocollo = _response.lngAnnoPG.ToString(),
                DataProtocollo = DateTime.Now.ToString("dd/MM/yyyy"),
                NumeroProtocollo = _response.lngNumPG.ToString()
            };
        }
    }
}
