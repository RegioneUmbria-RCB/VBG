//using Init.Sigepro.FrontEnd.AppLogic.Autenticazione.Vbg.TokenApplicazione;
//using Init.Sigepro.FrontEnd.AppLogic.Configurazione.V2;
//using Init.Sigepro.FrontEnd.AppLogic.ServiceCreators;
//using Init.Sigepro.FrontEnd.AppLogic.WsVerificaFirmaDigitale;
//using VBG.Shared.Infrastructure.ServiceModel;
//using System.ServiceModel;

//namespace Init.Sigepro.FrontEnd.AppLogic.VerificaFirmaDigitale
//{
//    public class FirmaDigitaleServiceCreator : ServiceCreatorBase<ValidationServiceClient>
//    {
//        public FirmaDigitaleServiceCreator(IConfigurazione<ParametriSigeproSecurity> cfg, ITokenApplicazioneService tokenApplicazioneService, IBindingFactory bindingFactory) : base(cfg, tokenApplicazioneService, bindingFactory)
//        {
//        }

//        protected override string GetBindingName() => "firmaDigitaleServiceBinding";
//        protected override ValidationServiceClient CreateClient(EndpointAddress endPoint, BasicHttpBinding binding)
//        {
//            return new ValidationServiceClient(binding, endPoint);
//        }

//        protected override string GetEndpointUrl(ParametriSigeproSecurity config)
//        {
//            return config.UrlVerificaFirmaService;
//        }
//    }
//}