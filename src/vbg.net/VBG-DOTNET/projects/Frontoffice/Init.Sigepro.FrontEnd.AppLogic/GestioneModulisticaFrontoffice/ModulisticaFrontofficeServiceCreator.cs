//using Init.Sigepro.FrontEnd.AppLogic.Autenticazione.Vbg.TokenApplicazione;
//using Init.Sigepro.FrontEnd.AppLogic.Configurazione.V2;
//using Init.Sigepro.FrontEnd.AppLogic.ServiceCreators;
//using Init.Sigepro.FrontEnd.AppLogic.WsModulisticaFrontoffice;
//using VBG.Shared.Infrastructure.ServiceModel;
//using System.ServiceModel;

//namespace Init.Sigepro.FrontEnd.AppLogic.GestioneModulisticaFrontoffice
//{
//    public class ModulisticaFrontofficeServiceCreator : ServiceCreatorBase<WsModulisticaSoapClient>
//    {
//        public ModulisticaFrontofficeServiceCreator(IConfigurazione<ParametriSigeproSecurity> cfg, ITokenApplicazioneService tokenApplicazioneService, IBindingFactory bindingFactory) : base(cfg, tokenApplicazioneService, bindingFactory)
//        {
//        }

//        protected override string GetBindingName() => "defaultServiceBinding";
//        protected override WsModulisticaSoapClient CreateClient(EndpointAddress endpoint, BasicHttpBinding binding)
//        {
//            return new WsModulisticaSoapClient(binding, endpoint);
//        }

//        protected override string GetEndpointUrl(ParametriSigeproSecurity config)
//        {
//            return config.UrlWsModulisticaFrontoffice;
//        }
//    }
//}