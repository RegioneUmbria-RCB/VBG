using Init.SIGePro.Manager.Authentication;
using Init.SIGePro.Manager.WsSigeproSecurity;
using Init.SIGePro.Protocollo.DocEr.GestioneDocumentale;
using Init.SIGePro.Protocollo.ProtocolloDocErGestioneDocumentaleService;
using Init.SIGePro.Protocollo.ProtocolloServices;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.SIGePro.Protocollo.DocEr.Autenticazione
{
    public class AuthenticationServiceLDAP : IAuthenticationService
    {
        string _tokenSigeproSecurity;
        KeyValuePair[] _ruoli;
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
            if(String.IsNullOrEmpty(Token))
            {
                var response = SigeproSecurityProxy.GetTokenDocEr(new GetTokenPartnerAppRequest { token = _tokenSigeproSecurity });
                Token = response.tokenPartnerApp;
            }
        }

        public void Logout()
        {
            
        }

        public KeyValuePair[] GetRuoli(GestioneDocumentaleService gestDocWrapper)
        {
            if (_ruoli == null)
                _ruoli = RuoliDocEr.GetRuoli(gestDocWrapper, _datiProtoSrv, Username);

            return _ruoli;
        }
    }
}
