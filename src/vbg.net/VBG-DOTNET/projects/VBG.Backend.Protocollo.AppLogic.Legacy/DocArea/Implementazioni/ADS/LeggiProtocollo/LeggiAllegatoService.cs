using System;
using System.ServiceModel;
using VBG.Backend.Protocollo.AppLogic.Shared.Serialize;


namespace VBG.Backend.Protocollo.AppLogic.Legacy.DocArea.Implementazioni.ADS.LeggiProtocollo
{
    public class LeggiAllegatoService : ILeggiAllegatoService
    {
        private static class Constants
        {
            public const string SEPARATORE = "$";
        }

        private IProtocolloSerializer _serializer;
        private string _endPointAddress;
        private string _userName;
        private string _password;

        public LeggiAllegatoService(string endPointAddress, string userName, string password, IProtocolloSerializer serializer)
        {
            if (string.IsNullOrEmpty(endPointAddress))
            {
                throw new ArgumentException($"'{nameof(endPointAddress)}' non può essere Null o vuoto", nameof(endPointAddress));
            }

            if (string.IsNullOrEmpty(userName))
            {
                throw new ArgumentException($"'{nameof(userName)}' non può essere Null o vuoto", nameof(userName));
            }

            if (string.IsNullOrEmpty(password))
            {
                throw new ArgumentException($"'{nameof(password)}' non può essere Null o vuoto", nameof(password));
            }

            this._serializer = serializer ?? throw new ArgumentNullException(nameof(serializer));
            this._endPointAddress = endPointAddress;
            this._userName = userName;
            this._password = password;
        }

        public byte[] Download(string idBase)
        {
            if (string.IsNullOrEmpty(idBase))
            {
                throw new ArgumentException($"'{nameof(idBase)}' non può essere Null o vuoto", nameof(idBase));
            }

            try
            {
                using (var ws = this.CreaWebService()) 
                {
                    var response = ws.downloadAttach(null, idBase, null, this._userName);
                    return response.contentFile;
                }
            }
            catch (Exception)
            {

                throw;
            }
        }

        private AttachServiceClient CreaWebService()
        {
            try
            {
                var endPointAddress = new EndpointAddress(this._endPointAddress);
                var binding = new BasicHttpBinding("defaultHttpBinding");
                binding.MessageEncoding = WSMessageEncoding.Mtom;

                binding.Security = new BasicHttpSecurity { Mode = BasicHttpSecurityMode.TransportCredentialOnly };
                binding.Security.Transport.ClientCredentialType = HttpClientCredentialType.Basic;

                var client = new AttachServiceClient(binding, endPointAddress);
                client.ClientCredentials.UserName.UserName = this._userName;
                client.ClientCredentials.UserName.Password = this._password;

                return client;
            }
            catch (Exception ex)
            {
                throw ex;
            }
        }
    }
}
