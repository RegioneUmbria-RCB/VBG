using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Protocollo.Logs;
using Init.SIGePro.Protocollo.Serialize;
using System.ServiceModel;
using Init.SIGePro.Protocollo.SicrawebAllegatiServiceProxy;

namespace Init.SIGePro.Protocollo.Sicraweb.Services
{
    public class LeggiAllegatiService
    {
        string _url;
        string _bindingName;
        ProtocolloLogs _logs;
        ProtocolloSerializer _serializer;
        

        public LeggiAllegatiService(string url, string bindingName, ProtocolloLogs logs, ProtocolloSerializer serializer)
        {
            _url = url;
            _bindingName = bindingName;
            _logs = logs;
            _serializer = serializer;
        }

        private RepWSSGatewayClient CreaWebService()
        {
            try
            {
                _logs.Debug("Creazione del webservice di protocollazione Sicraweb");
                if (String.IsNullOrEmpty(_url))
                    throw new Exception("IL PARAMETRO URL DELLA VERTICALIZZAZIONE PROTOCOLLO_SICRAWEB NON È STATO VALORIZZATO, NON È POSSIBILE CONTATTARE IL WEB SERVICE");

                if (String.IsNullOrEmpty(_bindingName))
                    _bindingName = "defaultHttpBinding";

                var endPointAddress = new EndpointAddress(_url);
                var binding = new BasicHttpBinding(_bindingName);

                var ws = new RepWSSGatewayClient(binding, endPointAddress);

                _logs.Debug("Fine creazione del webservice SICRAWEB");
                return ws;
            }
            catch (Exception ex)
            {
                throw new Exception("ERRORE AVVENUTO DURANTE LA CREAZIONE DEL WEB SERVICE DI PROTOCOLLAZIONE", ex);
            }
        }

        public string LeggiAllegato(string credenziali, string idDocumento)
        {
            using (var ws = CreaWebService())
            {
                try
                {
                    _logs.InfoFormat("Lettura dell'allegato {0}, credenziali: {1}", idDocumento, credenziali);
                    var response = ws.docExtract(credenziali, idDocumento);
                    _logs.InfoFormat("Lettura dell'allegato {0}, credenziali: {1} avvenuta correttamente", idDocumento, credenziali);

                    return response;
                }
                catch (Exception ex)
                {
                    throw new Exception(String.Format("ERRORE DURANTE LA LETTURA DELL'ALLEGATO {0}", idDocumento), ex);
                }
            }
        }
    }
}
