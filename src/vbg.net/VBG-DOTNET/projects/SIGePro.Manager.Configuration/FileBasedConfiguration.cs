using System;

namespace Init.SIGePro.Manager.Configuration
{
    public static class FileBasedConfiguration
    {
        private static IFileBasedConfigurationProvider provider;

        public static string GetSetting(string settingName)
        {
            if (provider == null)
            {
                throw new InvalidOperationException("L'applicazione non ha registrato un provider per la configurazione basata su files");
            }

            return provider.GetSetting(settingName);
        }

        public static void RegisterProvider(IFileBasedConfigurationProvider newProvider)
        {
            if (provider != null)
            {
                throw new InvalidOperationException("Un provider per la configurazione basata su files è già stato registrato");
            }

            provider = newProvider;
        }
    }
}
