using Init.SIGePro.Protocollo.Constants;
using Init.SIGePro.Protocollo.Logs;
using Init.SIGePro.Protocollo.Prisma.Protocollazione;
using Init.SIGePro.Protocollo.ProtocolloPrismaService;
using Init.SIGePro.Protocollo.Serialize;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using System.ServiceModel;
using System.Text;

namespace Init.SIGePro.Protocollo.Prisma
{
    public class DocAreaServiceWrapperBase : ServiceWrapperBase
    {
        protected ProtocolloLogs Logs;
        protected ProtocolloSerializer Serializer;
        string _endPointAddress;

        protected DocAreaServiceWrapperBase(string endPointAddress, ProtocolloLogs logs, ProtocolloSerializer serializer, CredentialsInfo credentials) : base(credentials)
        {
            this.Logs = logs;
            this.Serializer = serializer;
            this._endPointAddress = endPointAddress;
        }

        protected DOCAREAProtoSoapClient CreaWebService()
        {
            try
            {
                var endPointAddress = new EndpointAddress(_endPointAddress);
                var binding = new BasicHttpBinding("prismaHttpBinding");

                if (String.IsNullOrEmpty(_endPointAddress))
                    throw new Exception("IL PARAMETRO URL_EXTENDED DELLA VERTICALIZZAZIONE PROTOCOLLO_PRISMA NON È STATO VALORIZZATO.");

                if (endPointAddress.Uri.Scheme.ToLower() == ProtocolloConstants.HTTPS)
                    binding.Security = new BasicHttpSecurity { Mode = BasicHttpSecurityMode.Transport };

                return new DOCAREAProtoSoapClient(binding, endPointAddress);
            }
            catch (Exception ex)
            {
                throw new Exception(String.Format("ERRORE DURANTE LA CREAZIONE DEL WEB SERVICE, {0}", ex.Message), ex);
            }

        }
    }
}
