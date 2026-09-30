namespace GeneratoreRiepiloghiHtml.AppLogic.GenerazioneCertificatoInvio
{
    public static class GenerazioneCertificatoInvioConfigurationExtensions
    {
        public static IServiceCollection ConfiguraGenerazioneCertificatoInvio(this IServiceCollection services)
        {
            services.AddScoped<CertificatoDiInvioService>();


            return services;
        }
    }
}
