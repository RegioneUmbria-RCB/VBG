using Init.Sigepro.FrontEnd.Infrastructure.ServiceModel;
using Init.Sigepro.FrontEnd.Infrastructure.ServiceModel.Framework;
using Ninject.Modules;

namespace Init.Sigepro.FrontEnd.Infrastructure.IOC
{
    public class InfrastructureNinjectModule : NinjectModule
    {
        public override void Load()
        {
            this.Bind<IBindingFactory>().To<FrameworkBindingFactory>();
        }
    }
}
