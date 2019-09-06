// -----------------------------------------------------------------------
// <copyright file="GestioneMovimentiNinjectModule.cs" company="">
// TODO: Update copyright text.
// </copyright>
// -----------------------------------------------------------------------

namespace Init.Sigepro.FrontEnd.GestioneMovimenti.NinjectModule
{
    using Init.Sigepro.FrontEnd.GestioneMovimenti.Bootstrap;
    using Init.Sigepro.FrontEnd.GestioneMovimenti.ExternalServices;
    using Init.Sigepro.FrontEnd.GestioneMovimenti.Persistence;
    using Ninject.Modules;
    using Ninject.Web.Common;
    using Init.Sigepro.FrontEnd.GestioneMovimenti.ViewModels;
    using Init.Sigepro.FrontEnd.Infrastructure.Repositories;
    using Init.Sigepro.FrontEnd.GestioneMovimenti.GenerazioneRiepiloghiSchedeDinamiche;
    using Init.Sigepro.FrontEnd.GestioneMovimenti.GestioneMovimento.GestioneMovimentoDiOrigine;
    using Init.Sigepro.FrontEnd.GestioneMovimenti.GestioneMovimento.GestioneMovimentoDaEffettuare;
    using Init.Sigepro.FrontEnd.GestioneMovimenti.GestioneMovimento.Converter;
    using Ninject.Activation.Strategies;
    using Ninject.Activation;
    using Ninject;
    using Init.Sigepro.FrontEnd.Infrastructure.Dispatching;
    using Init.Sigepro.FrontEnd.GestioneMovimenti.Events;

    /// <summary>
    /// Modulo ninject per la configurazione del componente di gestione movimenti
    /// </summary>
    public class GestioneMovimentiNinjectModule : NinjectModule
    {


        public override void Load()
        {
            // Registro i gestori degli eventi dei movimenti (l'event bus viene istanziato per richiesta)
            Bind<EventsBus>().ToMethod(x =>
            {
                var eventBus = new EventsBus();

                var MovimentiBackofficeService = x.Kernel.Get<IMovimentiBackofficeService>();
                var DataContext = x.Kernel.Get<IGestioneMovimentiDataContext>();
                var ScadenzeService = x.Kernel.Get<IScadenzeService>();
                var TrasmissioneService = x.Kernel.Get<ITrasmissioneMovimentoService>();
                var MovimentoDiOrigineRepository = x.Kernel.Get<IMovimentiDiOrigineRepository>();

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

            }).InRequestScope();

            Bind<ICommandSender>().ToMethod(x => (ICommandSender)x.Kernel.GetService(typeof(EventsBus))).InRequestScope(); ;
            Bind<IEventPublisher>().ToMethod(x => (IEventPublisher)x.Kernel.GetService(typeof(EventsBus))).InRequestScope(); ;
            Bind<IEventDispatcher>().To<EventDispatcher>().InRequestScope();







            Bind<IMovimentiDiOrigineRepository>().To<ContextCachedMovimentiDiOrigineRepository>().InRequestScope();
            Bind<IUnitOfWork<GestioneMovimentiDataStore>>().To<GestioneMovimentiHttpDataContext>().InRequestScope();
            Bind<IIdMovimentoResolver>().To<IdMovimentoQuerystringResolver>().InRequestScope();
            Bind<IScadenzeService>().To<ScadenzeService>().InRequestScope();
            Bind<IMovimentiBackofficeService>().To<MovimentiBackofficeService>().InRequestScope();
            Bind<IGestioneMovimentiDataContext>().To<GestioneMovimentiHttpDataContext>().InRequestScope();
            Bind<IMovimentiDaEffettuareRepository>().To<MovimentiDaEffettuareRepository>().InRequestScope();
            Bind<MovimentiBackofficeServiceCreator>().ToSelf().InRequestScope();
            Bind<ITrasmissioneMovimentoService>().To<TrasmissioneMovimentoService>().InRequestScope();
            //Bind<GestioneMovimentiBootstrapper.GestioneMovimentiBootstrapperSettings>().ToSelf().InRequestScope();
            Bind<IGenerazioneRiepilogoSchedeDinamicheService>().To<GenerazioneRiepilogoSchedeDinamicheService>().InRequestScope();
            Bind<IMovimentoDaEffettuareToNotificaAttivitaRequestConverter>().To<MovimentoDaEffettuareToNotificaAttivitaRequestConverter>().InRequestScope();
            Bind<MovimentiDiOrigineRepository>().ToSelf().InRequestScope();


            // View models
            Bind<RiepilogoMovimentoDiOrigineViewModel>().ToSelf().InRequestScope();
            Bind<CompilazioneSchedeDinamicheViewModel>().ToSelf().InRequestScope();
            Bind<CaricamentoRiepiloghiSchedeViewModel>().ToSelf().InRequestScope();
            Bind<FirmaDigitaleAllegatoMovimentoViewModel>().ToSelf().InRequestScope();
            Bind<RiepilogoMovimentoDaEffettuareViewModel>().ToSelf().InRequestScope();
            Bind<SostituzioniDocumentaliViewModel>().ToSelf().InRequestScope();
        }
    }
}
