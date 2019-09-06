using Init.SIGePro.Protocollo.Logs;
using Init.SIGePro.Protocollo.ProtocolloApSystemsService;
using Init.SIGePro.Protocollo.Serialize;
using System;
using System.Collections.Generic;
using System.Linq;
using System.ServiceModel;
using System.Text;

namespace Init.SIGePro.Protocollo.ApSystems
{
    public class BaseServiceWrapper
    {
        protected ProtocolloLogs Logs { get; private set; }
        protected ProtocolloSerializer Serializer { get; private set; }
        protected AuthenticationDetails Auth { get; private set; }
        protected string Url { get; private set; }
        protected string Operatore { get; private set; }

        protected BaseServiceWrapper(ProtocolloLogs logs, ProtocolloSerializer serializer, string username, string password, string url, string operatore)
        {
            Auth = new AuthenticationDetails { UserName = username, Password = password };
            Logs = logs;
            Serializer = serializer;
            Url = url;
            Operatore = operatore;
        }

        protected ServiceProtocolloSoapClient CreaWebService()
        {
            Logs.Debug("Creazione del webservice APSYSTEM");
            try
            {
                var endPointAddress = new EndpointAddress(Url);
                var binding = new BasicHttpBinding("ApSystemsHttpBinding");

                if (String.IsNullOrEmpty(Url))
                    throw new Exception("IL PARAMETRO URL DELLA VERTICALIZZAZIONE PROTOCOLLO_APSYSTEMS NON È STATO VALORIZZATO.");

                Logs.Debug("Fine creazione del webservice APSYSTEM");

                return new ServiceProtocolloSoapClient(binding, endPointAddress);
            }
            catch (Exception ex)
            {
                throw new Exception(String.Format("ERRORE DURANTE LA CREAZIONE DEL WEB SERVICE, {0}", ex.Message), ex);
            }
        }
    }
}
