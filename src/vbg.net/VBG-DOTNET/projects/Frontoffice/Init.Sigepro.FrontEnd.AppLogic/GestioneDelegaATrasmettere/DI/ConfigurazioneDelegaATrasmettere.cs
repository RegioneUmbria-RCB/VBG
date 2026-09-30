using VBG.Shared.Infrastructure.DependencyInjection;

namespace Init.Sigepro.FrontEnd.AppLogic.GestioneDelegaATrasmettere.DI
{
    internal static class DelegaATrasmettereModule
    {
        public static IDIProvider ConfiguraDelegaATrasmettere(this IDIProvider k)
        {
            k.AddScoped<IDelegaATrasmettereService, DelegaATrasmettereService>();

            return k;
        }
    }
}
