using VBG.Shared.Infrastructure.DependencyInjection;
using Init.Sigepro.FrontEnd.AppLogic.GestioneFilesExcel;

namespace Init.Sigepro.FrontEnd.AppLogic.IoC
{
    internal static class ConfigurazioneGestioneFilesExcel
    {
        public static IDIProvider ConfiguraGestioneFilesExcel(this IDIProvider services)
        {
            services.AddScoped<MappatureServiceCreator>();
            services.AddScoped<IRegoleRepository, RegoleRepository>();
            services.AddScoped<IDatiDinamiciExcelService, DatiDinamiciExcelService>();

            return services;
        }
    }
}
