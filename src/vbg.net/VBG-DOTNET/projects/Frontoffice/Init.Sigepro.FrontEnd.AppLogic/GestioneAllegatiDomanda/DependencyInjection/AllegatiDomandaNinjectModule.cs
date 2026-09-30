using Init.Sigepro.FrontEnd.AppLogic.GestioneAllegatiDomanda.Endoprocedimenti;
using Init.Sigepro.FrontEnd.AppLogic.GestioneAllegatiDomanda.Intervento;
using Init.Sigepro.FrontEnd.AppLogic.GestioneAllegatiDomanda.Intervento.LogicaSincronizzazione;
using Init.Sigepro.FrontEnd.AppLogic.GestioneAllegatiDomanda.RiepilogoDomanda;
using VBG.Shared.Infrastructure.DependencyInjection;

namespace Init.Sigepro.FrontEnd.AppLogic.GestioneAllegatiDomanda.DependencyInjection
{
    public static class AllegatiDomandaNinjectModule
    {
        public static IDIProvider ConfiguraAllegatiDomanda(this IDIProvider k)
        {
            k.AddScoped<AllegatiDomandaService>();
            k.AddScoped<AllegatiDomandaWsClient>();
            k.AddScoped<IAllegatiDomandaFoRepository, WsAllegatiDomandaFoRepository>();

            // Allegati intervento
            k.AddScoped<ILogicaSincronizzazioneAllegatiIntervento, LogicaSincronizzazioneAllegatiIntervento>();
            k.AddScoped<AllegatiInterventoService>();

            // Allegati endoprocedimenti
            k.AddScoped<IAllegatiEndoprocedimentiRepository, WsAllegatiEndoprocedimentiRepository>();
            k.AddScoped<IAllegatiEndoprocedimentiService, AllegatiEndoprocedimentiService>();

            // Riepilogo domanda
            k.AddScoped<RiepilogoDomandaAllegatoService>();
            k.AddScoped<RiepilogoDomandaAllegatoLegacyService>();
            return k;
        }
    }
}
