using VBG.Shared.Infrastructure.DependencyInjection;

namespace Init.Sigepro.FrontEnd.AppLogic.GestioneProssimiPassi
{

    internal static class GestioneProssimiPassiModule
    {
        public static IDIProvider ConfiguraGestioneProssimiPassi(this IDIProvider k)
        {
            k.AddScoped<IProssimiPassiService, ProssimiPassiService>();

            return k;
        }
    }

}
