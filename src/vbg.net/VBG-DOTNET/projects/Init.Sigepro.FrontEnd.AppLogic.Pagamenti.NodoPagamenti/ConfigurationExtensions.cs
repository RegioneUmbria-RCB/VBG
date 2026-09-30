using VBG.Shared.Infrastructure.DependencyInjection;
using Init.Sigepro.FrontEnd.AppLogic.Pagamenti.NodoPagamenti.Anagrafiche;
using Init.Sigepro.FrontEnd.AppLogic.Pagamenti.NodoPagamenti.Configurazione;
using Init.Sigepro.FrontEnd.AppLogic.Pagamenti.NodoPagamenti.Conti;
using Init.Sigepro.FrontEnd.AppLogic.Pagamenti.NodoPagamenti.GestioneMessaggiRabbit.PosizioniDebitorie;
using VBG.Pagamenti.NodoPagamenti;

namespace Init.Sigepro.FrontEnd.AppLogic.Pagamenti.NodoPagamenti
{
    public static class ConfigurazioneIntegrazionePagamenti
    {
        public static IDIProvider ConfiguraIntegrazionePagamentiNodoPagamenti(this IDIProvider k)
        {
            // NODO_PAGAMENTI
            k.AddScoped<INodoPagamentiSettingsReader, NodoPagamentiSettingsReader>();
            k.AddScoped<IPagamentiNodoPagamentiService, PagamentiNodoPagamentiService>();
            k.AddScoped<IConfigurazioneNodoPagamentiRepository, ConfigurazioneNodoPagamentiRepository>();
            k.AddScoped<IVerificaConfigurazioneNodoPagamentiService, VerificaConfigurazioneNodoPagamentiService>();
            // k.AddScoped<IConfigurazioneBuilder<ParametriConfigurazionePagamentiNodoPagamenti>, ParametriPagamentiNodoPagamentiServiceBuilder>();
            k.AddScoped<INodoPagamentiPaymentService, NodoPagamentiPaymentService>();
            k.AddScoped<IContiRepository, ContiRepository>();
            k.AddScoped<IEstremiDomandaNodoPagamentiReader, EstremiDomandaNodoPagamentiReader>();
            k.AddScoped<ContiServiceCreator>();
            k.AddScoped<IEventiPagamentiService, EventiPagamentiService>();

            return k;
        }
    }
}
