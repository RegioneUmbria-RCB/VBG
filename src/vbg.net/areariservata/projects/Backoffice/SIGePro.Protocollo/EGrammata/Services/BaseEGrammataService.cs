using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Protocollo.Logs;
using Init.SIGePro.Protocollo.Serialize;
using Init.SIGePro.Protocollo.EGrammata.Segnatura.GetProtoInput.BaseOutput_WS;

namespace Init.SIGePro.Protocollo.EGrammata.Services
{
    internal class BaseEGrammataService
    {

        protected ProtocolloLogs _logs;
        protected ProtocolloSerializer _serializer;
        protected string _endPointAddress;

        public BaseEGrammataService(string endPointAddress, ProtocolloLogs logs, ProtocolloSerializer serializer)
        {
            _logs = logs;
            _serializer = serializer;
            _endPointAddress = endPointAddress;
        }

        public void SollevaErrore(string response)
        {
            try
            {
                if (String.IsNullOrEmpty(response))
                    throw new Exception("LA RESPONSE E' VUOTA");

                var baseOutput = (BaseOutput_WS)_serializer.Deserialize(response, typeof(BaseOutput_WS));
                if(baseOutput != null && baseOutput.WSError != null)
                    throw new Exception(String.Format("ERRORE RESTITUITO DAL WEB SERVICE, NUMERO ERRORE: {0}, DESCRIZIONE ERRORE: {1}", baseOutput.WSError.ErrorNumber, baseOutput.WSError.ErrorMessage));
            }
            catch (Exception)
            {
                throw;
            }
        }
    }
}
