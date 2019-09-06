using Init.Sigepro.FrontEnd.AppLogic.GestioneTransiti;
using Ninject;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.Sigepro.FrontEnd.AppLogic.IoC
{
    internal static class ConfigurazioneGestioneTransiti
    {
        public static IKernel RegistraGestioneTransiti(this IKernel k)
        {
            k.Bind<IGestioneTransitiService>().To<GestioneTransitiService>();
            k.Bind<IAutorizzazioniTransitiProxy>().To<AutorizzazioniTransitiProxy>();

            return k;
        }
    }
}
