using VBG.Shared.Infrastructure.DependencyInjection;
using Init.Sigepro.FrontEnd.AppLogic.GestioneVisuraIstanza.VisuraSigepro;

namespace Init.Sigepro.FrontEnd.AppLogic.GestioneVisuraIstanza
{
    public static class VisuraDIConfiguration
    {
        public static IDIProvider ConfiguraVisura(this IDIProvider services)
        {
            services.AddScoped<IVisuraService, VisuraSigeproService>();
            services.AddScoped<WsDettaglioPraticaRepository>();
            services.AddScoped<IstanzeServiceCreator>();

            return services;
        }
    }
}
