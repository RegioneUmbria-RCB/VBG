using Init.Sigepro.FrontEnd.AppLogic.GestioneFilesExcel;
using Ninject;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.Sigepro.FrontEnd.AppLogic.IoC
{
    internal static class ConfigurazioneGestioneFilesExcel
    {
        public static IKernel RegistraGestioneFilesExcel(this IKernel k)
        {
            k.Bind<IRegoleRepository>().To<RegoleRepository>();
            k.Bind<IDatiDinamiciExcelService>().To<DatiDinamiciExcelService>();

            return k;
        }
    }
}
