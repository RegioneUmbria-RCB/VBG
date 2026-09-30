// -----------------------------------------------------------------------
// <copyright file="GestioneMovimentiNinjectModule.cs" company="">
// TODO: Update copyright text.
// </copyright>
// -----------------------------------------------------------------------

namespace Init.Sigepro.FrontEnd.GestioneMovimenti.NinjectModule
{
    using VBG.Shared.Infrastructure.DependencyInjection;
    using Init.Sigepro.FrontEnd.GestioneMovimenti.DatiDinamici;
    using Init.Sigepro.FrontEnd.GestioneMovimenti.Events;
    using Init.Sigepro.FrontEnd.GestioneMovimenti.ExternalServices;
    using Init.Sigepro.FrontEnd.GestioneMovimenti.GenerazioneRiepiloghiSchedeDinamiche;
    using Init.Sigepro.FrontEnd.GestioneMovimenti.GestioneCaricamentoAllegati;
    using Init.Sigepro.FrontEnd.GestioneMovimenti.GestioneMovimento.Converter;
    using Init.Sigepro.FrontEnd.GestioneMovimenti.GestioneMovimento.GestioneMovimentoDaEffettuare;
    using Init.Sigepro.FrontEnd.GestioneMovimenti.GestioneMovimento.GestioneMovimentoDiOrigine;
    using Init.Sigepro.FrontEnd.GestioneMovimenti.Persistence;
    using Init.Sigepro.FrontEnd.GestioneMovimenti.ViewModels;
    using Init.Sigepro.FrontEnd.Infrastructure.Dispatching;
    using Init.Sigepro.FrontEnd.Infrastructure.Repositories;

    /// <summary>
    /// Modulo ninject per la configurazione del componente di gestione movimenti
    /// </summary>
    public static class ConfigurazioneGestioneMovimenti
    {

        public static IDIProvider ConfiguraMovimenti(this IDIProvider services)
        {
            // Registro i gestori degli eventi dei movimenti (l'event bus viene istanziato per richiesta)
            services.AddScoped<EventsBus>(x =>
            {
                var eventBus = new EventsBus();

                var MovimentiBackofficeService = x.GetService<IMovimentiBackofficeService>();
                var DataContext = x.GetService<IGestioneMovimentiDataContext>();
                var ScadenzeService = x.GetService<IScadenzeService>();
                var TrasmissioneService = x.GetService<ITrasmissioneMovimentoService>();
                var MovimentoDiOrigineRepository = x.GetService<IMovimentiDiOrigineRepository>();

                var typesRegistry = EventTypesRegistry.RegisterEvents()
                                                  .FromAssembly(typeof(MovimentoCreato).Assembly)
                                                  .Now();

                var jsonEventStream = new JsonEventStream(typesRegistry, DataContext);
                var movimentiRepository = new RepositoryBase<MovimentoFrontoffice>(eventBus, jsonEventStream);
                var commandHandler = new MovimentiCommandHandler(movimentiRepository, TrasmissioneService, MovimentoDiOrigineRepository);

                var movimentiReadRepository = new MovimentiDaEffettuareRepository(ScadenzeService, DataContext, MovimentiBackofficeService);
                var eventHandler = new MovimentiEventHandler(MovimentiBackofficeService,
                                                                         movimentiReadRepository,
                                                                         ScadenzeService);

                var riepiloghiSchedeEventHandler = new RiepiloghiSchedeDinamicheMovimentoEventHandler(eventBus, movimentiReadRepository, MovimentoDiOrigineRepository);

                EventHandlerConfigurator.Configure()
                                        .WithHandler(commandHandler)
                                        .WithHandler(eventHandler)
                                        .WithHandler(riepiloghiSchedeEventHandler)
                                        .OnBus(eventBus);

                return eventBus;

            });

            services.AddScoped<ICommandSender>(x => x.GetService<EventsBus>());
            services.AddScoped<IEventPublisher>(x => x.GetService<EventsBus>());
            services.AddScoped<IEventDispatcher, EventDispatcher>();


            services.AddScoped<IMovimentiDiOrigineRepository, ContextCachedMovimentiDiOrigineRepository>();
            services.AddScoped<IUnitOfWork<GestioneMovimentiDataStore>, GestioneMovimentiHttpDataContext>();

            services.AddScoped<IScadenzeService, ScadenzeService>();
            services.AddScoped<IMovimentiBackofficeService, MovimentiBackofficeService>();
            services.AddScoped<GestioneMovimentiHttpDataContext>();
            services.AddScoped<IGestioneMovimentiDataContext>(x => x.GetService<GestioneMovimentiHttpDataContext>());
            services.AddScoped<IMovimentiDaEffettuareRepository, MovimentiDaEffettuareRepository>();
            services.AddScoped<MovimentiBackofficeServiceCreator>();
            services.AddScoped<ITrasmissioneMovimentoService, TrasmissioneMovimentoService>();
            //Bind<GestioneMovimentiBootstrapper.GestioneMovimentiBootstrapperSettings>();
            services.AddScoped<IGenerazioneRiepilogoSchedeDinamicheService, GenerazioneRiepilogoSchedeDinamicheService>();
            services.AddScoped<SchedeMovimentiLoaderFactory>();
            services.AddScoped<IMovimentoDaEffettuareToNotificaAttivitaRequestConverter, MovimentoDaEffettuareToNotificaAttivitaRequestConverter>();
            services.AddScoped<MovimentiDiOrigineRepository>();


            // View models
            services.AddScoped<RiepilogoMovimentoDiOrigineViewModel>();
            services.AddScoped<CompilazioneSchedeDinamicheViewModel>();
            services.AddScoped<CaricamentoRiepiloghiSchedeViewModel>();
            services.AddScoped<FirmaDigitaleAllegatoMovimentoViewModel>();
            services.AddScoped<RiepilogoMovimentoDaEffettuareViewModel>();
            services.AddScoped<SostituzioniDocumentaliViewModel>();
            services.AddScoped<CaricamentoAllegatiMovimentoViewModel>();
            services.AddScoped<IntegrazioneSITDaScadenzarioViewModel>();

            services.AddScoped<MovimentiIstanzeManager>();
            services.AddScoped<CaricamentoAllegatiService>();

            return services;
        }
    }




