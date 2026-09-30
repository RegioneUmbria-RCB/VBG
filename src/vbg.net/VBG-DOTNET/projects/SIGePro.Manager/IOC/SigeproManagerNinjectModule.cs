using Init.SIGePro.Manager.Authentication;
using Init.SIGePro.Manager.Authentication.Utils;
using Init.SIGePro.Manager.Configuration;
using Init.SIGePro.Manager.Logic.DatiDinamici.ConfigurazioneSchede.SchedeCollegateACampi;
using Init.SIGePro.Manager.Logic.DatiDinamici.DataAccess;
using Init.SIGePro.Manager.Logic.GestioneDomandaOnLine;
using Init.SIGePro.Manager.Logic.GestioneIntegrazioneLDP;
using Init.SIGePro.Manager.Logic.GestioneIntegrazioneLDP.EventHandler;
using Init.SIGePro.Manager.Logic.GestioneIntegrazioneLDP.Verticalizzazioni;
using Init.SIGePro.Manager.Logic.GestioneSchedeAttivita;
using SIGePro.Manager.Verticalizzazioni;
using SIGePro.Manager.VerticalizzazioniBase;


#if NET48
using Init.SIGePro.Manager.Logic.Localizzazione;
#endif

using VBG.DatiDinamici.Interfaces.WebControls;
using VBG.Shared.Infrastructure;
using VBG.Shared.Infrastructure.DependencyInjection;

namespace Init.SIGePro.Manager.IOC
{
    public static class SigeproManagerNinjectModule
    {
        public static IDIProvider RegistraSigeproManager(this IDIProvider provider)
        {
            provider.AddSingleton<IKernelContainer>((ctxt) =>
            {
                var kernel = new InMemoryKernelContainer(ctxt);
                StaticKernelContainer.Initialize(kernel);
                return kernel;
            });
            //domanda on line
            provider.AddTransient<IDomandaOnlineService, DomandaOnlineService>();
            provider.AddScoped<RegoleServiceClient>();
            provider.AddTransient<IAuthenticationManager, AuthenticationManager>();
            provider.AddTransient<IVerticalizzazioniFactory, VerticalizzazioniFactory>();

            //Servizi LDP
            //provider.AddTransient<ILdpAnnullamentoServiceWrapper>((ctxt) =>
            //{
            //    var factory = ctxt.GetService<ILdpProxyServiceWrapperFactory>();

            //    return factory.GetAnnullamentoService();
            //});
            provider.AddTransient<IVerticalizzazioneAttiva<VerticalizzazioneSitLdp>, VerticalizzazioneSitLdpAttiva>();
            provider.AddTransient<GestioneIntegrazioneLDPDomandaFOEventHandler>();
            provider.AddTransient<GestioneIntegrazioneLDPService>();
            provider.AddTransient<ILdpProxyServiceWrapperFactory, LdpProxyServiceWrapperFactory>();

            provider.AddTransient<EventiSchedeDinamicheAttivitaService>();
            provider.AddTransient<ISchedeDinamicheAttivitaService, SchedeDinamicheAttivitaService>();
            provider.AddTransient<IDyn2QueryDatiDinamiciManager, QueryDatiDinamiciManager>();

            // Servizi di sistema
            provider.AddTransient<HttpContextAuthenticationInfoResolver>();
            provider.AddScoped<IAuthenticationInfoResolver>((ctxt) => ctxt.GetService<HttpContextAuthenticationInfoResolver>());
            provider.AddScoped<ITransientAuthenticationInfoResolver>((ctxt) => ctxt.GetService<HttpContextAuthenticationInfoResolver>());
            provider.AddTransient<IDatabaseCreator, DatabaseCreator>();
            provider.AddTransient<IIdComuneResolver, IdComuneResolver>();
            provider.AddTransient<AuthenticationInfo>(context =>
            {
                var svc = context.GetService<IAuthenticationInfoResolver>();
                return svc.Resolve();
            });

            provider.AddTransient<SigeproSecurityProxy>();
            provider.AddTransient<IConfigurazioneGenerale, ConfigurazioneGenerale>();
            provider.AddTransient<SchedeCollegateACampiService>();
#if NET48
            provider.AddTransient<CacheLayoutTesti>();
            
#endif
            //provider.AddScoped<CurrentThreadStorage>();
            provider.ConfiguraSharedInfrastructure();

            return provider;
        }
    }
}
