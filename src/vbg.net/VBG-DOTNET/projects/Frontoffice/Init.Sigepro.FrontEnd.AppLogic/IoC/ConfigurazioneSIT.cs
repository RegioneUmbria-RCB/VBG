using VBG.Shared.Infrastructure.DependencyInjection;
using Init.Sigepro.FrontEnd.AppLogic.IntegrazioneSit;

namespace Init.Sigepro.FrontEnd.AppLogic.IoC
{
    internal static class ConfigurazioneSIT
    {
        public static IDIProvider ConfiguraSIT(this IDIProvider services)
        {
            services.AddScoped<ISitService, SigeproSitService>();
            services.AddScoped<SitServiceCreator>();
            services.AddScoped<ISitServiceCreator, SitServiceCreator>();


            return services;
        }
    }
}
