using VBG.Shared.Infrastructure.DependencyInjection;
using Init.Sigepro.FrontEnd.AppLogic.GestioneIntegrazioneLDP.AreeUsoPubblicoLivorno;
using Init.Sigepro.FrontEnd.AppLogic.GestioneIntegrazioneLDP.PraticheEdilizieSiena;
using Init.Sigepro.FrontEnd.AppLogic.GestioneLocalizzazioni;

namespace Init.Sigepro.FrontEnd.AppLogic.GestioneIntegrazioneLDP
{
    public static class ConfigurazioneModuloLDP
    {
        public static IDIProvider ConfiguraIntegrazioneLDP(this IDIProvider services)
        {
            services.AddScoped<IntegrazioneLDPServiceCreator>();
            services.AddScoped<IDownloadPdfDomanda, DownloadPdfDomanda>();
            services.AddScoped<IAreeUsoPubblicoLivornoService, AreeUsoPubblicoLivornoService>();
            services.AddScoped<IIntegrazioneSITDaScadenzario, AreeUsoPubblicoLivornoService>();
            services.AddScoped<IPraticheEdilizieSienaService, PraticheEdilizieSienaService>();

            return services;

        }
    }
}
