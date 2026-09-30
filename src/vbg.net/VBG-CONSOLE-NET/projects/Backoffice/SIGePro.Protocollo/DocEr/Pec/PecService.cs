using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Protocollo.Logs;
using Init.SIGePro.Protocollo.Serialize;
using Init.SIGePro.Protocollo.ProtocolloDocErPecService;
using System.ServiceModel;
using Init.SIGePro.Protocollo.Constants;

namespace Init.SIGePro.Protocollo.DocEr.Pec
{
    public class PecService
    {
        string _url; 
        ProtocolloLogs _logs;
        ProtocolloSerializer _serializer;
        string _token;

        public PecService(string url, ProtocolloLogs logs, ProtocolloSerializer serializer, string token)
        {
            _url = url;
            _logs = logs;
            _serializer = serializer;
            _token = token;
        }

        private WSPECPortTypeClient CreaWebService()
        {
            try
            {
                _logs.Debug("Creazione del webservice Pec DOCER");

                var endPointAddress = new EndpointAddress(_url);
                var binding = new BasicHttpBinding("DocErHttpBinding");

                if (String.IsNullOrEmpty(_url))
                    throw new System.Exception("IL PARAMETRO URL_PEC DELLA VERTICALIZZAZIONE PROTOCOLLO_DOCER NON È STATO VALORIZZATO.");

                if (endPointAddress.Uri.Scheme.ToLower() == ProtocolloConstants.HTTPS)
                    binding.Security = new BasicHttpSecurity { Mode = BasicHttpSecurityMode.Transport };

                var ws = new WSPECPortTypeClient(binding, endPointAddress);

                _logs.Debug("Fine creazione del web service PEC PROTOCOLLO_DOCER");

                return ws;
            }
            catch (System.Exception ex)
            {
                throw new System.Exception(String.Format("ERRORE DURANTE LA CREAZIONE DEL WEB SERVICE PEC DOCER", ex.Message), ex);
            }
        }

        public void InvioPec(long idDocumento, SegnaturaPecAdapter.SegnaturaPec datiPec)
        {
            try
            {
                using (var ws = CreaWebService())
                {
                    _logs.InfoFormat("CHIAMATA A INVIO PEC DEL DOCUMENTO {0}, segnatura: {1}", idDocumento.ToString(), datiPec.SegnaturaSerializzata);
                    var response = ws.invioPEC(_token, idDocumento, datiPec.SegnaturaSerializzata);

                    var objResponse = (Esito)_serializer.Deserialize(response, typeof(Esito));
                    _serializer.Serialize(ProtocolloLogsConstants.SegnaturaPecResponseFileName, objResponse);

                    if (objResponse.Codice != "0")
                        throw new Exception(String.Format("CODICE {0}, DESCRIZIONE {1}", objResponse.Codice, objResponse.Descrizione));

                    _logs.InfoFormat("CHIAMATA A INVIO PEC DEL DOCUMENTO {0} ESEGUITA CORRETTAMENTE, IDENTIFICATIVO PEC: {1}", idDocumento, objResponse.Identificativo);
                }
            }
            catch (System.Exception ex)
            {
                throw new Exception(String.Format("ERRORE RESTITUITO DURANTE L'INVIO DELLA PEC TRAMITE WEB SERVICE, ERRORE {0}", ex.Message), ex);
            }
        }
    }
}
