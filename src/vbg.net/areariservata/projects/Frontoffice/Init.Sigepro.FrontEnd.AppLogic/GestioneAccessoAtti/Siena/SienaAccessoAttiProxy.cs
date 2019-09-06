using Init.Sigepro.FrontEnd.AppLogic.Autenticazione.Vbg.TokenApplicazione;
using Init.Sigepro.FrontEnd.AppLogic.Common;
using Init.Sigepro.FrontEnd.AppLogic.Configurazione.V2;
using Init.Sigepro.FrontEnd.AppLogic.WsAccessoAtti;
using log4net;
using System;
using System.Collections.Generic;
using System.ServiceModel;

namespace Init.Sigepro.FrontEnd.AppLogic.GestioneAccessoAtti.Siena
{
    public class SienaAccessoAttiProxy : ISienaAccessoAttiProxy
    {
        private readonly string _webServiceUrl;
        private readonly ITokenApplicazioneService _tokenApplicazioneService;
        private readonly ISoftwareResolver _softwareResolver;
        private ILog _log = LogManager.GetLogger(typeof(SienaAccessoAttiProxy));


        internal SienaAccessoAttiProxy(IConfigurazione<ParametriSigeproSecurity> configurazione, ITokenApplicazioneService tokenApplicazioneService, ISoftwareResolver softwareResolver)
        {
            _webServiceUrl = configurazione.Parametri.UrlServizioAccessoAttiSiena;
            _tokenApplicazioneService = tokenApplicazioneService;
            _softwareResolver = softwareResolver;
        }

        public IEnumerable<PraticaAccessoAtti> GetListaPratiche(int codiceAnagrafe)
        {
            var token = _tokenApplicazioneService.GetToken();

            return CallService(ws =>
            {
                _log.Info($"Richiesta lista atti accessibili dall'anagrafica {codiceAnagrafe.ToString()}");

                return ws.GetListaAtti(token, codiceAnagrafe, this._softwareResolver.Software);
            });
        }

        public void LogAccessoPratica(int codiceAnagrafe, int idAccessoAtti, int codiceIStanza)
        {
            var token = _tokenApplicazioneService.GetToken();

            CallService(ws =>
            {
                _log.Info($"Log accesso atti per la pratica con idAccessoAtti: {idAccessoAtti}, codiceAnagrafe: {codiceAnagrafe}, codiceIStanza: {codiceIStanza}");

                ws.LogAccessoAtti(token, idAccessoAtti, codiceAnagrafe, codiceIStanza);
            });
        }

        private void CallService(Action<ws_accesso_attiSoapClient> callback)
        {
            var tmp = CallService(ws =>
            {
                callback(ws);

                return true;
            });
        }

        private T CallService<T>(Func<ws_accesso_attiSoapClient, T> callback)
        {
            var endpoint = new EndpointAddress(_webServiceUrl);
            var binding = new BasicHttpBinding("defaultServiceBinding");

            using (var ws = new ws_accesso_attiSoapClient(binding, endpoint))
            {
                try
                {
                    return callback(ws);
                }
                catch (Exception ex)
                {
                    _log.Error($"ERRORE NELLA LETTURA della lista atti accessibili: {ex.ToString()}");

                    ws.Abort();
                    throw;
                }
            }
        }
    }
}
