[assembly: WebActivatorEx.PreApplicationStartMethod(typeof(VBG.Backend.Protocollo.WebApp.Legacy.App_Start.NinjectWebCommon), "Start")]
[assembly: WebActivatorEx.ApplicationShutdownMethodAttribute(typeof(VBG.Backend.Protocollo.WebApp.Legacy.App_Start.NinjectWebCommon), "Stop")]

namespace VBG.Backend.Protocollo.WebApp.Legacy.App_Start
{
    using VBG.Shared.Infrastructure.DependencyInjection;
    using Init.SIGePro.Manager.IOC;
    using Init.SIGePro.Manager.Logic.GestioneSchedeAttivita;
    using Microsoft.Web.Infrastructure.DynamicModuleHelper;
    using Ninject;
    using Ninject.Web.Common;
    using Ninject.Web.Common.WebHost;
    using System;
    using System.Web;
    using System.Web.Http;
    using Vbg.EventBus;
    using Vbg.EventBus.IOC;
    using VBG.Backend.Protocollo.AppLogic.Legacy;
    using VBG.Backend.Protocollo.AppLogic.Shared.PerEvitareLaDependencyInjection;
    using VBG.Backend.Protocollo.AppLogic.Shared.Services;

    public static class NinjectWebCommon
    {
        private static readonly Bootstrapper bootstrapper = new Bootstrapper();

        /// <summary>
        /// Starts the application
        /// </summary>
        public static void Start()
        {
            DynamicModuleUtility.RegisterModule(typeof(OnePerRequestHttpModule));
            DynamicModuleUtility.RegisterModule(typeof(NinjectHttpModule));
            bootstrapper.Initialize(CreateKernel);
        }

        /// <summary>
        /// Stops the application.
        /// </summary>
        public static void Stop()
        {
            bootstrapper.ShutDown();
        }

        /// <summary>
        /// Creates the kernel that will manage your application.
        /// </summary>
        /// <returns>The created kernel.</returns>
        private static IKernel CreateKernel()
        {
            var kernel = new StandardKernel();
            try
            {
                kernel.Bind<Func<IKernel>>().ToMethod(ctx => () => new Bootstrapper().Kernel);
                kernel.Bind<IHttpModule>().To<HttpApplicationInitializationHttpModule>();

                RegisterServices(kernel);

                return kernel;
            }
            catch
            {
                kernel.Dispose();
                throw;
            }
        }

        /// <summary>
        /// Load your modules or register your services here!
        /// </summary>
        /// <param name="kernel">The kernel.</param>
        private static void RegisterServices(IKernel kernel)
        {
            kernel.ToDIProvider()
                .RegistraSigeproManager()
                .RegistraEventBusModule()
                .RegistraProtocolloLegacy();

            // NON RIMUOVERE: Utilizzato per inizializzare il container statico del kernel
            var temp = kernel.Get<IKernelContainer>();

            var registry = kernel.Get<EventSubscribersRegistry>();
            registry.Load(new SigeproManagaerEventBusModule());
            registry.Load(new SchedeAttivitaEventBusModule());

            var protocolloFactory = kernel.Get<IProtocolloFactory>();
            ProtocolloFactoryProvider.Factory = protocolloFactory;

            // lo inizializzo passando una classe qualunque del namespace VBG.Backend.Protocollo.AppLogic.Legacy
            TipiProtocolloRegistry.Initialize(typeof(PROTOCOLLO_ACARIS).Assembly);


            GlobalConfiguration.Configuration.DependencyResolver = new NinjectDependencyResolver(kernel);
        }
    }
}
