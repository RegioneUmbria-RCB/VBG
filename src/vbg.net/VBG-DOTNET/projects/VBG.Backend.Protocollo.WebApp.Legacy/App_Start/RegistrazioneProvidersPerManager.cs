using Init.SIGePro.Manager.Authentication;
using System.Configuration;
using System.Xml.XPath;
using Init.SIGePro.Manager.Configuration;
using VBG.Backend.Protocollo.WebApp.Legacy.App_Start;
using VBG.Backend.Protocollo.WebApp.Legacy.Properties;

[assembly: WebActivatorEx.PreApplicationStartMethod(typeof(RegistrazioneProvidersPerManager), nameof(RegistrazioneProvidersPerManager.Start))]

namespace VBG.Backend.Protocollo.WebApp.Legacy.App_Start
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