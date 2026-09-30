using System.Net;

namespace Init.SIGePro.Manager.Configuration.Utils
{
    public class Tls12Utils
    {
        private static class Constants
        {
            public const string Tls12ConfigKey = "Infrastructure.UsaTls12";
        }

        private bool UsaTls12 => FileBasedConfiguration.GetSetting(Constants.Tls12ConfigKey) != "false";

        public void ApplicaImpostazioniTls12(string url)
        {
            if (url.ToUpper().StartsWith("HTTPS") && this.UsaTls12)
            {
                ServicePointManager.Expect100Continue = true;
                ServicePointManager.SecurityProtocol = SecurityProtocolType.Tls
                                                   | (SecurityProtocolType)3072
                                                   | SecurityProtocolType.Ssl3;
            }
        }
    }
}
