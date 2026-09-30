

namespace GeneratoreRiepiloghiHtml.AppLogic.GestioneInterventi
{
    public static class GestioneInterventiConfigurationExtensions
    {
        public static IServiceCollection ConfiguraGestioneInterventi(this IServiceCollection services)
        {
            services.AddScoped<InterventiServiceCreator>();
            services.AddScoped<IInterventiService, InterventiService>();

            return services;
        }
    }
}
