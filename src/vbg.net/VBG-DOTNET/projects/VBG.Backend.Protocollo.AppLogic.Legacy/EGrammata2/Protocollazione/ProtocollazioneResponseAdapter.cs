using System;
using VBG.Backend.Protocollo.AppLogic.Legacy.EGrammata2.Protocollazione.Segnatura.Response;
using VBG.Backend.Protocollo.AppLogic.Shared.Logs;
using VBG.Backend.Protocollo.AppLogic.Shared.WsDataClass;

namespace VBG.Backend.Protocollo.AppLogic.Legacy.EGrammata2.Protocollazione
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

        public DatiProtocolloResponseType Adatta()
        {
            var res = new DatiProtocolloResponseType
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
