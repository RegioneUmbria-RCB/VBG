using Init.Sigepro.FrontEnd.AppLogic.Autenticazione.Vbg.TokenApplicazione;
using Init.Sigepro.FrontEnd.AppLogic.BookmarksWebService;
using Init.Sigepro.FrontEnd.AppLogic.Configurazione.V2;
using Init.Sigepro.FrontEnd.AppLogic.ServiceCreators;
using System.ServiceModel;
using VBG.Shared.Infrastructure.ServiceModel;

namespace Init.Sigepro.FrontEnd.AppLogic.GestioneBookmarks
{
    public class BookmarksServiceClientCreator : ServiceCreatorBase<WsBookmarksServiceClient>
    {
        public BookmarksServiceClientCreator(IConfigurazione<ParametriSigeproSecurity> cfg, ITokenApplicazioneService tokenApplicazioneService, IBindingFactory bindingFactory) : base(cfg, tokenApplicazioneService, bindingFactory)
        {
        }

        protected override string GetBindingName() => "areaRiservataServiceBinding";
        protected override WsBookmarksServiceClient CreateClient(EndpointAddress endpoint, BasicHttpBinding binding)
        {
            return new WsBookmarksServiceClient(binding, endpoint);
        }

        protected override string GetEndpointUrl(ParametriSigeproSecurity config)
        {
            return config.UrlWebServiceBookmarks;
        }
    }
}