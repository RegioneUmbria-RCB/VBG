using Init.SIGePro.Manager.Authentication;

namespace VBG.Backend.Protocollo.WebApp.Core
{
    public class NetCoreParametriSecurity : IParametriSecurity
    {
        public NetCoreParametriSecurity(string webServiceUrl, string username, string password)
        {
            this.WebServiceUrl = webServiceUrl;
            this.Username = username;
            this.Password = password;
        }

        public string Username { get; }

        public string Password { get; }

        public string WebServiceUrl { get; }
    }
}
