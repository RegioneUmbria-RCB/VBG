using VBG.Shared.Infrastructure.DependencyInjection;
using VBG.Backend.Protocollo.AppLogic.Legacy.Infrastructure;
using VBG.Backend.Protocollo.AppLogic.Shared.Interfaces.Abstractions;
using VBG.Backend.Protocollo.AppLogic.Shared.Logs;
using VBG.Backend.Protocollo.AppLogic.Shared.PerEvitareLaDependencyInjection;
using VBG.Backend.Protocollo.AppLogic.Shared.Validation;

namespace VBG.Backend.Protocollo.AppLogic.Legacy
{
    public static class FrameworkNinjectExtensions
    {
        public static IDIProvider RegistraProtocolloLegacy(this IDIProvider provider)
        {
            #region dal progetto Shared
            provider.AddScoped<ProtocolloValidation>();
            provider.AddScoped<ILogPathResolverFactory, LogPathResolverFactory>();
            provider.AddSingleton<IProtocolloFactory, ProtocolloFactoryLegacy>();
            #endregion

            provider.AddScoped<ILogOptionsProvider, FrameworkLogOptionsProvider>();
            provider.AddScoped<IRequestContextStorage, FrameworkRequestContextStorage>();
            provider.AddScoped<ITemporaryPathProvider, FrameworkTemporaryPathProvider>();
            provider.AddScoped<IRequestUrlProvider, FrameworkRequestUrlProvider>();

            return provider;
        }
    }
}