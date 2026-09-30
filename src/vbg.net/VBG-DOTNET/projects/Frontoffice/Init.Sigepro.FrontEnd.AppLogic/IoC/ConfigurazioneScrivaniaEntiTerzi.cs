using VBG.Shared.Infrastructure.DependencyInjection;
using Init.Sigepro.FrontEnd.AppLogic.GestioneEntiTerzi;

namespace Init.Sigepro.FrontEnd.AppLogic.IoC
{
    public static class ConfigurazioneScrivaniaEntiTerzi
    {
        public static IDIProvider ConfiguraScrivaniaEntiTerzi(this IDIProvider kernel)
        {
            kernel.AddScoped<IScrivaniaEntiTerziService, ScrivaniaEntiTerziService>();
            kernel.AddScoped<IScrivaniaEntiTerziWsProxy, ScrivaniaEntiTerziWsProxy>();

            return kernel;
        }
    }
}
