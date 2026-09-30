using Init.SIGePro.Manager.Authentication.Core;
using Microsoft.Extensions.Configuration;
using Microsoft.Extensions.DependencyInjection;
using VBG.Backend.Protocollo.AppLogic.Core.Configuration;
using VBG.Backend.Protocollo.AppLogic.Core.Infrastructure;
using VBG.Backend.Protocollo.AppLogic.Shared.Interfaces.Abstractions;
using VBG.Backend.Protocollo.AppLogic.Shared.Logs;
using VBG.Backend.Protocollo.AppLogic.Shared.PerEvitareLaDependencyInjection;
using VBG.Backend.Protocollo.AppLogic.Shared.Validation;

namespace VBG.Backend.Protocollo.AppLogic.Core
{
    public static class CoreServiceCollectionExtensions
    {
        public static IServiceCollection AddProtocolloCore(
            this IServiceCollection services,
            IConfiguration configuration)
        {
            services.AddHttpContextAccessor();

            services.Configure<ConfigurazioneProtocolloLegacy>(
                configuration.GetSection("ProtocolloLegacy"));

            #region dal progetto Shared
            services.Configure<ProtocolloLogsOptions>(
                configuration.GetSection("ProtocolloLogsOptions"));

            services.Configure<ConfigurazioneSigeproSecurityOptions>(
                configuration.GetSection(ConfigurazioneSigeproSecurityOptions.SectionName));

            services.AddScoped<ProtocolloValidation>();
            services.AddScoped<ILogPathResolverFactory, LogPathResolverFactory>();
            services.AddSingleton<IProtocolloFactory, ProtocolloFactory>();
          #endregion

            services.AddScoped<ILogOptionsProvider, CoreLogOptionsProvider>();
            services.AddScoped<IRequestContextStorage, CoreRequestContextStorage>();
            services.AddScoped<ITemporaryPathProvider, CoreTemporaryPathProvider>();
            services.AddScoped<IRequestUrlProvider, CoreRequestUrlProvider>();

            return services;
        }
    }

    // Esempio di utilizzo in un'applicazione ASP.NET Core, nel Program.cs:

    //var builder = WebApplication.CreateBuilder(args);

    //builder.Services.AddProtocolloCore(builder.Configuration);

    //var app = builder.Build();

    //using (var scope = app.Services.CreateScope())
    //{
    //    ProtocolloFactoryProvider.Factory = scope.ServiceProvider.GetRequiredService<IProtocolloFactory>();
    //}
}
