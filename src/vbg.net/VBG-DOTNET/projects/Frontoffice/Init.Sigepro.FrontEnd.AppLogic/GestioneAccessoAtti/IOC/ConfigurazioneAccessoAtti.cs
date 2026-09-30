using Init.Sigepro.FrontEnd.AppLogic.GestioneAccessoAtti.Trieste;
using Init.Sigepro.FrontEnd.AppLogic.GestioneAccessoAtti.Vbg;
using Init.Sigepro.FrontEnd.AppLogic.GestioneAccessoAtti.Vbg.Zip;
using VBG.Shared.Infrastructure.DependencyInjection;

namespace Init.Sigepro.FrontEnd.AppLogic.GestioneAccessoAtti.IOC
{
    internal static class ConfigurazioneAccessoAtti
    {
        public static IDIProvider ConfiguraAccessoAtti(this IDIProvider kernel)
        {
            // Accesso atti trieste
            kernel.AddScoped<TriesteAccessoAttiService>();

            kernel.AddScoped<IVbgAccessoAttiProxy, VbgAccessoAttiProxy>();
            kernel.AddScoped<IVbgAccessoAttiService, VbgAccessoAttiService>();
            kernel.AddScoped<IZipAccessoAttiProxy, ZipAccessoAttiProxy>();
            kernel.AddScoped<IZipAccessoAttiService, ZipAccessoAttiService>();

            return kernel;
        }
    }
}
