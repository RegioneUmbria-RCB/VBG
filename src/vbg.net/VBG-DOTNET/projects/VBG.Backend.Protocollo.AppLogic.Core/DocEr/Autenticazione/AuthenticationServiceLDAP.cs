using Init.SIGePro.Manager.Authentication;
using Init.SIGePro.Manager.Authentication.WsSigeproSecurity;
using log4net;
using VBG.Backend.Protocollo.AppLogic.Core.DocEr.GestioneDocumentale;
using VBG.Backend.Protocollo.AppLogic.Shared.Services;

namespace VBG.Backend.Protocollo.AppLogic.Core.DocEr.Autenticazione
{
    public class AuthenticationServiceLDAP : IAuthenticationService
    {
        private readonly ILog _log = LogManager.GetLogger(typeof(AuthenticationServiceLDAP));

        private readonly string _tokenSigeproSecurity;
        private Dictionary<string, string> _ruoli;
        private readonly ResolveDatiProtocollazioneService _datiProtoSrv;

        public string Token { get; private set; }
        public string Username { get; private set; }

        public AuthenticationServiceLDAP(string token, string username, ResolveDatiProtocollazioneService datiProtoSrv)
        {
            this._tokenSigeproSecurity = token;
            this.Username = username;
            this._datiProtoSrv = datiProtoSrv;
        }

        public void Login()
        {
            this._log.InfoFormat("CHIAMATA A LOGIN LDAP (metodo GetTokenDocErPerComuneeSoftware), TOKEN_DOCER: {0}, TOKEN_SIGEPRO: {1}, CODICECOMUNE: {2}, SOFTWARE: {3}", this.Token, this._tokenSigeproSecurity, this._datiProtoSrv.CodiceComune, this._datiProtoSrv.Software);
            if (String.IsNullOrEmpty(this.Token))
            {
                var response = new SigeproSecurityProxy().GetTokenDocErPerComuneeSoftware(new GetTokenPartnerAppPerComuneESoftwareRequest
                {
                    token = this._tokenSigeproSecurity,
                    codicecomune = this._datiProtoSrv.CodiceComune,
                    software = this._datiProtoSrv.Software
                });
                this.Token = response.tokenPartnerApp;
                if (String.IsNullOrEmpty(this.Token))
                    throw new InvalidOperationException("TOKEN DOCER NON VALORIZZATO");
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
            if (this._ruoli == null)
                this._ruoli = RuoliDocEr.GetRuoli(gestDocWrapper, this._datiProtoSrv, this.Username, this._datiProtoSrv.CodiceComune, this._datiProtoSrv.Software);

            return this._ruoli;
        }
    }
}
