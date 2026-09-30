using ProtocolloInsielService3;
using VBG.Backend.Protocollo.AppLogic.Shared.Logs;
using VBG.Backend.Protocollo.AppLogic.Shared.WsDataClass;

namespace VBG.Backend.Protocollo.AppLogic.Core.Insiel3.Protocollazione
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
                AnnoProtocollo = _response.anno,
                DataProtocollo = _response.data.ToString("dd/MM/yyyy"),
                IdProtocollo = String.Concat(_response.progDoc.ToString(), _separatore, _response.progMovi),
                NumeroProtocollo = _response.numero,
                Warning = _log.Warnings.WarningMessage
            };
        }
    }
}
