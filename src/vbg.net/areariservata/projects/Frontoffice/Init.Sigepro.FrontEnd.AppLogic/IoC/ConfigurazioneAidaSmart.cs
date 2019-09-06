using Init.Sigepro.FrontEnd.AppLogic.Autenticazione.AidaSmart;
using Init.Sigepro.FrontEnd.AppLogic.GestioneIntegrazioneAidaSmart;
using Ninject;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.Sigepro.FrontEnd.AppLogic.IoC
{
    internal static class ConfigurazioneAidaSmart
    {
        public static IKernel RegistraClassiAidaSmart(this IKernel kernel)
        {
            kernel.Bind<IASCrossLoginClient>().To<ASCrossLoginClient>();
            kernel.Bind<IAidaSmartService>().To<AidaSmartService>();

            return kernel;
        }
    }
}
