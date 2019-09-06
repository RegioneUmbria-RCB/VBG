using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Protocollo.MailTipoService;
using System.ServiceModel;
using Init.SIGePro.Protocollo.Logs;
using Init.SIGePro.Manager.Configuration;
using PersonalLib2.Data;
using System.Data;
using Init.SIGePro.Data;
using Init.SIGePro.Manager;
using Init.SIGePro.Protocollo.Serialize;

namespace Init.SIGePro.Protocollo.ProtocolloServices
{
    public class BaseResolveMailTipoService
    {
        protected ProtocolloLogs _log;

        private const string _uriMailTipoWs = "services/mailtipo?wsdl";
        private const string _binding = "MailTipoServiceBinding";

        public BaseResolveMailTipoService(ProtocolloLogs log)
        {
            _log = log;
        }

        protected MailtipoClient CreaWebService()
        {
            try
            {
                var uri = String.Concat(ParametriConfigurazione.Get.WsHostUrlJava, _uriMailTipoWs);
                var binding = new BasicHttpBinding(_binding);
                var remoteAddress = new EndpointAddress(uri);

                return new MailtipoClient(binding, remoteAddress);
            }
            catch (Exception ex)
            {
                throw _log.LogErrorException("ERRORE VERIFICATO DURANTE LA CREAZIONE DEL CLIENT DI CONNESSIONE AL WEB SERVICE PER IL RECUPERO DEI DATI DELL'OGGETTO DA MAIL E TESTI TIPO", ex);
            }
        }
    }
}
