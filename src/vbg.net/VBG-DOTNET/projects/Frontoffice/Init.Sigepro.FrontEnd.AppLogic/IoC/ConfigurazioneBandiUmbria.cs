using Init.Sigepro.FrontEnd.AppLogic.GestioneBandiUmbria;
using Init.Sigepro.FrontEnd.AppLogic.GestioneBandiUmbria.Validazione;
using Ninject;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.Sigepro.FrontEnd.AppLogic.IoC
{
    public static class ConfigurazioneBandiUmbria
    {
        public static IKernel RegistraBandiUmbria(this IKernel kernel)
        {
            kernel.Bind<IConfigurazioneValidazioneReader>().To<ConfigurazioneValidazioneReader>();
            kernel.Bind<IBandiUmbriaService>().To<BandiUmbriaService>();
            kernel.Bind<IValidazioneBandoA1Service>().To<ValidazioneBandoA1Service>();
            kernel.Bind<IValidazioneBandoB1Service>().To<ValidazioneBandoB1Service>();
            kernel.Bind<IDatiProgettoReader>().To<DatiProgettoReader>();
            kernel.Bind<IBandiIncomingService>().To<BandiIncomingService>();
            kernel.Bind<IValidazioneBandoIncomingService>().To<ValidazioneBandoIncomingService>();

            return kernel;
        }
    }
}
