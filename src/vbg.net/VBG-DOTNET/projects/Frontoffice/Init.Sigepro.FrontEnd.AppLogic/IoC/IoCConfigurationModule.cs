using VBG.Shared.Infrastructure.DependencyInjection;
using Init.Sigepro.FrontEnd.AppLogic.Adapters.IOC;
using Init.Sigepro.FrontEnd.AppLogic.Autenticazione.Formule;
using Init.Sigepro.FrontEnd.AppLogic.Autenticazione.Vbg;
using Init.Sigepro.FrontEnd.AppLogic.Autenticazione.Vbg.AutenticazioneUtente;
using Init.Sigepro.FrontEnd.AppLogic.Autenticazione.Vbg.TokenApplicazione;
using Init.Sigepro.FrontEnd.AppLogic.Autenticazione.Vbg.TraduzioneIdComune;
using Init.Sigepro.FrontEnd.AppLogic.Configurazione.ServiceReference;
using Init.Sigepro.FrontEnd.AppLogic.ConversionePDF.DI;
using Init.Sigepro.FrontEnd.AppLogic.ConversioneVersioniDataSetDomanda;
using Init.Sigepro.FrontEnd.AppLogic.CopiaDomanda;
using Init.Sigepro.FrontEnd.AppLogic.DataAccess;
using Init.Sigepro.FrontEnd.AppLogic.DatiDinamici.IoC;
using Init.Sigepro.FrontEnd.AppLogic.GenerazioneDocumentiDomanda.GenerazioneCertificatoDiInvio;
using Init.Sigepro.FrontEnd.AppLogic.GenerazioneDocumentiDomanda.GenerazioneCertificatoDiInvio.AllegaCertificatoDiInvio;
using Init.Sigepro.FrontEnd.AppLogic.GenerazioneDocumentiDomanda.GenerazioneCertificatoDiInvio.LetturaXmlDomandaBackend;
using Init.Sigepro.FrontEnd.AppLogic.GenerazioneDocumentiDomanda.GenerazioneCertificatoDiInvio.StrategiaLetturaRiepilogo;
using Init.Sigepro.FrontEnd.AppLogic.GenerazioneDocumentiDomanda.GenerazioneDocumentiDomanda;
using Init.Sigepro.FrontEnd.AppLogic.GenerazioneDocumentiDomanda.GenerazioneRiepilogoDomanda;
using Init.Sigepro.FrontEnd.AppLogic.GenerazioneDocumentiDomanda.GenerazioneRiepilogoDomanda.GestioneSostituzioneSegnapostoRiepilogo;
using Init.Sigepro.FrontEnd.AppLogic.GenerazioneDocumentiDomanda.GenerazioneRiepilogoDomanda.GestioneSostituzioneSegnapostoRiepilogo.LetturaDatiDinamici.LetturaDaDomandaOnline;
using Init.Sigepro.FrontEnd.AppLogic.GestioneAccessoAtti.IOC;
using Init.Sigepro.FrontEnd.AppLogic.GestioneAllegatiDomanda.DependencyInjection;
using Init.Sigepro.FrontEnd.AppLogic.GestioneAnagrafiche;
using Init.Sigepro.FrontEnd.AppLogic.GestioneBookmarks;
using Init.Sigepro.FrontEnd.AppLogic.GestioneCodeMessaggi;
using Init.Sigepro.FrontEnd.AppLogic.GestioneCommissioni.IoC;
using Init.Sigepro.FrontEnd.AppLogic.GestioneComuni.IoC;
using Init.Sigepro.FrontEnd.AppLogic.GestioneConfigurazioneContenuti;
using Init.Sigepro.FrontEnd.AppLogic.GestioneConversioneFiles.IoC;
using Init.Sigepro.FrontEnd.AppLogic.GestioneDatiExtra;
using Init.Sigepro.FrontEnd.AppLogic.GestioneDelegaATrasmettere.DI;
using Init.Sigepro.FrontEnd.AppLogic.GestioneDomicilioElettronico.DI;
using Init.Sigepro.FrontEnd.AppLogic.GestioneEndoprocedimenti.IoC;
using Init.Sigepro.FrontEnd.AppLogic.GestioneInpsInail.IoC;
using Init.Sigepro.FrontEnd.AppLogic.GestioneIntegrazioneLDP.AreeUsoPubblicoLivorno;
using Init.Sigepro.FrontEnd.AppLogic.GestioneIntegrazioneLDP.PresentazionePraticheEdilizieSiena;
using Init.Sigepro.FrontEnd.AppLogic.GestioneInterventi;
using Init.Sigepro.FrontEnd.AppLogic.GestioneLocalizzazioni;
using Init.Sigepro.FrontEnd.AppLogic.GestioneLocalizzazioni.Modena;
using Init.Sigepro.FrontEnd.AppLogic.GestioneLocalizzazioni.SIC.IoC;
using Init.Sigepro.FrontEnd.AppLogic.GestioneLoghi;
using Init.Sigepro.FrontEnd.AppLogic.GestioneMenu.IoC;
using Init.Sigepro.FrontEnd.AppLogic.GestioneMetadatiToken;
using Init.Sigepro.FrontEnd.AppLogic.GestioneOggetti.IoC;
using Init.Sigepro.FrontEnd.AppLogic.GestioneOnceOnly;
using Init.Sigepro.FrontEnd.AppLogic.GestioneOneri;
using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneAnagrafiche.LogicaRisoluzioneSoggetti;
using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneAnagrafiche.Sincronizzazione;
using Init.Sigepro.FrontEnd.AppLogic.GestioneProssimiPassi;
using Init.Sigepro.FrontEnd.AppLogic.GestioneQuestionario.IOC;
using Init.Sigepro.FrontEnd.AppLogic.GestioneRisorseTestuali;
using Init.Sigepro.FrontEnd.AppLogic.GestioneServiziFVG;
using Init.Sigepro.FrontEnd.AppLogic.GestioneServiziFVG.IoC;
using Init.Sigepro.FrontEnd.AppLogic.GestioneStatoCompilazioneStep;
using Init.Sigepro.FrontEnd.AppLogic.GestioneTabelleDiBase;
using Init.Sigepro.FrontEnd.AppLogic.GestioneTipiSoggetto;
using Init.Sigepro.FrontEnd.AppLogic.GestioneVbgConsole.IoC;
using Init.Sigepro.FrontEnd.AppLogic.GestioneVisuraIstanza;
using Init.Sigepro.FrontEnd.AppLogic.GestioneVisuraIstanza.V1;
using Init.Sigepro.FrontEnd.AppLogic.InvioDomanda;
using Init.Sigepro.FrontEnd.AppLogic.InvioDomanda.MessaggiErroreInvio;
using Init.Sigepro.FrontEnd.AppLogic.Livorno.PortaleCittadino;
using Init.Sigepro.FrontEnd.AppLogic.PrecompilazionePDF;
using Init.Sigepro.FrontEnd.AppLogic.PrecompilazionePDF.DI;
using Init.Sigepro.FrontEnd.AppLogic.PresentazioneIstanze.DependencyInjection;
using Init.Sigepro.FrontEnd.AppLogic.ReadInterface;
using Init.Sigepro.FrontEnd.AppLogic.ReadInterface.Imp;
using Init.Sigepro.FrontEnd.AppLogic.RedirectFineDomanda;
using Init.Sigepro.FrontEnd.AppLogic.RedirectFineDomanda.CopiaDomanda;
using Init.Sigepro.FrontEnd.AppLogic.Repositories.Interfaces;
using Init.Sigepro.FrontEnd.AppLogic.Repositories.WebServices;
using Init.Sigepro.FrontEnd.AppLogic.SalvataggioDomanda;
using Init.Sigepro.FrontEnd.AppLogic.SalvataggioDomanda.EliminazioneDomanda;
using Init.Sigepro.FrontEnd.AppLogic.SalvataggioDomanda.Repositories;
using Init.Sigepro.FrontEnd.AppLogic.ServiceCreators;
using Init.Sigepro.FrontEnd.AppLogic.Services.Domanda;
using Init.Sigepro.FrontEnd.AppLogic.Services.Navigation;
using Init.Sigepro.FrontEnd.AppLogic.STC.Configuration;
using Init.Sigepro.FrontEnd.AppLogic.Utils;
using Init.Sigepro.FrontEnd.AppLogic.Utils.DatiDomanda;
using Init.Sigepro.FrontEnd.AppLogic.VerificaFirmaDigitale.IOC;
using Init.Sigepro.FrontEnd.AppLogic.VerificaSoggettiFirmatari;
using Init.Sigepro.FrontEnd.AppLogic.Wrappers;
using Init.Sigepro.FrontEnd.Infrastructure.DatesAndTimes;
using Init.Sigepro.FrontEnd.Infrastructure.IoC;
using Init.Sigepro.FrontEnd.Infrastructure.UrlsAndPaths;
using Init.Sigepro.FrontEnd.Infrastructure.Web;
using Microsoft.Extensions.DependencyInjection;
using System.Net.Http;

