

using VBG.Shared.Infrastructure.DependencyInjection;

namespace Init.Sigepro.FrontEnd.AppLogic.GestioneConversioneFiles.IoC
{
    internal static class ConfigurazioneConversioneFiles
    {
        public static IDIProvider ConfiguraConversioneFiles(this IDIProvider k)
        {
            k.AddScoped<FileConverterService>();
            k.AddScoped<FileConverterServiceCreator>();

            return k;
        }
    }
}