    ///// <summary>
    ///// Modulo ninject per la configurazione del componente di gestione movimenti
    ///// </summary>
    //public class GestioneMovimentiNinjectModule : NinjectModule
    //{


    //    public override void Load()
    //    {
    //        // Registro i gestori degli eventi dei movimenti (l'event bus viene istanziato per richiesta)
    //        this.Bind<EventsBus>().ToMethod(x =>
    //        {
    //            var eventBus = new EventsBus();

    //            var MovimentiBackofficeService = x.Kernel.Get<IMovimentiBackofficeService>();
    //            var DataContext = x.Kernel.Get<IGestioneMovimentiDataContext>();
    //            var ScadenzeService = x.Kernel.Get<IScadenzeService>();
    //            var TrasmissioneService = x.Kernel.Get<ITrasmissioneMovimentoService>();
    //            var MovimentoDiOrigineRepository = x.Kernel.Get<IMovimentiDiOrigineRepository>();

    //            var typesRegistry = EventTypesRegistry.RegisterEvents()
    //                                              .FromAssembly(typeof(MovimentoCreato).Assembly)
    //                                              .Now();

    //            var jsonEventStream = new JsonEventStream(typesRegistry, DataContext);
    //            var movimentiRepository = new RepositoryBase<MovimentoFrontoffice>(eventBus, jsonEventStream);
    //            var commandHandler = new MovimentiCommandHandler(movimentiRepository, TrasmissioneService, MovimentoDiOrigineRepository);

    //            var movimentiReadRepository = new MovimentiDaEffettuareRepository(ScadenzeService, DataContext, MovimentiBackofficeService);
    //            var eventHandler = new MovimentiEventHandler(MovimentiBackofficeService,
    //                                                                     movimentiReadRepository,
    //                                                                     ScadenzeService);

    //            var riepiloghiSchedeEventHandler = new RiepiloghiSchedeDinamicheMovimentoEventHandler(eventBus, movimentiReadRepository, MovimentoDiOrigineRepository);

    //            EventHandlerConfigurator.Configure()
    //                                    .WithHandler(commandHandler)
    //                                    .WithHandler(eventHandler)
    //                                    .WithHandler(riepiloghiSchedeEventHandler)
    //                                    .OnBus(eventBus);

    //            return eventBus;

    //        }).InTransientScope();

    //        this.Bind<ICommandSender>().ToMethod(x => (ICommandSender)x.Kernel.GetService(typeof(EventsBus))).InTransientScope(); ;
    //        this.Bind<IEventPublisher>().ToMethod(x => (IEventPublisher)x.Kernel.GetService(typeof(EventsBus))).InTransientScope(); ;
    //        this.Bind<IEventDispatcher>().To<EventDispatcher>().InTransientScope();







    //        this.Bind<IMovimentiDiOrigineRepository>().To<ContextCachedMovimentiDiOrigineRepository>().InTransientScope();
    //        this.Bind<IUnitOfWork<GestioneMovimentiDataStore>>().To<GestioneMovimentiHttpDataContext>().InTransientScope();

    //        this.Bind<IScadenzeService>().To<ScadenzeService>().InTransientScope();
    //        this.Bind<IMovimentiBackofficeService>().To<MovimentiBackofficeService>().InTransientScope();
    //        this.Bind<IGestioneMovimentiDataContext>().To<GestioneMovimentiHttpDataContext>().InTransientScope();
    //        this.Bind<IMovimentiDaEffettuareRepository>().To<MovimentiDaEffettuareRepository>().InTransientScope();
    //        this.Bind<MovimentiBackofficeServiceCreator>().ToSelf().InTransientScope();
    //        this.Bind<ITrasmissioneMovimentoService>().To<TrasmissioneMovimentoService>().InTransientScope();
    //        //Bind<GestioneMovimentiBootstrapper.GestioneMovimentiBootstrapperSettings>().ToSelf().InTransientScope();
    //        this.Bind<IGenerazioneRiepilogoSchedeDinamicheService>().To<GenerazioneRiepilogoSchedeDinamicheService>().InTransientScope();
    //        this.Bind<IMovimentoDaEffettuareToNotificaAttivitaRequestConverter>().To<MovimentoDaEffettuareToNotificaAttivitaRequestConverter>().InTransientScope();
    //        this.Bind<MovimentiDiOrigineRepository>().ToSelf().InTransientScope();


    //        // View models
    //        this.Bind<RiepilogoMovimentoDiOrigineViewModel>().ToSelf().InTransientScope();
    //        this.Bind<CompilazioneSchedeDinamicheViewModel>().ToSelf().InTransientScope();
    //        this.Bind<CaricamentoRiepiloghiSchedeViewModel>().ToSelf().InTransientScope();
    //        this.Bind<FirmaDigitaleAllegatoMovimentoViewModel>().ToSelf().InTransientScope();
    //        this.Bind<RiepilogoMovimentoDaEffettuareViewModel>().ToSelf().InTransientScope();
    //        this.Bind<SostituzioniDocumentaliViewModel>().ToSelf().InTransientScope();
    //    }
    //}
}
