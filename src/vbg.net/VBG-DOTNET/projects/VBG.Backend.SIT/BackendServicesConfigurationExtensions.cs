using Init.SIGePro.Manager.Authentication;
using Init.SIGePro.Manager.Authentication.Core;
using Init.SIGePro.Manager.Configuration;
using Microsoft.Extensions.Options;

namespace VBG.Backend.SIT
{
    public static class BackendServicesConfigurationExtensions
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

        public class NetCoreConfigurationProvider : IFileBasedConfigurationProvider
        {
            private const string SectionName = "ConfigurazioneBackend";
            private readonly IConfigurationSection _section;

            public NetCoreConfigurationProvider(IConfiguration configuration)
            {
                this._section = configuration.GetSection(SectionName);
            }
            public string GetSetting(string setting)
            {
                return this._section.GetValue<string>(setting);
            }
        }

        public static void RegistraConfigurazioneBackend(this WebApplication app)
        {
            var builder = ((Microsoft.AspNetCore.Builder.IApplicationBuilder)app);

            var options = builder.ApplicationServices.GetRequiredService<IOptions<ConfigurazioneSigeproSecurityOptions>>();
            var config = builder.ApplicationServices.GetRequiredService<IConfiguration>();

            ParametriSecurityStorage.RegistraParametriSecurity(new NetCoreParametriSecurity(options.Value.LoginServiceUrl, options.Value.Username, options.Value.Password));
            FileBasedConfiguration.RegisterProvider(new NetCoreConfigurationProvider(config));
        }

    }
}
