namespace VBG.DatiDinamici.Agid.HtmlRendering
{
    public static class ConfigurationExtensions
    {
        public static IServiceCollection RegistraRenderingModelliHTML(this IServiceCollection services)
        {
            services.AddScoped<IModelloDinamicoHtmlRenderer, CoreModelloDinamicoHtmlRenderer>();

            return services;
        }
    }
}
