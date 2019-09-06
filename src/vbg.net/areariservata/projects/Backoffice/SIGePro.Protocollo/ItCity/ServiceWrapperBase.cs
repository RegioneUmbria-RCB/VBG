using Init.SIGePro.Protocollo.Constants;
using Init.SIGePro.Protocollo.Logs;
using Init.SIGePro.Protocollo.ProtocolloItCityService;
using Init.SIGePro.Protocollo.Serialize;
using System;
using System.Collections.Generic;
using System.Linq;
using System.ServiceModel;
using System.Text;

namespace Init.SIGePro.Protocollo.ItCity
{
    public class ServiceWrapperBase
    {
        protected readonly string Url;
        protected readonly LoginWsInfo LoginInfo;
        protected readonly ProtocolloLogs Logs;
        protected ProtocolloSerializer Serializer;


        public ServiceWrapperBase(string url, LoginWsInfo loginInfo, ProtocolloLogs logs, ProtocolloSerializer serializer)
        {
            this.Url = url;
            this.LoginInfo = loginInfo;
            this.Logs = logs;
            this.Serializer = serializer;
        }

        protected ProtocollazioneClient CreaWebService()
        {
            try
            {
                var endPointAddress = new EndpointAddress(this.Url);
                var binding = new BasicHttpBinding("defaultHttpBinding");

                if (String.IsNullOrEmpty(this.Url))
                    throw new Exception("IL PARAMETRO URL DELLA VERTICALIZZAZIONE PROTOCOLLO_ITCITY NON È STATO VALORIZZATO.");

                if (endPointAddress.Uri.Scheme.ToLower() == ProtocolloConstants.HTTPS)
                {
                    binding.Security = new BasicHttpSecurity { Mode = BasicHttpSecurityMode.Transport };
                }

                return new ProtocollazioneClient(binding, endPointAddress);
            }
            catch (Exception)
            {
                throw;
            }
        }
    }
}
