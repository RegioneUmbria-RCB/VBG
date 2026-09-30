using Ninject;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.Sigepro.FrontEnd.AppLogic.GestioneConversioneFiles.IoC
{
    internal static class ConfigurazioneConversioneFiles
    {
        public static IKernel RegistraConversioneFiles(this IKernel k)
        {
            k.Bind<FileConverterService>().ToSelf().InTransientScope();
            k.Bind<FileConverterServiceCreator>().ToSelf().InTransientScope();

            return k;
        }
    }
}
