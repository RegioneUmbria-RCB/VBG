[assembly: WebActivatorEx.PreApplicationStartMethod(typeof(Init.Sigepro.FrontEnd.App_Start.RegistrazioneModuloHttpMovimenti), "Start")]


namespace Init.Sigepro.FrontEnd.App_Start
{
    using Init.Sigepro.FrontEnd.HttpModules;
    using Microsoft.Web.Infrastructure.DynamicModuleHelper;

    // 
    public static class RegistrazioneModuloHttpMovimenti
    {
        /// <summary>
        /// Starts the application
        /// </summary>
        public static void Start()
        {
            DynamicModuleUtility.RegisterModule(typeof(MovimentiUnitOfWorkModule));
        }
    }
}