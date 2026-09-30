namespace GeneratoreRiepiloghiHtml.AppLogic.GestioneOggetti
{
    public static class ConfigurationExtensions
    {
        public static IServiceCollection ConfiguraGestioneOggetti(this IServiceCollection services)
        {
            services.AddScoped<OggettiServiceCreator>();
            services.AddScoped<IOggettiService, OggettiService>();

            return services;
        }
    }
}
