using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Protocollo.Data;
using Init.SIGePro.Protocollo.Logs;
using Init.SIGePro.Protocollo.WsDataClass;

namespace Init.SIGePro.Protocollo.SiprWebTest.Protocollazione
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

        public DatiProtocolloRes Adatta(bool modificaNumero, bool aggiungiAnno)
        {
            var output = new DatiProtocolloRes
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
