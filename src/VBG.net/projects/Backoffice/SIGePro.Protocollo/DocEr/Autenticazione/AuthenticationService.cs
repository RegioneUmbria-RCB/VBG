using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Protocollo.ProtocolloDocErLoginService;
using Init.SIGePro.Protocollo.Logs;
using Init.SIGePro.Protocollo.Serialize;
using System.ServiceModel;
using Init.SIGePro.Protocollo.Constants;
using Init.SIGePro.Protocollo.DocEr.Verticalizzazioni;
using Init.SIGePro.Protocollo.ProtocolloDocErGestioneDocumentaleService;
using Init.SIGePro.Protocollo.DocEr.GestioneDocumentale;
using Init.SIGePro.Protocollo.ProtocolloServices;


namespace Init.SIGePro.Protocollo.DocEr.Autenticazione
{
    public class AuthenticationService : IAuthenticationService
    {
        private ProtocolloLogs _logs;
        private ProtocolloSerializer _serializer;
        string _password;
        VerticalizzazioniConfiguration _vert;
        KeyValuePair[] _ruoli;
        ResolveDatiProtocollazioneService _datiProtoSrv;

        public string Token { get; private set; }
        public string Username { get; private set; }

        public AuthenticationService(string username, string password, VerticalizzazioniConfiguration vert, ProtocolloLogs logs, ProtocolloSerializer serializer, ResolveDatiProtocollazioneService datiProtoSrv)
        {
            _logs = logs;
            _serializer = serializer;
            _vert = vert;
            Username = username;
            _password = password;
            _datiProtoSrv = datiProtoSrv;
        }

        private AuthenticationServicePortTypeClient CreaWebService()
        {
            try
            {
                _logs.Debug("Creazione del webservice di autenticazione DOCER");

                var endPointAddress = new EndpointAddress(_vert.UrlLogin);
                var binding = new BasicHttpBinding("DocErHttpBinding");

                if (String.IsNullOrEmpty(_vert.UrlLogin))
                    throw new System.Exception("IL PARAMETRO URL_LOGIN DELLA VERTICALIZZAZIONE PROTOCOLLO_DOCER NON È STATO VALORIZZATO.");

                if (endPointAddress.Uri.Scheme.ToLower() == ProtocolloConstants.HTTPS)
                    binding.Security = new BasicHttpSecurity { Mode = BasicHttpSecurityMode.Transport };

                var ws = new AuthenticationServicePortTypeClient(binding, endPointAddress);

                _logs.Debug("Fine creazione del web service di autenticazione PROTOCOLLO_DOCER");

                return ws;

            }
            catch (System.Exception ex)
            {
                throw new System.Exception(String.Format("ERRORE DURANTE LA CREAZIONE DEL WEB SERVICE DI AUTENTICAZIONE DOCER {0}", ex.Message), ex);
            }
        }

        public void Login()
        {
            try
            {
                using (var ws = CreaWebService())
                {
                    _logs.InfoFormat("AUTENTICAZIONE AL WEB SERVICE, username: {0}, password: {1}, codice ente: {2}, application: {3}", Username, _password, _vert.CodiceEnte, _vert.Applicazione);
                    Token = ws.login(Username, _password, _vert.CodiceEnte, _vert.Applicazione);
                    _logs.InfoFormat("AUTENTICAZIONE AL WEB SERVICE AVVENUTA CON SUCCECCO, token: {0}", Token);
                }
            }
            catch (System.Exception ex)
            {
                throw new System.Exception(String.Format("AUTENTICAZIONE AL WEB SERVICE FALLITA, ERRORE: {0}", ex.Message), ex);
            }
        }

        public void Logout()
        {
            try
            {
                using (var ws = CreaWebService())
                {
                    bool response = ws.logout(Token);
                    if (!response)
                        throw new System.Exception("RESTITUITO IL VALORE FALSE");
                }
            }
            catch (System.Exception ex)
            {
                _logs.WarnFormat("ERRORE GENERATO DURANTE LA LOGOUT AL WEB SERVICE: {0} ", ex.ToString(), ex);
            }
        }

        public KeyValuePair[] GetRuoli(GestioneDocumentaleService gestDocWrapper)
        {
            if (_ruoli == null)
                _ruoli = RuoliDocEr.GetRuoli(gestDocWrapper, _datiProtoSrv, Username);

            return _ruoli;
        }
    }
}
