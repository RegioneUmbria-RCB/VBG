using VBG.Shared.Infrastructure.DependencyInjection;
using Init.Sigepro.FrontEnd.AppLogic.GestioneServiziFVG.Database;
using Init.Sigepro.FrontEnd.AppLogic.GestioneServiziFVG.DebugConfiguration;
using Init.Sigepro.FrontEnd.AppLogic.GestioneServiziFVG.ManagedData;

namespace Init.Sigepro.FrontEnd.AppLogic.GestioneServiziFVG.IoC
{
    internal static class ConfigurazioneServiziFVG
    {
        public static IDIProvider ConfiguraServiziFVG(this IDIProvider services)
        {
            services.AddScoped<ServiziFVGService>();
            services.AddScoped<IFvgDatabaseFactory, FvgDatabaseFactory>();
            services.AddScoped<IFvgManagedDataRepository, FvgManagedDataRepository>();
            services.AddScoped<IFVGDebugConfiguration, FVGDebugConfiguration>();

            return services;
        }
    }
}
