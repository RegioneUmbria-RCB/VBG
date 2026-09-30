using VBG.Shared.Infrastructure.DependencyInjection;
using Init.Sigepro.FrontEnd.AppLogic.GestioneOneri.EditingSession;

namespace Init.Sigepro.FrontEnd.AppLogic.GestioneOneri
{

    internal static class GestioneOneriModule
    {
        public static IDIProvider ConfiguraGestioneOneri(this IDIProvider k)
        {
            k.AddScoped<OneriServiceCreator>();
            k.AddScoped<IOneriRepository, OneriRepository>();
            k.AddScoped<ILogicaSincronizzazioneOneri, LogicaSincronizzazioneOneri>();
            k.AddScoped<OneriDomandaService>();
            k.AddScoped<IOneriDomandaService>(x => x.GetService<OneriDomandaService>());
            k.AddScoped<ITipiPagamentoService>(x => x.GetService<OneriDomandaService>());
            k.AddScoped<OneriDomandaEditingSessionFactory>();

            return k;
        }
    }

}
