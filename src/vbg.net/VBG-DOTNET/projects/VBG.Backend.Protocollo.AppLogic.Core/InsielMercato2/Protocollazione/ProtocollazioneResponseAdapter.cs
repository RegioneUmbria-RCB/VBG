using ProtocolloInsielMercatoService2;
using VBG.Backend.Protocollo.AppLogic.Shared.Logs;
using VBG.Backend.Protocollo.AppLogic.Shared.WsDataClass;

namespace VBG.Backend.Protocollo.AppLogic.Core.InsielMercato2.Protocollazione
{
    public class ProtocollazioneResponseAdapter
    {
        recordIdentifier _response;
        ProtocolloLogs _logs;

        public ProtocollazioneResponseAdapter (recordIdentifier response, ProtocolloLogs logs)
	    {
            _response = response;
            _logs = logs;
	    }

        public DatiProtocolloResponseType Adatta()
        {
            try
            {
                var datiRes = new DatiProtocolloResponseType();

                datiRes.IdProtocollo = String.Concat(_response.documentProg.ToString(), PROTOCOLLO_INSIELMERCATO.Constants.SEPARATORE_ID_PROTOCOLLO, _response.moveProg.ToString());
                datiRes.NumeroProtocollo = _response.number.ToString();
                datiRes.DataProtocollo = _response.registrationDate.ToString("dd/MM/yyyy");
                datiRes.AnnoProtocollo = _response.year.ToString();

                datiRes.Warning = _logs.Warnings.WarningMessage;

                return datiRes;
            }
            catch (Exception ex)
            {
                throw new Exception(String.Format("ERRORE GENERATO DURANTE LA MAPPATURA DEI DATI DI RISPOSTA DEL WEB SERVICE DI PROTOCOLLO SULL'ADATTATORE, LA PROTOCOLLAZIONE E' PROBABILMENTE ANDATA COMUNQUE A BUON FINE", ex.Message));
            }
        }
    }
}
