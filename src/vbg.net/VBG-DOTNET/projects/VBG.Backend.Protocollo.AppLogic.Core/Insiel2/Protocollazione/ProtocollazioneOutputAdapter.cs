using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using ProtocolloInsielService2;
using VBG.Backend.Protocollo.AppLogic.Shared.Logs;
using VBG.Backend.Protocollo.AppLogic.Shared.WsDataClass;

namespace VBG.Backend.Protocollo.AppLogic.Core.Insiel2.Protocollazione
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

        public DatiProtocolloResponseType Adatta()
        {
            return new DatiProtocolloResponseType
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
