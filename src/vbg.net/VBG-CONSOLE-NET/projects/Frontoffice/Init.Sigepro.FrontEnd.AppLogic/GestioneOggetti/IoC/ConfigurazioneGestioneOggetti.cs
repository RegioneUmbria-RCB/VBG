using Init.Sigepro.FrontEnd.AppLogic.GestioneOggetti.Metadati;
using Init.Sigepro.FrontEnd.AppLogic.GestioneOggetti.PostedFileSpecifications;
using Init.Sigepro.FrontEnd.AppLogic.GestioneOggetti.UrlDownloadOggetti;
using Ninject;
using Ninject.Web.Common;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.Sigepro.FrontEnd.AppLogic.GestioneOggetti.IoC
{
    internal static class ConfigurazioneGestioneOggetti
    {
        public static IKernel RegistraGestioneOggetti(this IKernel k)
        {
            k.Bind<IOggettiService>().To<OggettiService>().InRequestScope();
            k.Bind<IOggettiRepository>().To<WsOggettiRepository>().InRequestScope();
            k.Bind<OggettiServiceCreator>().ToSelf().InRequestScope();
            k.Bind<IMetadatiOggettoProvider>().To<MetadatiOggettoUtenteProvider>().InRequestScope();
            k.Bind<IUrlDownloadOggettiService>().To<UrlDownloadOggettiService>().InRequestScope();
            k.Bind<IDownloadOggettiSecretKeyService>().To<DownloadOggettiSecretKeyService>().InRequestScope();

            k.Bind<IPostedFileSpecificationFactory>().To<PostedFileSpecificationFactory>().InRequestScope();

            return k;
        }
    }
}
