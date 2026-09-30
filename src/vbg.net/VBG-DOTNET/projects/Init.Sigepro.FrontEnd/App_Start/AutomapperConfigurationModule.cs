//using AutoMapper;
//using Init.Sigepro.FrontEnd.AppLogic.AutomapperBootstrapper;
//using Init.Sigepro.FrontEnd.GestioneMovimenti.Bootstrap;
//using Ninject.Modules;

//namespace Init.Sigepro.FrontEnd.App_Start
//{
//    public class AutomapperConfigurationModule : NinjectModule
//    {
//        public override void Load()
//        {
//            this.Bind<IMapper>().ToMethod(_ =>
//            {
//                var config = new MapperConfiguration(cfg =>
//                {
//                    AreaRiservataAutomapperConfiguration.Bootstrap(cfg);
//                    MovimentiAutomapperConfiguration.Bootstrap(cfg);
//                });

//                config.AssertConfigurationIsValid();

//                return config.CreateMapper();

//            }).InSingletonScope();
//        }
//    }
//}