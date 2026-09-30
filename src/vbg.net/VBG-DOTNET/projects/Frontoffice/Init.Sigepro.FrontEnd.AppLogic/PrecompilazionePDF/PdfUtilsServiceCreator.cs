using Init.Sigepro.FrontEnd.AppLogic.Autenticazione.Vbg.TokenApplicazione;
using Init.Sigepro.FrontEnd.AppLogic.Configurazione.V2;
using Init.Sigepro.FrontEnd.AppLogic.ServiceCreators;
using Init.Sigepro.FrontEnd.AppLogic.ServizioPrecompilazionePDF;
using VBG.Shared.Infrastructure.ServiceModel;
using System.ServiceModel;

namespace Init.Sigepro.FrontEnd.AppLogic.PrecompilazionePDF
{
    public class PdfUtilsServiceCreator : ServiceCreatorBase<PdfUtilsClient>
    {
        public PdfUtilsServiceCreator(IConfigurazione<ParametriSigeproSecurity> cfg, ITokenApplicazioneService tokenApplicazioneService, IBindingFactory bindingFactory) : base(cfg, tokenApplicazioneService, bindingFactory)
        {
        }

        protected override string GetBindingName() => "pdfUtilsBinding";
        protected override PdfUtilsClient CreateClient(EndpointAddress endpoint, BasicHttpBinding binding)
        {
            return new PdfUtilsClient(binding, endpoint);
        }

        protected override string GetEndpointUrl(ParametriSigeproSecurity config)
        {
            return config.UrlPdfUtilsService;
        }
    }
}