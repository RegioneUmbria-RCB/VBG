using System;
using Ninject;
using Ninject.Web.Common;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.Sigepro.FrontEnd.AppLogic.GestioneEntiTerzi;

namespace Init.Sigepro.FrontEnd.AppLogic.IoC
{
    public static class ConfigurazioneScrivaniaEntiTerzi
    {
        public static IKernel RegistraClassiScrivaniaEntiTerzi(this IKernel kernel)
        {
            kernel.Bind<IScrivaniaEntiTerziService>().To<ScrivaniaEntiTerziService>().InRequestScope();
            kernel.Bind<IScrivaniaEntiTerziWsProxy>().To<ScrivaniaEntiTerziWsProxy>().InRequestScope();

            return kernel;
        }
    }
}
