using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.ServiceModel;
using VBG.Backend.Protocollo.AppLogic.Legacy.DocEr.Verticalizzazioni;
using VBG.Backend.Protocollo.AppLogic.Legacy.DocEr.GestioneDocumentale;
using VBG.Backend.Protocollo.AppLogic.Shared.Logs;
using VBG.Backend.Protocollo.AppLogic.Shared.Serialize;
using VBG.Backend.Protocollo.AppLogic.Shared.Services;
using VBG.Shared.Infrastructure.ServiceModel;


namespace VBG.Backend.Protocollo.AppLogic.Legacy.DocEr.Autenticazione
{
    public class AuthenticationService : IAuthenticationService
    {
        private ProtocolloLogs _logs;
        private ProtocolloSerializer _serializer;
        string _password;
        VerticalizzazioniConfiguration _vert;
        Dictionary<string, string> _ruoli;
        ResolveDatiProtocollazioneService _datiProtoSrv;
        private readonly ClientAutenticazioneServiceCreator _clientAutenticazioneServiceCreator;

        public string Token { get; private set; }
        public string Username { get; private set; }

        public AuthenticationService(string username, string password, VerticalizzazioniConfiguration vert, ProtocolloLogs logs, ProtocolloSerializer serializer, ResolveDatiProtocollazioneService datiProtoSrv, IBindingFactory bindingFactory)
        {
            _logs = logs;
            _serializer = serializer;
            _vert = vert;
            Username = username;
            _password = password;
            _datiProtoSrv = datiProtoSrv;
            this._clientAutenticazioneServiceCreator = new ClientAutenticazioneServiceCreator(logs, bindingFactory, vert.UrlLogin);
        }

        public void Login()
        {
            try
            {
                using (var ws = this._clientAutenticazioneServiceCreator.CreateClient())
                {
                    _logs.InfoFormat("AUTENTICAZIONE AL WEB SERVICE, username: {0}, password: {1}, codice ente: {2}, application: {3}", Username, _password, _vert.CodiceEnte, _vert.Applicazione);
                    Token = ws.Service.login(Username, _password, _vert.CodiceEnte, _vert.Applicazione);
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
                using (var ws = this._clientAutenticazioneServiceCreator.CreateClient())
                {
                    bool response = ws.Service.logout(Token);
                    if (!response)
                        throw new System.Exception("RESTITUITO IL VALORE FALSE");
                }
            }
            catch (System.Exception ex)
            {
                _logs.WarnFormat("ERRORE GENERATO DURANTE LA LOGOUT AL WEB SERVICE: {0} ", ex.ToString(), ex);
            }
        }

        //public KeyValuePair[] GetRuoli(GestioneDocumentaleService gestDocWrapper)
        //{
        //    if (_ruoli == null)
        //        _ruoli = RuoliDocEr.GetRuoli(gestDocWrapper, _datiProtoSrv, Username, _datiProtoSrv.CodiceComune, _datiProtoSrv.Software);

        //    return _ruoli;
        //}


        public Dictionary<string, string> GetRuoli(GestioneDocumentaleService gestDocWrapper)
        {
            if (_ruoli == null)
                _ruoli = RuoliDocEr.GetRuoli(gestDocWrapper, _datiProtoSrv, Username, _datiProtoSrv.CodiceComune, _datiProtoSrv.Software);

            return _ruoli;
        }
    }
}
