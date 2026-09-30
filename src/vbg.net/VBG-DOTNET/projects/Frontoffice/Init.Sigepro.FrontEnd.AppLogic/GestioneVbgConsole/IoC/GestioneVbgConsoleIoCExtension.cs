using VBG.Shared.Infrastructure.DependencyInjection;
using Init.Sigepro.FrontEnd.AppLogic.GestioneVbgConsole.Authentication;

namespace Init.Sigepro.FrontEnd.AppLogic.GestioneVbgConsole.IoC
{
    public static class GestioneVbgConsoleIoCExtension
    {
        public static IDIProvider ConfiguraVbgConsole(this IDIProvider services)
        {
            services.AddScoped<IVbgConsoleService, VbgConsoleService>();
            services.AddScoped<IVbgCrossLoginClient, VbgCrossLoginClient>();
            services.AddScoped<IUrlConsoleRepository, UrlConsoleRepository>();

            return services;
        }
    }
}
