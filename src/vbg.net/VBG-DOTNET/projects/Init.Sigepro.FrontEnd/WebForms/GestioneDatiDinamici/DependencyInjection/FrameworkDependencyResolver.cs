using Init.SIGePro.DatiDinamici.DependencyInjection;
using Ninject;
using System;

namespace Init.Sigepro.FrontEnd.WebForms.GestioneDatiDinamici.DependencyInjection
{
    public class FrameworkDependencyResolver : IFrameworkDependencyResolver
    {
        private readonly IKernel _kernel;

        public FrameworkDependencyResolver(IKernel kernel)
        {
            this._kernel = kernel;
        }
        public object GetServiceForType(Type type)
        {
            return this._kernel.GetService(type);
        }
    }
}