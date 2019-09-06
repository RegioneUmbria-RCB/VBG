// -----------------------------------------------------------------------
// <copyright file="GestioneMovimentiBootstrapper.cs" company="">
// TODO: Update copyright text.
// </copyright>
// -----------------------------------------------------------------------

namespace Init.Sigepro.FrontEnd.GestioneMovimenti.Bootstrap
{
    using CuttingEdge.Conditions;
    using Init.Sigepro.FrontEnd.GestioneMovimenti.Events;
    using Init.Sigepro.FrontEnd.GestioneMovimenti.ExternalServices;
    using Init.Sigepro.FrontEnd.GestioneMovimenti.GestioneMovimento.GestioneMovimentoDaEffettuare;
    using Init.Sigepro.FrontEnd.GestioneMovimenti.GestioneMovimento.GestioneMovimentoDiOrigine;
    using Init.Sigepro.FrontEnd.GestioneMovimenti.Persistence;
    using Init.Sigepro.FrontEnd.Infrastructure.Dispatching;
    using Init.Sigepro.FrontEnd.Infrastructure.Repositories;
    using Ninject;

	/// <summary>
	/// Responsabile dell'inizializzazione del componente di gestione movimenti dal frontoffice
	/// </summary>
	public class GestioneMovimentiBootstrapper
	{
		/// <summary>
		/// Configurazione dei parametri di inizializzazione del componente di gestione movimenti dal frontoffice
		/// </summary>
		public class GestioneMovimentiBootstrapperSettings
		{
			public readonly EventsBus Bus;
			public readonly IMovimentiBackofficeService MovimentiBackofficeService;
			public readonly IGestioneMovimentiDataContext DataContext;
			public readonly IScadenzeService ScadenzeService;
			public readonly ITrasmissioneMovimentoService TrasmissioneService;
            public readonly IMovimentiDiOrigineRepository MovimentoDiOrigineRepository;

			public GestioneMovimentiBootstrapperSettings( EventsBus bus, IMovimentiBackofficeService movimentiBackofficeService, 
															IGestioneMovimentiDataContext dataContext,
															IScadenzeService scadenzeService,
															ITrasmissioneMovimentoService trasmissioneService,
                                                            IMovimentiDiOrigineRepository movimentoDiOrigineRepository)
			{
				Condition.Requires(bus, "bus").IsNotNull();
				Condition.Requires(movimentiBackofficeService, "movimentiBackofficeService").IsNotNull();
				Condition.Requires(dataContext, "dataContext").IsNotNull();
				Condition.Requires(scadenzeService, "scadenzeService").IsNotNull();
				Condition.Requires(trasmissioneService, "trasmissioneService").IsNotNull();
                Condition.Requires(movimentoDiOrigineRepository, "movimentoDiOrigineRepository").IsNotNull();
                

				this.Bus = bus;
				this.MovimentiBackofficeService = movimentiBackofficeService;
				this.DataContext = dataContext;
				this.ScadenzeService = scadenzeService;
				this.TrasmissioneService = trasmissioneService;
                this.MovimentoDiOrigineRepository = movimentoDiOrigineRepository;
			}

            
        }

		/// <summary>
		/// Inizializzazione il componente di gestione movimenti dal frontoffice
		/// </summary>
		/// <param name="configuration">Parametri di configurazione</param>
		public static void Bootstrap( GestioneMovimentiBootstrapperSettings configuration )
		{
			

			var eventBus = configuration.Bus;

			var typesRegistry = EventTypesRegistry.RegisterEvents()
												  .FromAssembly(typeof(MovimentoCreato).Assembly)
												  .Now();

			var jsonEventStream		= new JsonEventStream(typesRegistry, configuration.DataContext);
			var movimentiRepository = new RepositoryBase<MovimentoFrontoffice>(configuration.Bus, jsonEventStream);
			var commandHandler		= new MovimentiCommandHandler(movimentiRepository, configuration.TrasmissioneService, configuration.MovimentoDiOrigineRepository);

			var movimentiReadRepository = new MovimentiDaEffettuareRepository(configuration.ScadenzeService, configuration.DataContext, configuration.MovimentiBackofficeService );
			var eventHandler			= new MovimentiEventHandler( configuration.MovimentiBackofficeService,
																	 movimentiReadRepository, 
																	 configuration.ScadenzeService );

            var riepiloghiSchedeEventHandler = new RiepiloghiSchedeDinamicheMovimentoEventHandler(eventBus, movimentiReadRepository, configuration.MovimentoDiOrigineRepository);

		    EventHandlerConfigurator.Configure()
									.WithHandler(commandHandler)
									.WithHandler(eventHandler)
									.WithHandler( riepiloghiSchedeEventHandler )
                                    .OnBus(eventBus);
		}
	}
}
