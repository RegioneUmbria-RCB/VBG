using VBG.Shared.Infrastructure.DependencyInjection;
using Init.Sigepro.FrontEnd.AppLogic.Configurazione.V2;
using Init.Sigepro.FrontEnd.AppLogic.GestionePagamenti.EntraNext;
using Init.Sigepro.FrontEnd.AppLogic.GestionePagamenti.ESED;
using Init.Sigepro.FrontEnd.AppLogic.GestionePagamenti.MIP;
using Init.Sigepro.FrontEnd.AppLogic.Pagamenti.Legacy.Configurazione;
using Init.Sigepro.FrontEnd.AppLogic.Pagamenti.Legacy.ESED;
using Init.Sigepro.FrontEnd.Infrastructure.UrlsAndPaths;
using VBG.Pagamenti.Legacy;
using VBG.Pagamenti.Legacy.ENTRANEXT;
using VBG.Pagamenti.Legacy.ESED;
using VBG.Pagamenti.Legacy.MIP;
using VBG.Pagamenti.Legacy.MIP.Client;

namespace Init.Sigepro.FrontEnd.AppLogic.Pagamenti.Legacy.IoC
{
    public static class ConfigurazioneIntegrazionePagamenti
    {
        public static IDIProvider ConfiguraIntegrazionePagamentiLegacy(this IDIProvider k)
        {

            // Entranext
            k.AddScoped<IConfigurazione<ParametriConfigurazionePagamentiEntraNext>, ConfigurazioneImpl<ParametriConfigurazionePagamentiEntraNext>>();
            k.AddScoped<IConfigurazioneBuilder<ParametriConfigurazionePagamentiEntraNext>, ParametriPagamentiEntraNextServiceBuilder>();
            k.AddScoped<IPagamentiEntraNextSettingsReader, PagamentiEntraNextSettingsReader>();
            k.AddScoped<ESEDServiceCreator>();

            // ESED
            k.AddScoped<IGetStatoPagamento, GetStatoPagamentoESED>();
            k.AddScoped<PayServerClientWrapperESED>(x =>
            {

                var settingsReader = x.GetService<IPagamentiSettingsReader>();
                var urlEncoder = x.GetService<IUrlEncoder>();
                var getStatoPagamento = x.GetService<IGetStatoPagamento>();

                return new PayServerClientWrapperESED(new PayServerClientSettings(settingsReader.GetSettings()), urlEncoder, getStatoPagamento);
            });
            k.AddScoped<PayServerClientFactory>();

            // MIP
            k.AddScoped<IConfigurazione<ParametriConfigurazionePagamentiMIP>, ConfigurazioneImpl<ParametriConfigurazionePagamentiMIP>>();
            k.AddScoped<IConfigurazioneBuilder<ParametriConfigurazionePagamentiMIP>, ParametriPagamentiMipServiceBuilder>();
            k.AddScoped<IPagamentiSettingsReader, PagamentiMipSettingsReader>();
            k.AddScoped<IMIPPaymentRequestFactory, MIPPaymentRequestFactory>();

            return k;
        }
    }
}
