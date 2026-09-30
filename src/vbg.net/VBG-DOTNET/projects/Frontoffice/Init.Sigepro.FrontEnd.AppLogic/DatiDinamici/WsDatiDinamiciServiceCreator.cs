using Init.Sigepro.FrontEnd.AppLogic.Autenticazione.Vbg.TokenApplicazione;
using Init.Sigepro.FrontEnd.AppLogic.Configurazione.V2;
using Init.Sigepro.FrontEnd.AppLogic.ServiceCreators;
using Init.Sigepro.FrontEnd.AppLogic.WsVbgDatiDinamici;
using System.ServiceModel;
using VBG.Shared.Infrastructure.ServiceModel;

namespace Init.Sigepro.FrontEnd.AppLogic.DatiDinamici
{
    public class WsDatiDinamiciServiceCreator : ServiceCreatorBase<WsDatiDinamiciClient>
    {
        public WsDatiDinamiciServiceCreator(IConfigurazione<ParametriSigeproSecurity> cfg, ITokenApplicazioneService tokenApplicazioneService, IBindingFactory bindingFactory) : base(cfg, tokenApplicazioneService, bindingFactory)
        {
        }

        protected override WsDatiDinamiciClient CreateClient(EndpointAddress address, BasicHttpBinding binding)
        {
            return new WsDatiDinamiciClient(binding, address);
        }

        protected override string GetEndpointUrl(ParametriSigeproSecurity config)
        {
            return config.UrlWsDatiDinamici;
        }
    }
}