namespace Init.Sigepro.FrontEnd.AppLogic.IoC
{
    public static class AreaRiservataIOCConfiguration
    {
        public static IDIProvider ConfiguraTokenApplicazione(this IDIProvider services)
        {
            services.AddScoped<TokenApplicazioneRepository>();
            services.AddScoped<ITokenApplicazioneService, TokenApplicazioneService>();
            services.AddScoped<IAliasToIdComuneTranslator, AliasToIdComuneTranslator>();

            return services;
        }

        public static IDIProvider ConfiguraAreaRiservata(this IDIProvider services)
        {
            services.AddScoped<SalvataggioDirettoStrategy>();
#if NET9_0_OR_GREATER
            services.AddScoped<ISalvataggioDomandaStrategy, SalvataggioCachedStrategy>();
            services.AddSingleton<IAreaRiservataRuntimeEnvironment, AreaRiservataCoreRuntimeEnvironment>();
#endif

#if NET48
            services.AddSingleton<IAreaRiservataRuntimeEnvironment, AreaRiservataRuntimeEnvironment>();

            services.AddSingleton(_ =>
            {
                var httpClientServices = new ServiceCollection();
                httpClientServices.AddHttpClient();
                var provider = httpClientServices.BuildServiceProvider();
                return provider.GetRequiredService<IHttpClientFactory>();
            });
#endif
            services.AddScoped<WorkflowInvioDomanda>();
            services.AddScoped<IInvioDomandaStrategy, InvioDomandaSTCStrategy>();

            services.AddScoped<XmlDomandaBackendStrategy>();

            services.AddScoped<CertificatoDiInvioService>();
            services.AddScoped<RiepilogoDomandaReader>();
            services.AddScoped<AliasToIdComuneRepository>();
            services.AddScoped<MessaggioInvioFallito>();
            services.AddScoped<SchedeDrupalWsClient>();
            services.AddScoped<PdfUtilsServiceCreator>();
            services.AddScoped<TabelleDiBaseServiceCreator>();
            services.AddScoped<LivornoSITServiceProxy>();
            services.AddScoped<LDPServiceProxy>();
            services.AddScoped<LoghiAreaRiservataService>();

            services.AddScoped<V5DataSetSerializer>();
            services.AddScoped<ConfigurazioneContenutiServiceCreator>();

            services.AddTransient<IDateTimeServiceWrapper, DateTimeServiceWrapper>();



            // Gestione interventi
            services.AddScoped<IInterventiRepository, WsInterventiRepository>();
            services.AddScoped<IInterventiV3Service, WsInterventiRepository>();
            services.AddScoped<IResolveDescrizioneIntervento, ResolveDescrizioneIntervento>();
            services.AddScoped<InterventiServiceCreator>();

            // Gestione configurazione area riservata
            services.AddScoped<ConfigurazioneAreaRiservataServiceCreator>();
            services.AddScoped<IConfigurazioneAreaRiservataRepository, WsConfigurazioneAreaRiservataRepository>();


            services.AddScoped<AlboPretorioServiceCreator>();
            services.AddScoped<IAlboPretorioRepository, WsAlboPretorioRepository>();

            services.AddScoped<CreazioneAnagrafeServiceCreator>();
            services.AddScoped<RicercaAnagraficheServiceCreator>();
            services.AddScoped<BookmarksServiceClientCreator>();
            // services.AddScoped<AtecoServiceCreator>();
            // services.AddScoped<IAtecoRepository, WsAtecoRepository>();
            services.AddScoped<CampiRicercaPraticheServiceCreator>();
            services.AddScoped<WsCampiRicercaVisuraRepository>();
            services.AddScoped<ICampiRicercaVisuraRepository, WsCampiRicercaVisuraRepository>();
            // services.AddScoped<ICartRepository, WsCartRepository>();

            services.AddScoped<IConfigurazioneContenutiRepository, WsConfigurazioneContenutiRepository>();
            services.AddScoped<IConfigurazioneVbgRepository, WsConfigurazioneVbgRepository>();

            services.AddScoped<WsDatiDomandaServiceCreator>();
            services.AddScoped<IDatiDomandaFoRepository, WsDatiDomandaFoRepository>();
            services.AddScoped<IEliminazioneBozzaDomandaService, EliminazioneDomandaService>();
            // services.AddScoped<IDomandeEndoRepository, WsDomandeEndoRepository>();
            services.AddScoped<IElenchiProfessionaliRepository, WsElenchiProfessionaliRepository>();
            services.AddScoped<IFormeGiuridicheRepository, WsFormeGiuridicheRepository>();
            services.AddScoped<IInterventiAllegatiRepository, WsInterventiAllegatiRepository>();
            services.AddScoped<IIstanzePresentateRepository, WsIstanzePresentateRepository>();
            //			Bind<IMovimentiRepository,WsMovimentiRepository>();
            // services.AddScoped<IMessaggiFrontofficeRepository, WsMessaggiFrontofficeRepository>();


            //Bind<IScadenzeRepository,ScadenzeRepository>();
            //services.AddScoped<ISoftwareRepository, WsSoftwareRepository>();
            //services.AddScoped<ISottoscrizioniRepository, WsSottoscrizioniRepository>();
            services.AddScoped<IStatiIstanzaRepository, WsStatiIstanzaRepository>();
            services.AddScoped<IStradarioRepository, WsStradarioRepository>();
            services.AddScoped<ITitoliRepository, WsTitoliRepository>();
            //Bind<IVisuraRepository,WsVisuraRepository>();

            // Tipi soggetto


            services.AddScoped<ILogicaSincronizzazioneTipiSoggetto, LogicaSincronizzazioneTipiSoggetto>();

            services.AddScoped<IsUtenteAnonimoSpecification>();
            //services.AddScoped<StradarioServiceCreator>();
            // File converter

            // Check Browser
            services.AddScoped<ICheckBrowserService, CheckBrowserService>();

            //WrapperService
            services.AddScoped<IGuidWrapperService, GuidServiceWrapper>();

            // Services
            services.AddScoped<LocalizzazioniService>();
            services.AddScoped<DomandeOnlineService>();
            services.AddScoped<EventiDomandaService>();
            services.AddScoped<InvioDomandaAreaRiservataService>();
            services.AddScoped<StatoDomandaPresentataService>();
            services.AddScoped<ProcureService>();

            // Riepilogo domanda

            services.AddScoped<SostituzioneSegnapostoRiepilogoService>();
            services.AddScoped<DomandaOnlineDatiDinamiciReaderFactory>();
            services.AddScoped<GenerazioneRiepilogoDomandaLegacyService>();
            services.AddScoped<FacSimileDomandaService>();
            services.AddScoped<ModelloDomandaReaderFactory>();

            services.AddScoped<GenerazioneRiepilogoDomandaService>();

            services.AddScoped<RiepilogoDomandaDaVisuraService>();
            // services.AddScoped<DatiDomandaSalvataggioInterventoService>();
            services.AddScoped<DatiDomandaService>();

            services.ConfiguraTokenApplicazione();

            services.AddScoped<GenerazioneDocumentoDomandaService>();
            services.AddScoped<ValidazioneSoggettiRiepilogoDomandaService>();

            // Generazione certificato di invio
#if NET48_OR_GREATER
            services.AddScoped<IReadFacade, ReadFacadeImp>();
            services.AddScoped<IReadDatiDomanda, ReadFacadeImp>();
#endif
            services.AddScoped<GeneratoreCertificatoDiInvio>();
            services.AddScoped<CertificatoDiInvioFinderDaMetadati>();
            services.AddScoped<CertificatoDiInvioFinderDaVisura>();
            services.AddScoped<ICertificatoDiInvioFinder, CompositeCertificatoInvioFinder>();
            services.AddScoped<IAllegaCertificatoDiInvioService, AllegaCertificatoDiInvioService>();

            // Ricerca del riepilogo della domanda online
            services.AddScoped<IndividuazioneCertificatoInvioDaConfigurazione>();
            services.AddScoped<IndividuazioneCertificatoInvioDaProcedura>();
            services.AddScoped<IStrategiaIndividuazioneCertificatoInvio, IndividuazioneCertificatoInvioDaProceduraOConfigurazione>();

            // Messaggi notifica
            //services.AddScoped<IMessaggiNotificaInvioService, MessaggiNotificaInvioService>();
            services.AddScoped<IMessaggioErroreInvioService, MessaggioErroreInvioService>();

            // Dati dinamici

            // Segnaposto schede dinamiche
            services.ConfiguraSegnapostoRiepilogo();
            services.AddScoped<IGeneratoreHtmlSchedeDinamiche, GeneratoreHtmlSchedeDinamiche>();

            // Redirect a fine presentazione  e copia dati domanda
            services.AddScoped<IRedirectFineDomandaService, RedirectFineDomandaService>();
            services.AddScoped<ICopiaDatiDomandaService, CopiaDatiDomandaService>();

            // Gestione FVG-SOL
            services.AddScoped<FVGWebServiceProxyFactory>();
            services.AddScoped<IFVGWebServiceProxy>((ctxt) =>
            {
                var svc = ctxt.GetService<FVGWebServiceProxyFactory>();

                return svc.CreateService();
            });

            // Autenticazione
            services.AddScoped<IVbgAuthenticationService, VbgAuthenticationService>();
            services.AddScoped<IAuthenticationUrlBuilder, AuthenticationUrlBuilder>();
            services.AddScoped<IUserCredentialsStorage, UserCredentialsStorage>();



            // infrastruttura
            services.AddScoped<RisorseTestualiServiceCreator>();
            services.AddScoped<IRisorseTestualiService, CachedRisorseTestualiService>();
            services.AddScoped<IUrlEncoder, HttpUtilityUrlEncoder>();
            services.AddScoped<SigeproSecurityProxy>();

            // Presentazione domanda

            services.AddScoped<ILogicaRisoluzioneTecnico, LogicaRisoluzioneTecnico>();

            // services.AddScoped<IInpsInailService, InpsInailService>();

            services.AddScoped<IBookmarksService, BookmarksService>();
            services.AddScoped<ILocalizzazioniService, LocalizzazioniService>();
            services.AddScoped<IPortaleCittadinoService, PortaleCittadinoService>();

            services.AddScoped<DatiExtraService>();
            services.AddScoped<IDatiExtraService>(x => x.GetService<DatiExtraService>());

            //Copia domanda da template istanza di backoffice
            services.AddScoped<CopiaDomandaService>();
            services.AddScoped<CopiaDomandaAltriDatiAdapter>();
            services.AddScoped<CopiaDomandaAnagraficheAdapter>();
            services.AddScoped<CopiaDomandaLocalizzazioniAdapter>();
            services.AddScoped<CopiaDomandaDatiDinamiciAdapter>();
            services.AddScoped<CopiaDomandaEndoprocedimentiAdapter>();
            services.AddScoped<CopiaDomandaProcureAdapter>();
            services.AddScoped<CopiaDomandaAllegatiAdapter>();

            services.AddScoped<ICurrentDateTimeProvider, CurrentDateTimeProvider>();

            services.AddScoped<ILocalizzazioniModenaService, LocalizzazioniModenaService>();

            //Download ZIP della domanda
            services.AddScoped<DownloadDomandaZIPService>();

            services.AddScoped<Tls12Utils>();
            services.AddScoped<AutenticazioneFormuleService>();

            services.AddScoped<SoggettiFirmatariServiceCreator>();
            services.AddScoped<VerificaSoggettiFirmatariService>();

            services.AddScoped<DatiDomandaDumper>();

            services.ConfiguraGestioneAnagrafiche();

            services.ConfiguraInfrastruttura()
                .ConfiguraPrecompilazionePDF()
                .ConfiguraConversionePDF()
                .ConfiguraGestioneFilesExcel()
                .ConfiguraGestioneOggetti()
                .ConfiguraParametriConfigurazione()
                .ConfiguraSIT()
                .ConfiguraWorkflow()
                .ConfiguraScrivaniaEntiTerzi()
                .ConfiguraAccessoAtti()
                .ConfiguraGestioneTransiti()
                .ConfiguraServiziFVG()
                .ConfiguraVbgConsole()
                .ConfiguraGestioneEndo()
                .ConfiguraDatiDinamici()
                .ConfiguraConversioneFiles()
                .ConfigurCommissioni()
                .ConfiguraSigeproAdapters()
                .ConfiguraQuestionarioFo()
                .ConfiguraAllegatiDomanda()
                .ConfiguraGestioneLocalizzazioni()
                .ConfiguraVerificaFirmaDigitale()
                .ConfiguraMenu()
                .ConfiguraGestioneComuni()
                .ConfiguraGestioneInpsInail()
                .ConfiguraDelegaATrasmettere()
                .ConfiguraDomicilioElettronico()
                .ConfiguraGestioneCodeMessaggi()
                .ConfiguraGestioneOneri()
                .ConfiguraOnceOnly()
                .ConfiguraGestioneStatoCompilazioneStep()
                .ConfiguraGestioneTipiSoggetto()
                .ConfiguraGestioneProssimiPassi()
                .ConfiguraSic()
                .ConfiguraMetadatiToken()
                .ConfiguraStc()
                .ConfiguraDataAccess();

            return services;
        }
    }
}
