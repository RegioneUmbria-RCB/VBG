using Init.Sigepro.FrontEnd.AppLogic.IntegrazioneSit;
using Ninject;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.Sigepro.FrontEnd.AppLogic.IoC
{
    internal static class ConfigurazioneSIT
    {
        public static IKernel RegistraSIT(this IKernel kernel)
        {
            kernel.Bind<ISitService>().To<SigeproSitService>();
            kernel.Bind<ISitServiceCreator>().To<SitServiceCreator>();


            return kernel;
        }
    }
}
