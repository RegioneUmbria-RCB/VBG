using Init.SIGePro.DatiDinamici.DependencyInjection;
using Init.SIGePro.Manager.IOC;
using System;

namespace Init.SIGePro.Manager.Logic.DatiDinamici.DependencyInjection
{
    public class FrameworkDependencyResolver : IFrameworkDependencyResolver
    {
        public object GetServiceForType(Type type)
        {
            return StaticKernelContainer.GetService(type);
        }
    }
}
