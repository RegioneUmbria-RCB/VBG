using Init.Sigepro.UrlSecurity.Configuration;
using Init.Sigepro.UrlSecurity.Configuration.WebForms;
using Init.Sigepro.UrlSecurity.Runtime;
using Ninject;
using Ninject.Modules;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Init.Sigepro.UrlSecurity.IoC
{
    public class UrlSecurityNinjectModule : NinjectModule
    {
        public override void Load()
        {
            this.Kernel.Bind<IUrlDecoder>().To<HttpUtilityUrlDecoder>();
            this.Kernel.Bind<IUrlSecurityRuntime>().To<UrlSecurityRuntime>();
            this.Kernel.Bind<IParametersValidatorProvider>().To<ParametersValidatorProvider>();
            this.Kernel.Bind<BlacklistValidator>().ToSelf();
            this.Kernel.Bind<StringheEsadecimaliValidator>().ToSelf();
            this.Kernel.Bind<EntitaXmlValidator>().ToSelf();


            this.Kernel.Bind<IUrlSecurityConfiguration>().ToMethod(x =>
            {
                return new WebFormsConfigurationBuilder().BuildConfiguration();
            }).InSingletonScope();
        }
    }
}
