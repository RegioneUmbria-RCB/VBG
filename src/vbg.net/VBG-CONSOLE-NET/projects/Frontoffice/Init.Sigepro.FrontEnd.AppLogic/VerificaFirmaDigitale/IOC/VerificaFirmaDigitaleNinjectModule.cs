using Init.Sigepro.FrontEnd.AppLogic.IoC;
using Init.Sigepro.FrontEnd.AppLogic.VerificaFirmaDigitale.VerificaFirmaRest;
using Ninject.Web.Common;

namespace Init.Sigepro.FrontEnd.AppLogic.VerificaFirmaDigitale.IOC
{
    public static class VerificaFirmaDigitaleNinjectModule
    {
        public static ConfigurazioneAreaRiservataModule ConfiguraVerificaFirmaDigitale(this ConfigurazioneAreaRiservataModule k)
        {
            k.Bind<IFirmaDigitaleMetadataService>().To<VerificaFirmaDigitaleRestService>().InRequestScope();
            k.Bind<IVerificaFirmaDigitaleService>().To<VerificaFirmaDigitaleRestService>().InRequestScope();
            k.Bind<VerificaFirmaDigitaleRestClient>().ToSelf().InRequestScope();

            return k;
        }

    }
}
