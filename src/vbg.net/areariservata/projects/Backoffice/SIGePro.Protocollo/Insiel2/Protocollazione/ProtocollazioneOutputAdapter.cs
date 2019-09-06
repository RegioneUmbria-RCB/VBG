using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Protocollo.Data;
using Init.SIGePro.Protocollo.ProtocolloInsielService2;
using Init.SIGePro.Protocollo.Logs;
using Init.SIGePro.Protocollo.WsDataClass;

namespace Init.SIGePro.Protocollo.Insiel2.Protocollazione
{
    public class ProtocollazioneOutputAdapter
    {
        ProtocolloResponse _response;
        string _separatore;
        ProtocolloLogs _log;

        public ProtocollazioneOutputAdapter(ProtocolloResponse response, string separatore, ProtocolloLogs log)
        {
            _response = response;
            _separatore = separatore;
            _log = log;
        }

        public DatiProtocolloRes Adatta()
        {
            return new DatiProtocolloRes
            {
                AnnoProtocollo = _response.Anno,
                DataProtocollo = _response.Data.ToString("dd/MM/yyyy"),
                IdProtocollo = String.Concat(_response.ProgDoc.ToString(), _separatore, _response.ProgMovi),
                NumeroProtocollo = _response.Numero,
                Warning = _log.Warnings.WarningMessage
            };
        }
    }
}
