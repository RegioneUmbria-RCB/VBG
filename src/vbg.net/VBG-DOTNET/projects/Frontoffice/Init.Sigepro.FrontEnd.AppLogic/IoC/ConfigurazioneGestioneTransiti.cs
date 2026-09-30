using VBG.Shared.Infrastructure.DependencyInjection;
using Init.Sigepro.FrontEnd.AppLogic.GestioneTransiti;

namespace Init.Sigepro.FrontEnd.AppLogic.IoC
{
    internal static class ConfigurazioneGestioneTransiti
    {
        public static IDIProvider ConfiguraGestioneTransiti(this IDIProvider k)
        {
            k.AddScoped<IGestioneTransitiService, GestioneTransitiService>();
            k.AddScoped<IAutorizzazioniTransitiProxy, AutorizzazioniTransitiProxy>();

            return k;
        }
    }
}
