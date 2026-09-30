using VBG.Shared.Infrastructure.DependencyInjection;

namespace Init.Sigepro.FrontEnd.AppLogic.GestioneComuni.IoC
{
    internal static class ConfigurazioneGestioneComuni
    {
        public static IDIProvider ConfiguraGestioneComuni(this IDIProvider k)
        {
            k.AddScoped<IComuniRepository, WsCachedComuniRepository>();
            k.AddScoped<IComuniService, ComuniService>();
            k.AddScoped<IComuniAssociatiService, ComuniAssociatiService>();
            k.AddScoped<ICittadinanzeService, CittadinanzeService>();
            k.AddScoped<ComuniServiceCreator>();

            return k;
        }
    }

}
