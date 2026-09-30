using System;
using VBG.DatiDinamici.DependencyInjection;

namespace Init.SIGePro.DatiDinamici.DependencyInjection
{
    public class FrameworkDependencyInjectionService : ScriptDependencyInjectionServiceBase
    {
        private readonly IFrameworkDependencyResolver _frameworkDependencyResolver;

        public FrameworkDependencyInjectionService(IFrameworkDependencyResolver frameworkDependencyResolver)
        {
            this._frameworkDependencyResolver = frameworkDependencyResolver;
        }
        protected override object GetServiceForType(Type type)
        {
            return this._frameworkDependencyResolver.GetServiceForType(type);
        }
    }
}
