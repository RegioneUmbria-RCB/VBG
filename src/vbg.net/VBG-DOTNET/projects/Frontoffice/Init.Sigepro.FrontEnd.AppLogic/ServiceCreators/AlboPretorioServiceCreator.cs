using Init.Sigepro.FrontEnd.AppLogic.AlboPretorioService;
using Init.Sigepro.FrontEnd.AppLogic.Autenticazione.Vbg.TokenApplicazione;
using Init.Sigepro.FrontEnd.AppLogic.Configurazione.V2;
using VBG.Shared.Infrastructure.ServiceModel;
using System.ServiceModel;

namespace Init.Sigepro.FrontEnd.AppLogic.ServiceCreators
{
    internal class AlboPretorioServiceCreator : ServiceCreatorBase<AlboPretorioClient>
    {
        public AlboPretorioServiceCreator(IConfigurazione<ParametriSigeproSecurity> cfg, ITokenApplicazioneService tokenApplicazioneService, IBindingFactory bindingFactory) : base(cfg, tokenApplicazioneService, bindingFactory)
        {
        }

        protected override string GetBindingName() => "alboPretorioServiceBinding";
        protected override AlboPretorioClient CreateClient(EndpointAddress endpoint, BasicHttpBinding binding)
        {
            return new AlboPretorioClient(binding, endpoint);
        }

        protected override string GetEndpointUrl(ParametriSigeproSecurity config)
        {
            return config.UrlAlboPretorioService;
        }
    }
}