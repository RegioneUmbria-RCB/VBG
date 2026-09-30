using Init.Sigepro.FrontEnd.AppLogic.Autenticazione.Vbg.TokenApplicazione;
using Init.Sigepro.FrontEnd.AppLogic.Configurazione.V2;
using Init.Sigepro.FrontEnd.AppLogic.ServiceCreators;
using VBG.Shared.Infrastructure.ServiceModel;
using MailServiceWs;
using System.ServiceModel;

namespace VBG.AppLogic.SSU.GeneratoreRicevute.InvioMail
{
    public class MailServiceServiceCreator : ServiceCreatorBase<MailServicePortTypeClient>
    {
        public MailServiceServiceCreator(IConfigurazione<ParametriSigeproSecurity> cfg, ITokenApplicazioneService tokenApplicazioneService, IBindingFactory bindingFactory) : base(cfg, tokenApplicazioneService, bindingFactory)
        {
        }

        protected override MailServicePortTypeClient CreateClient(EndpointAddress endpoint, BasicHttpBinding binding)
        {
            return new MailServicePortTypeClient(binding, endpoint);
        }

        protected override string GetEndpointUrl(ParametriSigeproSecurity config) => config.UrlMailService;
    }
}
