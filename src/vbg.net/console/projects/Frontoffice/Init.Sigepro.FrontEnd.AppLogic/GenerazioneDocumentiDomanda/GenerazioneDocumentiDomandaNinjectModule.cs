using Init.Sigepro.FrontEnd.AppLogic.GenerazioneDocumentiDomanda.GenerazioneCertificatoDiInvio;
using Init.Sigepro.FrontEnd.AppLogic.GenerazioneDocumentiDomanda.GenerazioneCertificatoDiInvio.GestioneQrCode;
using Init.Sigepro.FrontEnd.AppLogic.ServiceCreators;
using Ninject.Modules;
using Ninject.Web.Common;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.Sigepro.FrontEnd.AppLogic.GenerazioneDocumentiDomanda
{
    public class GenerazioneDocumentiDomandaNinjectModule: NinjectModule
    {
        public override void Load()
        {
            // Qr code
            Bind<QrCodeServiceCreator>().ToSelf().InRequestScope();
            Bind<IGenerazioneQrCodeServiceWrapper>().To<GenerazioneQrCodeServiceWrapper>().InRequestScope();
            Bind<ISostituzioneSegnapostoQrCode>().To<SostituzioneSegnapostoQrCode>().InRequestScope();



            Bind<CertificatoDiInvioService>().ToSelf().InRequestScope();
        }
    }
}
