
using Init.SIGePro.Manager.Authentication;
using Init.SIGePro.Manager.Configuration;
using Sigepro.net.App_Start;
using Sigepro.net.Properties;
using System.Configuration;
using System.Xml.XPath;

[assembly: WebActivatorEx.PreApplicationStartMethod(typeof(Sigepro.net.App_Start.RegistrazioneProvidersPerManager), nameof(RegistrazioneProvidersPerManager.Start))]

namespace Sigepro.net.App_Start
{
    public static class RegistrazioneProvidersPerManager
    {
        public class NetFrameworkParametriSecurity : IParametriSecurity
        {
            public string Username => Settings.Default.SigeproSecurityUsername;
            public string Password => Settings.Default.SigeproSecurityPassword;
            public string WebServiceUrl { get; private set; }

            public NetFrameworkParametriSecurity()
            {
                var doc = new XPathDocument(System.AppDomain.CurrentDomain.SetupInformation.ConfigurationFile);
                var navigator = doc.CreateNavigator();
                var node = navigator.SelectSingleNode("/configuration/system.serviceModel/client/endpoint[@binding=\"basicHttpBinding\"]");

                this.WebServiceUrl = node?.GetAttribute("address", "");

                if (string.IsNullOrEmpty(this.WebServiceUrl))
                {
                    this.WebServiceUrl = Settings.Default.SigeproSecurityUrl;
                }
            }
        }

        public class NetFrameworkConfigurationProvider : IFileBasedConfigurationProvider
        {
            public string GetSetting(string setting)
            {
                return ConfigurationManager.AppSettings[setting];
            }
        }

        public static void Start()
        {
            ParametriSecurityStorage.RegistraParametriSecurity(new NetFrameworkParametriSecurity());
            FileBasedConfiguration.RegisterProvider(new NetFrameworkConfigurationProvider());
        }
    }
}