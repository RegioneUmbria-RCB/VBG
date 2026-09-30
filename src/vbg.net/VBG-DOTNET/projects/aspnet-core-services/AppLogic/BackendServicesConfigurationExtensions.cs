using Init.SIGePro.Manager.Authentication;
using Init.SIGePro.Manager.Authentication.Core;
using Init.SIGePro.Manager.Configuration;
using Init.SIGePro.Manager.IOC;
using Init.SIGePro.Manager.Logic.GestioneSchedeAttivita;
using Microsoft.Extensions.Options;
using Vbg.EventBus;
using Vbg.EventBus.IOC;
using VBG.Shared.Infrastructure.DependencyInjection;

namespace AspnetCoreServices.AppLogic
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

        private static IKernelContainer _kernel = default!;

        public static void RegistraConfigurazioneBackend(this WebApplication app)
        {
            var builder = ((Microsoft.AspNetCore.Builder.IApplicationBuilder)app);

            // Schifezza, serve per istanziare lo StaticKernelContainer
            _kernel = builder.ApplicationServices.GetRequiredService<IKernelContainer>();

            var options = builder.ApplicationServices.GetRequiredService<IOptions<ConfigurazioneSigeproSecurityOptions>>();
            var config = builder.ApplicationServices.GetRequiredService<IConfiguration>();

            ParametriSecurityStorage.RegistraParametriSecurity(new NetCoreParametriSecurity(options.Value.LoginServiceUrl, options.Value.Username, options.Value.Password));
            FileBasedConfiguration.RegisterProvider(new NetCoreConfigurationProvider(config));

            // RegistrazioneSistemaEventi
            var registry = builder.ApplicationServices.GetRequiredService<EventSubscribersRegistry>();
            registry.Load(new SigeproManagaerEventBusModule());
            registry.Load(new SchedeAttivitaEventBusModule());
        }

        public static IServiceCollection ConfiguraDependencyInjectionBackend(this IServiceCollection services)
        {
            services.ToDIProvider()
                .RegistraSigeproManager()
                .RegistraEventBusModule();


            return services;
        }
    }
}
