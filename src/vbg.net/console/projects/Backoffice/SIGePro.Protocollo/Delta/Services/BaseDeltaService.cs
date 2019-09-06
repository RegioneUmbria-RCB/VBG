using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Protocollo.ProtocolloDeltaService;
using Init.SIGePro.Protocollo.Logs;
using Init.SIGePro.Protocollo.Serialize;
using System.ServiceModel;

namespace Init.SIGePro.Protocollo.Delta.Services
{
    public class BaseDeltaService
    {
        protected string _url = String.Empty;
        protected ProtocolloLogs _logs;
        protected ProtocolloSerializer _serializer;
        protected string _username;
        protected string _password;
        private string _proxy;

        public BaseDeltaService(string url, ProtocolloLogs logs, ProtocolloSerializer serializer, string username, string password, string proxy)
        {
            _url = url;
            _logs = logs;
            _serializer = serializer;
            _username = username;
            _password = password;
            _proxy = proxy;

            _logs.InfoFormat("Credenziali di autenticazione dei metodi del web service Username: {0}, Password: {1}", _username, _password);
        }

        protected PROTOCOLLOWSDLPortTypeClient CreaWebService()
        {
            try
            {
                _logs.Debug("Creazione del webservice di protocollazione Delta");
                if (String.IsNullOrEmpty(_url))
                    throw new Exception("IL PARAMETRO URL DELLA VERTICALIZZAZIONE PROTOCOLLO_DELTA NON È STATO VALORIZZATO, NON È POSSIBILE CONTATTARE IL WEB SERVICE");

                var endPointAddress = new EndpointAddress(_url);
                var binding = new BasicHttpBinding("deltaHttpBinding");

                if (!String.IsNullOrEmpty(_proxy))
                {
                    binding.UseDefaultWebProxy = false;
                    binding.ProxyAddress = new Uri(_proxy);
                }

                var ws = new PROTOCOLLOWSDLPortTypeClient(binding, endPointAddress);

                _logs.Debug("Fine creazione del webservice DELTA");

                return ws;
            }
            catch (Exception ex)
            {
                throw new Exception("ERRORE AVVENUTO DURANTE LA CREAZIONE DEL WEB SERVICE DI PROTOCOLLAZIONE", ex);
            }
        }
    }
}
