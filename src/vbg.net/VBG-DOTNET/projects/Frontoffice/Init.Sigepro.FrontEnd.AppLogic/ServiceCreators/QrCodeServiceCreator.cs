using Init.Sigepro.FrontEnd.AppLogic.Autenticazione.Vbg.TokenApplicazione;
using Init.Sigepro.FrontEnd.AppLogic.BackendQrCodeWs;
using Init.Sigepro.FrontEnd.AppLogic.Configurazione.V2;
using VBG.Shared.Infrastructure.ServiceModel;
using System.ServiceModel;

namespace Init.Sigepro.FrontEnd.AppLogic.ServiceCreators
{
    public class QrCodeServiceCreator : ServiceCreatorBase<QrcodeClient>
    {
        public QrCodeServiceCreator(IConfigurazione<ParametriSigeproSecurity> cfg, ITokenApplicazioneService tokenApplicazioneService, IBindingFactory bindingFactory) : base(cfg, tokenApplicazioneService, bindingFactory)
        {
        }

        protected override string GetBindingName() => "defaultServiceBinding";
        protected override QrcodeClient CreateClient(EndpointAddress endpoint, BasicHttpBinding binding)
        {
            return new QrcodeClient(binding, endpoint);
        }

        protected override string GetEndpointUrl(ParametriSigeproSecurity config)
        {
            return config.UrlGenerazioneQrCode;
        }
    }
}