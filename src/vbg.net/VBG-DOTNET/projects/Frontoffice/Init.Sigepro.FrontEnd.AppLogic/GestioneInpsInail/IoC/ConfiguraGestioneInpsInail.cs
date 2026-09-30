using VBG.Shared.Infrastructure.DependencyInjection;

namespace Init.Sigepro.FrontEnd.AppLogic.GestioneInpsInail.IoC
{
    internal static class ConfigurazioneGestioneInpsInail
    {
        public static IDIProvider ConfiguraGestioneInpsInail(this IDIProvider k)
        {
            k.AddScoped<IInpsInailService, InpsInailService>();
            k.AddScoped<InpsInailService>();

            return k;
        }
    }
}
