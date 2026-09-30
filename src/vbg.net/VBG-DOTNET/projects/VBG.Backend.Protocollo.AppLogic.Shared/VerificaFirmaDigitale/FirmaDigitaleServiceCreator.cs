using VBG.Shared.Infrastructure.ServiceModel;
using Init.SIGePro.Manager.Configuration;
using log4net;
using System.ServiceModel;
using VBG.Backend.Protocollo.AppLogic.Shared.ServiceCreators;

namespace VBG.Backend.Protocollo.AppLogic.Shared.VerificaFirmaDigitale
{
    public class FirmaDigitaleServiceCreator : ServiceCreatorBase<ValidationServiceClient>
    {
        public FirmaDigitaleServiceCreator(ILog logger, IBindingFactory bindingFactory) : base(logger, bindingFactory)
        {
        }

        protected override string GetBindingName() => "firmaDigitaleServiceBinding";
        protected override ValidationServiceClient CreateClient(EndpointAddress endPoint, BasicHttpBinding binding)
        {
            return new ValidationServiceClient(binding, endPoint);
        }

        public override string GetEndpointUrl()
        {
            return ParametriConfigurazione.Get.WsHostUrlFirmaDigitale;
        }
    }
}
