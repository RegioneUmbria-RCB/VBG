using HalleyProtoService;
using VBG.Backend.Protocollo.AppLogic.Shared.Logs;
using VBG.Backend.Protocollo.AppLogic.Shared.WsDataClass;

namespace VBG.Backend.Protocollo.AppLogic.Core.Halley.Adapters
{
    public class HalleyProtocolloInsertOutputAdapter
    {
        ProtocollazioneRet _response;
        ProtocolloLogs _logs;

        public HalleyProtocolloInsertOutputAdapter(ProtocollazioneRet response, ProtocolloLogs logs)
        {
            _response = response;
            _logs = logs;
        }

        public DatiProtocolloResponseType Adatta()
        {
            try
            {
                var datiRes = new DatiProtocolloResponseType();

                datiRes.NumeroProtocollo = _response.lngNumPG.ToString();
                datiRes.DataProtocollo = DateTime.ParseExact(_response.strDataPG, "yyyy-MM-dd", null).ToString("dd/MM/yyyy");
                datiRes.AnnoProtocollo = _response.lngAnnoPG.ToString();
                datiRes.Warning = _logs.Warnings.WarningMessage;
                return datiRes;
            }
            catch (Exception ex)
            {
                throw new Exception("ERRORE GENERATO DURANTE LA MAPPATURA DEI DATI DI RISPOSTA DEL WEB SERVICE DI PROTOCOLLO SULL'ADATTATORE, LA PROTOCOLLAZIONE E' PROBABILMENTE ANDATA COMUNQUE A BUON FINE", ex);
            }
        }
    }
}
