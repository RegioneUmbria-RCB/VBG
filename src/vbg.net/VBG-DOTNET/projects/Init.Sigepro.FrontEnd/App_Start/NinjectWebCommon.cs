
[assembly: WebActivatorEx.PreApplicationStartMethod(typeof(Init.Sigepro.FrontEnd.App_Start.NinjectWebCommon), "Start")]
[assembly: WebActivatorEx.ApplicationShutdownMethodAttribute(typeof(Init.Sigepro.FrontEnd.App_Start.NinjectWebCommon), "Stop")]

namespace Init.Sigepro.FrontEnd.App_Start
{
    using VBG.Shared.Infrastructure.DependencyInjection;
    using Init.Sigepro.FrontEnd.AppLogic.GenerazioneDocumentiDomanda;
    using Init.Sigepro.FrontEnd.AppLogic.GestioneIntegrazioneLDP;
    using Init.Sigepro.FrontEnd.AppLogic.GestioneVisuraIstanza;
    using Init.Sigepro.FrontEnd.AppLogic.IoC;
    using Init.Sigepro.FrontEnd.AppLogic.Pagamenti.Legacy.IoC;
    using Init.Sigepro.FrontEnd.AppLogic.Pagamenti.NodoPagamenti;
    using Init.Sigepro.FrontEnd.GestioneMovimenti.NinjectModule;
    using Init.Sigepro.FrontEnd.Infrastructure.IOC;
    using Init.Sigepro.FrontEnd.IoC;
    using Init.Sigepro.FrontEnd.Log4Net;
    using Init.Sigepro.FrontEnd.WebForms.GestioneDatiDinamici.DependencyInjection;
    using Init.Sigepro.UrlSecurity.IoC;
    using Microsoft.Extensions.Logging;
    using Microsoft.Web.Infrastructure.DynamicModuleHelper;
    using Ninject;
    using Ninject.Web.Common;
    using Ninject.Web.Common.WebHost;
    using System;
    using System.Web;


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
            var settings = new NinjectSettings { InjectNonPublic = true };

            var kernel = new StandardKernel(settings);

            kernel.Bind<Func<IKernel>>().ToMethod(ctx => () => new Bootstrapper().Kernel);
            kernel.Bind<IHttpModule>().To<HttpApplicationInitializationHttpModule>();

            RegisterServices(kernel);
            return kernel;
        }

        /// <summary>
        /// Load your modules or register your services here!
        /// </summary>
        /// <param name="kernel">The kernel.</param>
        private static void RegisterServices(IKernel kernel)
        {
            kernel.Bind<ILoggerFactory>().ToMethod((ctxt) =>
            {
                var loggerFactory = new LoggerFactory();
                loggerFactory.AddProvider(new Log4NetProvider());

                return (ILoggerFactory)loggerFactory;
            }).InSingletonScope();

            kernel.ToDIProvider()
                .ConfiguraAreaRiservata()
                .ConfiguraMovimenti()
                .ConfiguraIntegrazioneLDP()
                .ConfiguraVisura()
                .ConfiguraGenerazioneDocumenti()
                .ConfiguraDatiDinamiciFramework()
                .ConfiguraIntegrazionePagamentiNodoPagamenti()
                .ConfiguraIntegrazionePagamentiLegacy();

            kernel.Load(new UrlSecurityNinjectModule());
            kernel.Load(new FrontendNinjectModule());

            //kernel.Bind<ILoggerFactory>().ToConstant(new Log4NetLoggerFactory());

            FoKernelContainer.Kernel = kernel;
        }
    }
}
