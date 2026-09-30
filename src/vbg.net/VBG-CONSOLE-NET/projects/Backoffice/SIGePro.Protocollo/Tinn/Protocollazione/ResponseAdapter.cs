using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Protocollo.Data;
using Init.SIGePro.Protocollo.ProtocolloTinnServiceProxy;
using Init.SIGePro.Protocollo.Logs;
using Init.SIGePro.Protocollo.WsDataClass;

namespace Init.SIGePro.Protocollo.Tinn.Protocollazione
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

        public DatiProtocolloRes Adatta()
        {
            try
            {
                return new DatiProtocolloRes
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
