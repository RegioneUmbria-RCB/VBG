using System.Configuration;
using System.Net;

namespace Init.Sigepro.FrontEnd.Infrastructure.Web
{
    public class Tls12Utils
    {
        private static class Constants
        {
            public const string Tls12ConfigKey = "Infrastructure.UsaTls12";
        }

        private bool UsaTls12 => ConfigurationManager.AppSettings[Constants.Tls12ConfigKey] != "false";

        public void ApplicaImpostazioniTls12(string url)
        {
            if (url.ToUpper().StartsWith("HTTPS") && this.UsaTls12)
            {
                ServicePointManager.Expect100Continue = true;
                ServicePointManager.SecurityProtocol = SecurityProtocolType.Tls
                                                   | SecurityProtocolType.Tls11
                                                   | SecurityProtocolType.Tls12
#if NET48_OR_GREATER
                                                   | SecurityProtocolType.Tls13
                                                   | SecurityProtocolType.Ssl3
#endif
                                                   ;
            }
        }
    }
}
