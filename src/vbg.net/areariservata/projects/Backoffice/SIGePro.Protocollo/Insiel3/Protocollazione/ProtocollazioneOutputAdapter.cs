using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Protocollo.Data;
using Init.SIGePro.Protocollo.ProtocolloInsielService3;
using Init.SIGePro.Protocollo.Logs;
using Init.SIGePro.Protocollo.WsDataClass;

namespace Init.SIGePro.Protocollo.Insiel3.Protocollazione
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
                AnnoProtocollo = _response.anno,
                DataProtocollo = _response.data.ToString("dd/MM/yyyy"),
                IdProtocollo = String.Concat(_response.progDoc.ToString(), _separatore, _response.progMovi),
                NumeroProtocollo = _response.numero,
                Warning = _log.Warnings.WarningMessage
            };
        }
    }
}
