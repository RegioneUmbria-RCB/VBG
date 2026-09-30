using Init.SIGePro.Manager.StcServiceReference;
using Init.SIGePro.Verticalizzazioni;
using log4net;
using System;
using System.Collections.Generic;
using System.Linq;
using System.ServiceModel;
using System.Text;

namespace Init.SIGePro.Manager.Stc
{
    public class StcToken
    {
        ILog m_logger = LogManager.GetLogger(typeof(StcToken));
        VerticalizzazioneStc _configurazione;
        string _token = null;

        internal StcToken(VerticalizzazioneStc configurazione)
        {
            this._configurazione = configurazione;
        }

        public string GetToken()
        {
            if (this._token != null)
            {
                return this._token;
            }

            var urlInvio = _configurazione.StcWsUrl;
            var username = _configurazione.NlaUsername;
            var password = _configurazione.NlaPassword;

            var endPoint = new EndpointAddress(urlInvio);

            var binding = new BasicHttpBinding("StcBinding");

            using (var ws = new StcClient(binding, endPoint))
            {
                var loginRequest = new LoginRequest
                {
                    username = username,
                    password = password
                };

                m_logger.DebugFormat("Lettura di un nuovo token STC con le credenziali:\nusername={0}\npassword={1}",
                        username,
                        password);


                var response = ws.Login(loginRequest);

                if (response == null || !response.result)
                {
                    m_logger.ErrorFormat("Impossibile leggere un token STC utilizzando le credenziali username={0} password={1}",
                        username,
                        password);

                    throw new Exception("Impossibile leggere un token STC");
                }

                this._token = response.token;

                return this._token;
            }
        }
    }
}
