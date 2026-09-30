using HalleyFascicolaService;
using System;
using System.Collections.Generic;
using System.Configuration;
using System.Net.Security;
using System.Security.Cryptography.X509Certificates;
using System.ServiceModel;
using System.ServiceModel.Channels;
using System.Text;
using VBG.Backend.Protocollo.AppLogic.Core.CustomEncoder;
using VBG.Backend.Protocollo.AppLogic.Core.ProtocolloHalley2Service;
using VBG.Backend.Protocollo.AppLogic.Shared.Costants;

namespace VBG.Backend.Protocollo.AppLogic.Core.Halley2.Client
{
    public class FascicolaClient
    {
        private readonly string _url;

        public FascicolaClient(string url)
        {
            this._url = url;
        }

        public FascicolaPortClient CreaWebService()
        {
            if (String.IsNullOrEmpty(this._url))
                throw new ConfigurationErrorsException("Il parametro URLFASCICOLAZIONE nella verticalizzazione Halley2 non è valorizzato.");

            var endPointAddress = new EndpointAddress(this._url);

            HttpTransportBindingElement transportBinding = new HttpTransportBindingElement();

            if (endPointAddress.Uri.Scheme.ToLower() == ProtocolloConstants.HTTPS)
            {
                transportBinding = new HttpsTransportBindingElement();
                System.Net.ServicePointManager.SecurityProtocol = System.Net.SecurityProtocolType.Tls12 | System.Net.SecurityProtocolType.Tls13;
                System.Net.ServicePointManager.ServerCertificateValidationCallback = (object sender, X509Certificate certificate, X509Chain chain, SslPolicyErrors sslPolicyErrors) => true;
            }

            var binding = new CustomBinding(
                            new CustomTextMessageBindingElement("iso-8859-1", "text/xml", MessageVersion.Soap11),
                            transportBinding)
            {
                SendTimeout = new TimeSpan(0, 15, 0),
                ReceiveTimeout = new TimeSpan(0, 15, 0)
            };

            return new FascicolaPortClient(binding, endPointAddress);
        }
    }
}
