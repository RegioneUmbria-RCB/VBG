using Init.SIGePro.Manager.Configuration.Utils;
using System;
using System.Net;
using System.ServiceModel;
using VBG.Backend.SIT.AppLogic.SitLdp.ServiceReferences;

namespace VBG.Backend.SIT.AppLogic.SitLdp
{
    internal class LdpClientBase<T> where T : ILDPSoapClient, IDisposable
    {
        private readonly Uri _serviceUrl;
        private readonly BasicSoapAuthenticationCredentials _credentials;
        private readonly Func<BasicHttpBinding, EndpointAddress, T> _serviceCreatorCallback;

        public LdpClientBase(string serviceUrl, BasicSoapAuthenticationCredentials credentials, Func<BasicHttpBinding, EndpointAddress, T> serviceCreatorCallback)
        {
            this._serviceUrl = new Uri(serviceUrl);
            this._credentials = credentials;
            this._serviceCreatorCallback = serviceCreatorCallback;
        }

        public R CallServiceMethod<R>(Func<T, R> operation)
        {
            using (var ws = this.CreateClient())
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

                new Tls12Utils().ApplicaImpostazioniTls12(this._serviceUrl.AbsoluteUri);
            }

            var ws = this._serviceCreatorCallback(binding, endpoint);

            return ws;
        }
    }
}
