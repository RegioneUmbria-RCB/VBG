using Init.Sigepro.FrontEnd.AppLogic.Autenticazione.Vbg.TokenApplicazione;
using Init.Sigepro.FrontEnd.AppLogic.Common;
using Init.Sigepro.FrontEnd.AppLogic.Configurazione.V2;
using Init.Sigepro.FrontEnd.AppLogic.ServiceCreators;
using Init.Sigepro.FrontEnd.AppLogic.WsOneri;
using Init.Sigepro.FrontEnd.Infrastructure.ServiceModel;
using System.ServiceModel;

namespace Init.Sigepro.FrontEnd.AppLogic.GestionePagamenti.NODOPAGAMENTI.Conti
{
    public class OneriServiceCreator : ServiceCreatorBase<WsOneriServiceClient>
    {
        public OneriServiceCreator(IConfigurazione<ParametriSigeproSecurity> cfg, ITokenApplicazioneService tokenApplicazioneService, IAliasResolver aliasResolver, IBindingFactory bindingFactory) : base(cfg, tokenApplicazioneService, aliasResolver, bindingFactory)
        {
        }

        protected override string GetBindingName() => "oneriServiceBinding";
        protected override WsOneriServiceClient CreateClient(EndpointAddress endpoint, BasicHttpBinding binding)
        {
            return new WsOneriServiceClient(binding, endpoint);
        }

        protected override string GetEndpointUrl(ParametriSigeproSecurity config)
        {
            return config.UrlOneriService;
        }
    }
}
