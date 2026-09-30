using VBG.Shared.Infrastructure.DependencyInjection;

namespace Init.Sigepro.FrontEnd.AppLogic.GestioneLocalizzazioni.SIC.IoC
{
    internal static class SICConfigurationExtension
    {
        public static IDIProvider ConfiguraSic(this IDIProvider k)
        {
            k.AddScoped<ISitCartograficoService, SitCartograficoService>();
            k.AddScoped<ILocalizzazioniSICDomandaService, LocalizzazioniSICDomandaService>();

#if NET48
            k.AddScoped<ILocalizzazioniSICSyncService, LocalizzazioniSICSyncService>();
#endif
            return k;
        }
    }
}
