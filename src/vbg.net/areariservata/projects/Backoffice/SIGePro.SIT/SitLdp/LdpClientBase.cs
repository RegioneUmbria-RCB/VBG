using Init.SIGePro.Sit.SitLdp.ServiceReferences;
using Init.SIGePro.Sit.Utils;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using System.ServiceModel;
using System.Text;

namespace Init.SIGePro.Sit.SitLdp
{
    class LdpClientBase<T> where T : ILDPSoapClient, IDisposable
    {
        private Uri _serviceUrl;
        BasicSoapAuthenticationCredentials _credentials;
        Func<BasicHttpBinding,EndpointAddress, T> _serviceCreatorCallback;

        public LdpClientBase(string serviceUrl, BasicSoapAuthenticationCredentials credentials, Func<BasicHttpBinding,EndpointAddress, T> serviceCreatorCallback )
        {
            this._serviceUrl = new Uri(serviceUrl);
            this._credentials = credentials;
            this._serviceCreatorCallback = serviceCreatorCallback;
        }

        public R CallServiceMethod<R>(Func<T, R> operation)
        {
            using (var ws = CreateClient())
            {
                try
                {
                    using (var scope = new OperationContextScope(ws.InnerChannel))
                    {
                        this._credentials.AggiungiCredenzialiAContextScope();

                        return operation(ws);
                    }
                }
                catch (Exception)
                {
                    ws.Abort();

                    throw;
                }
            }

        }

        private T CreateClient()
        {
            var endpoint = new EndpointAddress(this._serviceUrl);

            var binding = new BasicHttpBinding();

            binding.MaxBufferSize = 1024000;
            binding.MaxReceivedMessageSize = 1024000;

            if (this._serviceUrl.Scheme.ToUpper() == "HTTPS")
            {
                ServicePointManager.ServerCertificateValidationCallback = delegate { return true; };
                binding.Security.Mode = BasicHttpSecurityMode.Transport;
            }

            var ws = _serviceCreatorCallback(binding, endpoint);

            return ws;
        }
    }
}
