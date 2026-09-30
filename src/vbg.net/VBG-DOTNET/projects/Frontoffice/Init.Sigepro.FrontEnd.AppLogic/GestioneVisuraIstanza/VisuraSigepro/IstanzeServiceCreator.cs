using Init.Sigepro.FrontEnd.AppLogic.Autenticazione.Vbg.TokenApplicazione;
using Init.Sigepro.FrontEnd.AppLogic.Configurazione.V2;
using Init.Sigepro.FrontEnd.AppLogic.ServiceCreators;
using Init.Sigepro.FrontEnd.AppLogic.WebServiceReferences.IstanzeService;
using VBG.Shared.Infrastructure.ServiceModel;
using System.ServiceModel;

namespace Init.Sigepro.FrontEnd.AppLogic.GestioneVisuraIstanza.VisuraSigepro
{
    public class IstanzeServiceCreator : ServiceCreatorBase<WsIstanzeServiceClient>
    {
        public IstanzeServiceCreator(IConfigurazione<ParametriSigeproSecurity> cfg, ITokenApplicazioneService tokenApplicazioneService, IBindingFactory bindingFactory) : base(cfg, tokenApplicazioneService, bindingFactory)
        {
        }

        protected override string GetBindingName() => "istanzeVisuraServiceBinding";
        protected override WsIstanzeServiceClient CreateClient(EndpointAddress endpoint, BasicHttpBinding binding)
        {
            binding.MaxBufferSize = int.MaxValue;
            binding.MaxReceivedMessageSize = int.MaxValue;
            binding.MaxBufferPoolSize = int.MaxValue;
            return new WsIstanzeServiceClient(binding, endpoint);
        }

        protected override string GetEndpointUrl(ParametriSigeproSecurity config)
        {
            return config.UrlIstanzeService;
        }
    }
}