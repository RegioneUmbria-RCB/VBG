using System;
using System.ServiceModel;
using System.ServiceModel.Channels;
using VBG.Backend.Protocollo.AppLogic.Shared.Costants;

namespace VBG.Backend.Protocollo.AppLogic.Legacy.Acaris.Client
{
    public class BaseAcarisWSClient : IAcarisWsEndpointAddress
    {
        public string Url { get; }
        private readonly string _accessToken;

        public BaseAcarisWSClient(string url, string accessToken)
        {
            this.Url = url;
            this._accessToken = accessToken;
        }

        public EndpointAddress CreaEndpointAddress()
        {
            return new EndpointAddress(this.Url);

        }
        public BasicHttpBinding CreaBasicHttpBinding(string bindingName, EndpointAddress endPointAddress)
        {
            var binding = new BasicHttpBinding(bindingName);

            if (endPointAddress.Uri.Scheme.ToLower() == ProtocolloConstants.HTTPS)
            {
                binding.Security = new BasicHttpSecurity { Mode = BasicHttpSecurityMode.Transport };
            }

            return binding;
        }

        public void AggiungiTokenAContextScope()
        {
            if (!String.IsNullOrEmpty(this._accessToken))
            {
                var request = new HttpRequestMessageProperty();

                request.Headers[System.Net.HttpRequestHeader.Authorization] = "Bearer " + this._accessToken;

                OperationContext.Current.OutgoingMessageProperties.Add(HttpRequestMessageProperty.Name, request);
            }
        }
    }
}
