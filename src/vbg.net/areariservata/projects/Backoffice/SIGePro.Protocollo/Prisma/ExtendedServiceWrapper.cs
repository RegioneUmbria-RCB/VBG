using Init.SIGePro.Protocollo.Constants;
using Init.SIGePro.Protocollo.Logs;
using Init.SIGePro.Protocollo.ProtocolloExtendedPrismaService;
using Init.SIGePro.Protocollo.Serialize;
using System;
using System.Collections.Generic;
using System.Linq;
using System.ServiceModel;
using System.Text;

namespace Init.SIGePro.Protocollo.Prisma
{
    public class ExtendedServiceWrapper : ServiceWrapperBase
    {
        string _url;
        protected ProtocolloLogs Logs;
        protected ProtocolloSerializer Serializer;

        protected ExtendedServiceWrapper(string url, ProtocolloLogs logs, ProtocolloSerializer serializer, CredentialsInfo credentials) : base(credentials)
        {
            this._url = url;
            this.Logs = logs;
            this.Serializer = serializer;
        }

        protected ProtocolloExtendedServiceClient CreaWebService()
        {
            try
            {
                var endPointAddress = new EndpointAddress(_url);
                var binding = new BasicHttpBinding("prismaHttpBinding");

                if (String.IsNullOrEmpty(_url))
                    throw new Exception("IL PARAMETRO URL_EXTENDED DELLA VERTICALIZZAZIONE PROTOCOLLO_PRISMA NON È STATO VALORIZZATO.");

                if (endPointAddress.Uri.Scheme.ToLower() == ProtocolloConstants.HTTPS)
                    binding.Security = new BasicHttpSecurity { Mode = BasicHttpSecurityMode.Transport };

                return new ProtocolloExtendedServiceClient(binding, endPointAddress);
            }
            catch (Exception ex)
            {
                throw new Exception(String.Format("ERRORE DURANTE LA CREAZIONE DEL WEB SERVICE EXTENDED, {0}", ex.Message), ex);
            }
        }
    }
}
