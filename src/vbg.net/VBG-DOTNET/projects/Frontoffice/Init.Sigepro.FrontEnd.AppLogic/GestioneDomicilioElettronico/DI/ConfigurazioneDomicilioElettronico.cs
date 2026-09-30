using VBG.Shared.Infrastructure.DependencyInjection;

namespace Init.Sigepro.FrontEnd.AppLogic.GestioneDomicilioElettronico.DI
{

    internal static class DomicilioElettronicoModule
    {
        public static IDIProvider ConfiguraDomicilioElettronico(this IDIProvider k)
        {
            k.AddScoped<IDomicilioElettronicoService, DomicilioElettronicoService>();

            return k;
        }
    }

}
