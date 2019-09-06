using Init.Sigepro.FrontEnd.AppLogic.GestioneServiziFVG;
using Ninject;
using Ninject.Web.Common;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.Sigepro.FrontEnd.AppLogic.IoC
{
    internal static class ConfigurazioneServiziFVG
{
        public static IKernel RegistraServiziFVG(this IKernel kernel)
        {
            kernel.Bind<ServiziFVGService>().ToSelf().InRequestScope();

            return kernel;
        }
    }
}
