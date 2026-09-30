using Init.Sigepro.FrontEnd.AppLogic.Autenticazione.Vbg.TokenApplicazione;
using Init.Sigepro.FrontEnd.AppLogic.Configurazione.V2;
using Init.Sigepro.FrontEnd.AppLogic.ServiceCreators;
using Init.Sigepro.FrontEnd.AppLogic.WsQuestionarioFo;
using VBG.Shared.Infrastructure.ServiceModel;
using System.ServiceModel;

namespace Init.Sigepro.FrontEnd.AppLogic.GestioneQuestionario
{
    public class QuestionarioWebServiceDao : ServiceCreatorBase<WsQuestionarioFoServiceClient>, IQuestionarioSoddisfazioneDao
    {
        public QuestionarioWebServiceDao(IConfigurazione<ParametriSigeproSecurity> cfg, ITokenApplicazioneService tokenApplicazioneService, IBindingFactory bindingFactory) : base(cfg, tokenApplicazioneService, bindingFactory)
        {
        }

        protected override WsQuestionarioFoServiceClient CreateClient(EndpointAddress address, BasicHttpBinding binding)
        {
            return new WsQuestionarioFoServiceClient(binding, address);
        }

        protected override string GetEndpointUrl(ParametriSigeproSecurity config)
        {
            return config.UrlQuestionarioSoddisfazione;
        }

        public bool QuestionarioCompilato(string uuidIstanza)
        {
            return this.Call((ws) => ws.Service.QuestionarioCompilato(ws.Token, uuidIstanza));
        }

        public void SalvaQuestionario(string uuidIstanza, int valutazione, string note)
        {
            this.CallVoid((ws) => ws.Service.SalvaQuestionario(ws.Token, uuidIstanza, valutazione, note));
        }
    }
}