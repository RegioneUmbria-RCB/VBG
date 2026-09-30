using VBG.Shared.Infrastructure.DependencyInjection;
using Init.Sigepro.FrontEnd.AppLogic.GestioneLoghi;
using Init.Sigepro.FrontEnd.AppLogic.GestioneOggetti.Metadati;
using Init.Sigepro.FrontEnd.AppLogic.GestioneOggetti.UrlDownloadOggetti;

namespace Init.Sigepro.FrontEnd.AppLogic.GestioneOggetti.IoC
{
    internal static class ConfigurazioneGestioneOggetti
    {
        public static IDIProvider ConfiguraGestioneOggetti(this IDIProvider k)
        {
            k.AddScoped<IOggettiService, OggettiService>();
            k.AddScoped<IOggettiRepository, WsOggettiRepository>();
            k.AddScoped<OggettiServiceCreator>();
            k.AddScoped<IMetadatiOggettoProvider, MetadatiOggettoUtenteProvider>();
            k.AddScoped<IUrlDownloadOggettiService, UrlDownloadOggettiService>();
            k.AddScoped<IDownloadOggettiSecretKeyService, DownloadOggettiSecretKeyService>();
            k.AddScoped<IRisorseFrontofficeService, RisorseFrontofficeService>();

            return k;
        }
    }
}
