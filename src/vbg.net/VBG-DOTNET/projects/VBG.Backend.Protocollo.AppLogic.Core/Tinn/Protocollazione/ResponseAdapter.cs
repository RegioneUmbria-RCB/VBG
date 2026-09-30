using ProtocolloTinnServiceProxy;
using VBG.Backend.Protocollo.AppLogic.Shared.Logs;
using VBG.Backend.Protocollo.AppLogic.Shared.WsDataClass;

namespace VBG.Backend.Protocollo.AppLogic.Core.Tinn.Protocollazione
{
    public class ResponseAdapter
    {
        TRispostaProtocollazione _response;
        ProtocolloLogs _logs;

        public ResponseAdapter(TRispostaProtocollazione response, ProtocolloLogs logs)
        {
            _response = response;
            _logs = logs;
        }

        public DatiProtocolloResponseType Adatta()
        {
            try
            {
                return new DatiProtocolloResponseType
                {
                    AnnoProtocollo = _response.IngAnnoPG.ToString(),
                    NumeroProtocollo = _response.IngNumPG.ToString(),
                    DataProtocollo = _response.StrDataPG,
                    Warning = _logs.Warnings.WarningMessage
                };
            }
            catch (Exception ex)
            {
                throw new Exception("ERRORE GENERATO DALL'ADATTATORE DI RISPOSTA DELLA PROTOCOLLAZIONE", ex);
            }
        }
    }
}
