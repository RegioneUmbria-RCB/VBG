using Init.SIGePro.Manager.Authentication;
using Init.SIGePro.Manager.Configuration;
using System.Configuration;
using WSSigeproExport.App_Start;
using WSSigeproExport.Properties;

[assembly: WebActivatorEx.PreApplicationStartMethod(typeof(WSSigeproExport.App_Start.RegistrazioneProvidersPerManager), nameof(RegistrazioneProvidersPerManager.Start))]

namespace WSSigeproExport.App_Start
{
    public static class RegistrazioneProvidersPerManager
    {
        public class NetFrameworkParametriSecurity : IParametriSecurity
        {
            public string Username => Settings.Default.SigeproSecurityUsername;

            public string Password => Settings.Default.SigeproSecurityPassword;
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