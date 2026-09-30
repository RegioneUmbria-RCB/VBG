using System;
using VBG.Backend.Protocollo.AppLogic.Shared.Logs;
using VBG.Backend.Protocollo.AppLogic.Shared.WsDataClass;


namespace VBG.Backend.Protocollo.AppLogic.Legacy.SiprWeb.Protocollazione
{
    public class ProtocollazioneOutputAdapter
    {
        protDocumentoResponse _response;
        ProtocolloLogs _log;

        public ProtocollazioneOutputAdapter(protDocumentoResponse response, ProtocolloLogs log)
        {
            _response = response;
            _log = log;
        }

        public DatiProtocolloResponseType Adatta(bool modificaNumero, bool aggiungiAnno)
        {
            var output = new DatiProtocolloResponseType
            {
                AnnoProtocollo = _response.NumeroProtocollo.Substring(0, 4),
                DataProtocollo = DateTime.Now.ToString("dd/MM/yyyy"),
                NumeroProtocollo = _response.NumeroProtocollo.Remove(0, 4)
            };

            if(modificaNumero)
                output.NumeroProtocollo = _response.NumeroProtocollo.TrimStart(new char[] { '0' });

            if(aggiungiAnno)
                output.NumeroProtocollo += "/" + _response.NumeroProtocollo.Substring(0, 4);

            output.Warning = _log.Warnings.WarningMessage;

            return output;
        }
    }
}
