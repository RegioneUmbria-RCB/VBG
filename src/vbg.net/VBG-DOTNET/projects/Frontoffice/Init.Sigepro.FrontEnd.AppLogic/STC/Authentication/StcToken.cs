using Init.Sigepro.FrontEnd.AppLogic.Configurazione.V2;
using Init.Sigepro.FrontEnd.AppLogic.StcService;
using VBG.Shared.Infrastructure.ServiceModel;
using log4net;
using System;
using System.ServiceModel;

namespace Init.Sigepro.FrontEnd.AppLogic.Autenticazione.Stc
{
    public class StcToken
    {
        private readonly ILog m_logger = LogManager.GetLogger(typeof(StcToken));
        private readonly IConfigurazione<ParametriStc> _configurazione;
        private readonly IBindingFactory _bindingFactory;
        public StcToken(IConfigurazione<ParametriStc> configurazione, IBindingFactory bindingFactory)
        {
            if (configurazione == null)
                throw new ArgumentNullException(nameof(configurazione));
            //Condition.Requires(configurazione, "configurazione").IsNotNull();
            this._configurazione = configurazione;
            this._bindingFactory = bindingFactory;
        }

        public string GetToken( /*string aliasComune, string username, string password*/)
        {
            //var endPoint = new EndpointAddress(ConfigurazioneFrontEnd.WebConfig.GetUrlInvioStc(aliasComune));
            var urlInvio = this._configurazione.Parametri.UrlInvio;
            var username = this._configurazione.Parametri.Username;
            var password = this._configurazione.Parametri.Password;
            var endPoint = new EndpointAddress(urlInvio);
            var binding = this._bindingFactory.CreateAndConfigure("stcServiceBinding");
            using (var ws = new StcClient(binding, endPoint))
            {
                var loginRequest = new LoginRequest { username = username, password = password };
                this.m_logger.DebugFormat("Lettura di un nuovo token STC con le credenziali:\nusername={0}\npassword={1}", username, password);
                var response = ws.Login(loginRequest);
                if (response == null || !response.result)
                {
                    this.m_logger.ErrorFormat("Impossibile leggere un token STC utilizzando le credenziali username={0} password={1}", username, password);
                    throw new Exception("Impossibile leggere un token STC");
                }

                return response.token;
            }
        }
    }
}