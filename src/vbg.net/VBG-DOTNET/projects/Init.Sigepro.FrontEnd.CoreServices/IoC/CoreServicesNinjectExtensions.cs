using VBG.Shared.Infrastructure.DependencyInjection;
using Init.Sigepro.FrontEnd.AppLogic.Autenticazione.Vbg.AutenticazioneUtente;
using Init.Sigepro.FrontEnd.AppLogic.Common;
using Init.Sigepro.FrontEnd.AppLogic.Configurazione;
using Init.Sigepro.FrontEnd.AppLogic.ConversionePDF.Ghostscript.API;
using Init.Sigepro.FrontEnd.AppLogic.ReadInterface;
using Init.Sigepro.FrontEnd.CoreServices.Autenticazione;
using Init.Sigepro.FrontEnd.CoreServices.Configurazione;
using Init.Sigepro.FrontEnd.CoreServices.ConversionePDF.Ghostscript;
using Init.Sigepro.FrontEnd.CoreServices.DatiDinamici.Ricerche;
using Init.Sigepro.FrontEnd.CoreServices.GestioneOggetti;
using Init.Sigepro.FrontEnd.CoreServices.GestionePresentazioneDomanda;
using Init.Sigepro.FrontEnd.CoreServices.GestionePresentazioneDomanda.Admin;
using Init.Sigepro.FrontEnd.CoreServices.GestionePresentazioneDomanda.Paginatore;
using Init.Sigepro.FrontEnd.CoreServices.GestioneTitoloPagina;
using Init.Sigepro.FrontEnd.CoreServices.GestioneUrl;
using Init.Sigepro.FrontEnd.CoreServices.LabelUpdater;
using Init.Sigepro.FrontEnd.CoreServices.PNRR.GestioneWorkflow;
using Init.Sigepro.FrontEnd.CoreServices.Visura;
using Init.Sigepro.FrontEnd.Infrastructure.UrlsAndPaths.Standard;

namespace Init.Sigepro.FrontEnd.CoreServices.IoC
{
    public static class ConfigurazioneDICoreServices
    {
        public static IDIProvider ConfiguraGestioneWorkflowCore<StepMapper>(this IDIProvider services) where StepMapper : StepTypeMapperBase
        {
            // Workflow
            services.AddScoped<IWorkflowPNRRService, WorkflowPNRRService>();
            services.AddSingleton<StepTypeMapperBase, StepMapper>();

            return services;
        }

        public static IDIProvider ConfiguraAliasSoftwareCore(this IDIProvider services)
        {
            services.AddScoped<AliasSoftwareProvider>();
            services.AddScoped<IAliasSoftwareResolver>(x => x.GetService<AliasSoftwareProvider>());
            services.AddScoped<IAliasResolver>(x => x.GetService<AliasSoftwareProvider>());
            services.AddScoped<ISoftwareResolver>(x => x.GetService<AliasSoftwareProvider>());

            return services;
        }

        public static IDIProvider ConfiguraTokenCore(this IDIProvider services)
        {
            services.AddScoped<TokenProvider>();
            services.AddScoped<ITokenResolver>(x => x.GetService<TokenProvider>());

            return services;
        }


        public static IDIProvider ConfiguraCoreServices(this IDIProvider services)
        {
            services.AddScoped<DllImportResolver>();
            services.AddScoped<IAppConfigurationReader, OptionsReader>();
            services.AddScoped<AuthenticationDataResolver>();
            services.AddScoped<IAuthenticationDataResolver>(x => x.GetService<AuthenticationDataResolver>());
            services.AddScoped<IAuthenticationDataStore>(x => x.GetService<AuthenticationDataResolver>());
            services.AddScoped<AreaRiservataAuthenticationService>();
            services.AddScoped<IAreaRiservataAuthenticationService>(x => x.GetService<AreaRiservataAuthenticationService>());
            services.AddScoped<TokenProvider>();
            services.AddScoped<ITokenResolver>(x => x.GetService<TokenProvider>());

            // Dati dinamici
            services.AddScoped<CoreDatiDinamiciSearchService>();



            services.AddScoped<IUrlBuilder, UrlBuilder>();
            services.AddScoped<Init.Sigepro.FrontEnd.Infrastructure.UrlsAndPaths.IBaseUrl, StandardBaseUrl>();

            //services.AddScoped<IdMovimentoResolver>();
            //services.AddScoped<IIdMovimentoResolver>(x => x.GetService<IdMovimentoResolver>());

            services.AddScoped<IPostedFileSpecificationFactory, PostedFileSpecificationFactory>();
            services.AddScoped<IValidPostedFileSpecification, ValidPostedFileSpecification>();
            services.AddScoped<IPostedFileSpecificationFactoryAsync, PostedFileSpecificationFactoryAsync>();
            services.AddScoped<IValidPostedFileSpecificationAsync, ValidPostedFileSpecificationAsync>();

            services.AddScoped<FiltriVisuraControlProvider>();
            services.AddScoped<FiltriArchivioIstanzeControlProvider>();
            services.AddScoped<IFiltriVisuraControlProvider, FiltriVisuraControlProvider>();
            services.AddScoped<IFiltriVisuraControlProvider, FiltriArchivioIstanzeControlProvider>();

            services.AddScoped<IGhostscriptAPI, GhostscriptAPI64Core>();

            services.AddScoped<LabelUpdaterService>();
            services.AddScoped<VbgPageTitleService>();

            services.AddScoped<IdDomandaResolver>();
            services.AddScoped<IIdDomandaResolver>(x => x.GetService<IdDomandaResolver>());
            services.AddScoped<IReadDatiDomanda, ReadDatiDomanda>();
            services.AddScoped<PaginatoreStateService>();
            services.AddScoped<GoToWorkflowButtonService>();

            // Urls and paths
            services.AddScoped<ICoreUrlResolver, CoreUrlResolver>();

            return services;
        }
    }
}
