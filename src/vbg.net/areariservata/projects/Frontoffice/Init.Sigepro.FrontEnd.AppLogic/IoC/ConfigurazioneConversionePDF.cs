using Init.Sigepro.FrontEnd.AppLogic.Autenticazione.Vbg;
using Init.Sigepro.FrontEnd.AppLogic.ConversionePDF;
using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneAnagrafiche.LogicaRisoluzioneSoggetti;
using Init.Sigepro.FrontEnd.AppLogic.GestioneRisorseTestuali;
using Init.Sigepro.FrontEnd.AppLogic.PrecompilazionePDF;
using Ninject;
using Ninject.Web.Common;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.Sigepro.FrontEnd.AppLogic.IoC
{

    internal static class ConfigurazioneConversionePDF
    {
        public static IKernel RegistraClassiConversionePDF(this IKernel kernel)
        {
            kernel.Bind<IHtmlToPdfFileConverter>().To<PhantomjsFileConverter>();
            kernel.Bind<IPdfUtilsService>().To<PdfUtilsService>();
            kernel.Bind<IPdfUtilsWsWrapper>().To<PdfUtilsWsWrapper>();

            return kernel;
        }
    }

}
