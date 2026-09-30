//using Init.Sigepro.FrontEnd.AppLogic.Autenticazione.Vbg.TokenApplicazione;
//using Init.Sigepro.FrontEnd.AppLogic.Configurazione.V2;
//using Init.Sigepro.FrontEnd.AppLogic.ServiceCreators;
//using Init.Sigepro.FrontEnd.AppLogic.WsInterventiAteco;
//using VBG.Shared.Infrastructure.ServiceModel;
//using System.ServiceModel;

//namespace Init.Sigepro.FrontEnd.AppLogic.Repositories.WebServices
//{
//    internal class AtecoServiceCreator : ServiceCreatorBase<WsInterventiAteco.WsInterventiAtecoClient>
//    {
//        public AtecoServiceCreator(IConfigurazione<ParametriSigeproSecurity> cfg, ITokenApplicazioneService tokenApplicazioneService, IBindingFactory bindingFactory) : base(cfg, tokenApplicazioneService, bindingFactory)
//        {
//        }

//        protected override WsInterventiAtecoClient CreateClient(EndpointAddress address, BasicHttpBinding binding)
//        {
//            return new WsInterventiAtecoClient(binding, address);
//        }

//        protected override string GetEndpointUrl(ParametriSigeproSecurity config)
//        {
//            return config.AspNetBaseUrl + "/WebServices/WsAreaRiservata/WcfServices/Interventi/WsInterventiAteco.svc";
//        }
//    }
//}