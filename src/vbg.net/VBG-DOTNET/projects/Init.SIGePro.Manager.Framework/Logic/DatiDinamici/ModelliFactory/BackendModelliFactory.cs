using Init.SIGePro.DatiDinamici.DependencyInjection;
using Init.SIGePro.DatiDinamici.Framework.ModelliFactory;
using Init.SIGePro.Manager.Logic.DatiDinamici.DependencyInjection;

namespace Init.SIGePro.Manager.Logic.DatiDinamici.ModelliFactory
{
    public class BackendModelliFactory : ModelliDinamiciFrameworkFactory
    {
        public BackendModelliFactory() : base(new FrameworkDependencyInjectionService(new FrameworkDependencyResolver()))
        {
        }
    }
}
