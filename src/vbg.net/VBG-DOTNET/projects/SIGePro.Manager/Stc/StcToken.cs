using VBG.Shared.Infrastructure.ServiceModel;
using Init.SIGePro.Manager.StcServiceReference;
using log4net;
using SIGePro.Manager.Verticalizzazioni;
using System;
using System.ServiceModel;

namespace Init.SIGePro.Manager.Stc
{
    public class StcToken
    {
        private readonly ILog m_logger = LogManager.GetLogger(typeof(StcToken));
        private readonly VerticalizzazioneStc _configurazione;
        private readonly IBindingFactory _bindingFactory;
        private string _token = null;

        internal StcToken(VerticalizzazioneStc configurazione, IBindingFactory bindingFactory)
        {
            this._configurazione = configurazione;
            this._bindingFactory = bindingFactory;
        }

        public string GetToken()
        {
            if (this._token != null)
            {
                return this._token;
            }

            var urlInvio = this._configurazione.StcWsUrl;
            var username = this._configurazione.NlaUsername;
            var password = this._configurazione.NlaPassword;

            var endPoint = new EndpointAddress(urlInvio);

            var binding = this._bindingFactory.CreateAndConfigure("StcBinding");

            using (var ws = new StcClient(binding, endPoint))
            {
                var loginRequest = new LoginRequest
                {
                    username = username,
                    password = password
                };

                this.m_logger.DebugFormat("Lettura di un nuovo token STC con le credenziali:\nusername={0}\npassword=*****",
                        username);


                var response = ws.Login(loginRequest);

                if (response == null || !response.result)
                {
                    this.m_logger.ErrorFormat("Impossibile leggere un token STC utilizzando le credenziali username={0} password=******",
                        username);

                    throw new Exception("Impossibile leggere un token STC");
                }

                this._token = response.token;

                return this._token;
            }
        }
    }
}
