using VBG.Shared.Infrastructure.DependencyInjection;
using Init.Sigepro.FrontEnd.AppLogic.GestioneTipiSoggetto.Notifiche;

namespace Init.Sigepro.FrontEnd.AppLogic.GestioneTipiSoggetto
{

    internal static class GestioneTipiSoggettoModule
    {
        public static IDIProvider ConfiguraGestioneTipiSoggetto(this IDIProvider k)
        {
            k.AddScoped<TipiSoggettoServiceCreator>();
            k.AddScoped<ITipiSoggettoService, TipiSoggettoService>();
            k.AddScoped<INotificheTipiSoggettoService, NotificheTipiSoggettoService>();


            return k;
        }
    }

}
