using Init.SIGePro.Manager.Authentication;
using Init.SIGePro.Manager.WsSigeproSecurity;
using Init.SIGePro.Protocollo.DocEr.GestioneDocumentale;
using Init.SIGePro.Protocollo.ProtocolloDocErGestioneDocumentaleService;
using Init.SIGePro.Protocollo.ProtocolloServices;
using log4net;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.SIGePro.Protocollo.DocEr.Autenticazione
{
    public class AuthenticationServiceLDAP : IAuthenticationService
    {
        ILog _log = LogManager.GetLogger(typeof(AuthenticationServiceLDAP));

        string _tokenSigeproSecurity;
        Dictionary<string, string> _ruoli;
        ResolveDatiProtocollazioneService _datiProtoSrv;

        public string Token { get; private set; }
        public string Username { get; private set; }

        public AuthenticationServiceLDAP(string token, string username, ResolveDatiProtocollazioneService datiProtoSrv)
        {
            _tokenSigeproSecurity = token;
            Username = username;
            _datiProtoSrv = datiProtoSrv;
        }

        public void Login()
        {
            _log.InfoFormat("CHIAMATA A LOGIN LDAP (metodo GetTokenDocErPerComuneeSoftware), TOKEN_DOCER: {0}, TOKEN_SIGEPRO: {1}, CODICECOMUNE: {2}, SOFTWARE: {3}", Token, _tokenSigeproSecurity, _datiProtoSrv.CodiceComune, _datiProtoSrv.Software);
            if(String.IsNullOrEmpty(Token))
            {
                var response = SigeproSecurityProxy.GetTokenDocErPerComuneeSoftware(new GetTokenPartnerAppPerComuneESoftwareRequest
                {
                    token = _tokenSigeproSecurity,
                    codicecomune = _datiProtoSrv.CodiceComune,
                    software = _datiProtoSrv.Software
                });
                Token = response.tokenPartnerApp;
                if (String.IsNullOrEmpty(Token))
                    throw new System.Exception("TOKEN DOCER NON VALORIZZATO");
            }
        }

        public void Logout()
        {
            
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
