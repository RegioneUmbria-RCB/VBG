using Init.Sigepro.FrontEnd.AppLogic.GestioneAnagrafiche.Backend;
using Init.Sigepro.FrontEnd.AppLogic.GestioneAnagrafiche.CondizioniUscitaSteps;
using Init.Sigepro.FrontEnd.AppLogic.GestioneAnagrafiche.RicercaAnagrafiche;
using VBG.Shared.Infrastructure.DependencyInjection;

namespace Init.Sigepro.FrontEnd.AppLogic.GestioneAnagrafiche
{

    internal static class GestioneAnagraficheModule
    {
        public static IDIProvider ConfiguraGestioneAnagrafiche(this IDIProvider services)
        {
            services.AddScoped<WsAnagraficheServiceCreator>();
            services.AddScoped<IAnagraficheRepository, WsAnagraficheRepository>();
            services.AddScoped<IAnagraficheBackendService, AnagraficheBackendService>();
            services.AddScoped<IAnagraficheService, AnagraficheService>();
            services.AddScoped<CondizioniUscitaGestioneAnagrafiche>();
            services.AddScoped<CondizioniUscitaGestioneAnagraficheSemplificata>();
            services.AddScoped<IRicercheAnagraficheService, RicercheAnagraficheService>();

            return services;
        }
    }

}
