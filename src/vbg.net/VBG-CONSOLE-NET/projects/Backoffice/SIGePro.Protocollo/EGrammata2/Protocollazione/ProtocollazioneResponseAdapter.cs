using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Protocollo.Logs;
using Init.SIGePro.Protocollo.EGrammata2.Protocollazione.Segnatura.Response;
using Init.SIGePro.Protocollo.Data;
using Init.SIGePro.Protocollo.WsDataClass;

namespace Init.SIGePro.Protocollo.EGrammata2.Protocollazione
{
    public class ProtocollazioneResponseAdapter
    {
        ProtocolloLogs _logs;
        Risposta _response;

        public ProtocollazioneResponseAdapter(ProtocolloLogs logs, Risposta response)
        {
            _logs = logs;
            _response = response;
        }

        public DatiProtocolloRes Adatta()
        {
            var res = new DatiProtocolloRes
            {
                AnnoProtocollo = _response.NumeroProtocollo.anno,
                DataProtocollo = DateTime.Now.ToString("dd/MM/yyyy"),
                NumeroProtocollo = _response.NumeroProtocollo.numero
            };

            if(_logs.Warnings != null && !String.IsNullOrEmpty(_logs.Warnings.WarningMessage))
                res.Warning = _logs.Warnings.WarningMessage;

            return res;

        }
    }
}
