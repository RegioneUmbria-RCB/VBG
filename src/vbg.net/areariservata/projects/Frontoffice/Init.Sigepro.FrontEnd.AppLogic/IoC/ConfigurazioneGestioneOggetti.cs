using Init.Sigepro.FrontEnd.AppLogic.GestioneOggetti;
using Init.Sigepro.FrontEnd.AppLogic.ServiceCreators;
using Ninject;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.Sigepro.FrontEnd.AppLogic.IoC
{
    internal static class ConfigurazioneGestioneOggetti
    {
        public static IKernel RegistraGestioneOggetti(this IKernel k)
        {
            k.Bind<IOggettiService>().To<OggettiService>();
            k.Bind<IOggettiRepository>().To<WsOggettiRepository>();
            k.Bind<OggettiServiceCreator>().ToSelf();

            return k;
        }
    }
}
