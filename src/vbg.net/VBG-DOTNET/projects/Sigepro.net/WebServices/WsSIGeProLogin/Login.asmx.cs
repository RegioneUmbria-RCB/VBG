using Init.SIGePro.Data;
using Init.SIGePro.Exceptions.Token;
using Init.SIGePro.Manager;
using Init.SIGePro.Manager.Authentication;
using Init.SIGePro.Manager.Authentication.Legacy;
using Init.SIGePro.Manager.Authentication.WsSigeproSecurity;
using Init.SIGePro.Manager.Configuration;
using log4net;
using Ninject;
using Sigepro.net.WebServices.WsSIGeProLogin;
using System;
using System.Collections.Generic;
using System.Data;
using System.Web.Services;

namespace SIGePro.Net.WebServices.WsSIGeProLogin
{
    /// <summary>
    /// Gestisce l'autenticazione basata su token utilizzata per accedere agli web services di SiGEPro.
    /// </summary>
    [WebService(Namespace = "http://init.sigepro.it")]
    public class Login : Ninject.Web.WebServiceBase
    {
        private readonly ILog _log = LogManager.GetLogger(typeof(Login));

        [Inject]
        public IAuthenticationManager _authenticationManager { get; set; }
        [Inject]
        public IConfigurazioneGenerale _configurazioneGenerale { get; set; }
        //[Inject]
        //public LoginSSOService _loginSSOService { get; set; }
        [Inject]
        public SecurityListService _securityListService { get; set; }



        /// <summary>
        /// Autentica un utente esterno (ad es. un utente del FO) oppure del comune
        /// </summary>
        /// <param name="idComune">Id del comune su cui autenticarsi</param>
        /// <param name="userName">Username dell'utente</param>
        /// <param name="password">Password dell'utente</param>
        /// <param name="adminOption">Flag per stabilire se si tratta di un'autenticazione effettuata da un utente del comune (FALSE) oppure da un utente esterno (TRUE)</param>
        /// <returns>Token assegnato all'utente o null se l'autenticazione è fallita</returns>
        [WebMethod(Description = "Metodo usato per effettuare l'autenticazione di un utente del comune o di un utente esterno (ad es. FO)", EnableSession = false)]
        public string Authenticate(string idComune, string userName, string password, bool adminOption)
        {
            this._log.InfoFormat("Login.asmx->Authenticate\r\nParametri: idcomune={0},userName={1}, password={2}, adminOption={3}", idComune, userName, password, adminOption);

            try
            {
                AuthenticationInfo authInfo = null;
                if (adminOption)
                    authInfo = this._authenticationManager.Login(idComune, userName, password, ContextType.ExternalUsers);
                else
                    authInfo = this._authenticationManager.Login(idComune, userName, password, ContextType.Operatore);

                return authInfo.Token;
            }
            catch (Exception ex)
            {
                this._log.ErrorFormat("Login.asmx->Authenticate->{0}\r\nParametri: idcomune={1},userName={2}, password={3}, adminOption={4}", ex.ToString(),
                    idComune, userName, password, adminOption);

                return null;
            }
        }
        /*
        [WebMethod(Description = "Metodo utilizzato per autenticare un richiedente/tecnico nel front-office ", EnableSession = false)]
        public string SSOAuthenticate(string idComune, Anagrafe anagrafica)
        {
            this._log.InfoFormat("Login.asmx->SSOAuthenticate\r\nParametri: idcomune={0}, anagrafica={1}", idComune, anagrafica);

            #region Controllo dei parametri passati
            if (string.IsNullOrEmpty(idComune))
                throw new Exception("Non è stato specificato il parametro [idComune]");

            if (anagrafica == null)
                throw new Exception("Non è stato specificato il parametro [anagrafica]");

            if (string.IsNullOrEmpty(anagrafica.CODICEFISCALE) && string.IsNullOrEmpty(anagrafica.PARTITAIVA))
                throw new Exception("E' stata passata un'anagrafica senza Codice Fiscale e Partita IVA. Uno di questi due parametri è obbligatorio per eseguire l'autenticazione");
            #endregion

            return this._loginSSOService.LoginSSO(idComune, anagrafica);
        }
        */
        /// <summary>
        /// Verifica la validità di un token di autenticazione
        /// </summary>
        /// <param name="token">Token da verificare</param>
        /// <returns></returns>
        [WebMethod(Description = "Metodo usato per verificare la validità di un token di autenticazione", EnableSession = false)]
        public string CheckToken(string token)
        {
            this._log.InfoFormat("Login.asmx->CheckToken\r\nParametri: token={0}", token);

            try
            {
                return this._authenticationManager.CheckToken(token).Token;
            }
            catch (Exception ex)
            {
                this._log.ErrorFormat("Login.asmx->CheckToken->{0}\r\nParametri: token={1}", ex.ToString(), token);

                return null;
            }
        }

        /// <summary>
        /// Ritorna le informazioni utili per la connessione al database ricevendo in ingresso il token e l'ambiente
        /// </summary>
        /// <param name="token">Token per il quale si intende recuperare le informazioni per la connessione al database</param>
        /// <param name="ambiente">Ambiente per il quale si intende recuperare le informazioni per la connessione al database</param>
        /// <returns></returns>
        [WebMethod(Description = "Metodo usato per ottenere tutte le informazioni utili per la connessione", EnableSession = false)]
        public AuthenticationInfo GetTokenInfo(string token, string ambiente)
        {
            this._log.InfoFormat("Login.asmx->GetTokenInfo\r\nParametri: token={0}, ambiente={1}", token, ambiente);

            try
            {
                var ta = (TipiAmbiente)Enum.Parse(typeof(TipiAmbiente), ambiente, true);
                return this._authenticationManager.CheckToken(token, ta);
            }
            catch (Exception ex)
            {
                var log = LogManager.GetLogger(this.GetType());

                log.ErrorFormat("Login.asmx->GetTokenInfo->{0}\r\nParametri: token={1}, ambiente={2}", ex.ToString(), token, ambiente);

                return null;
            }
        }

        [WebMethod(Description = "Metodo usato per ottenere la lista dei comuni installati", EnableSession = false)]
        public DataSet SecurityList()
        {
            this._log.InfoFormat("Login.asmx->SecurityList");

            try
            {
                return this._securityListService.GetSecurityList();
            }
            catch (Exception)
            {
                return null;
            }
        }

        /// <summary>
        /// Autentica un utente esterno (ad es. un utente del FO), del comune oppure un tecnico 
        /// </summary>
        /// <param name="idComune">Id del comune su cui autenticarsi</param>
        /// <param name="userName">Username dell'utente</param>
        /// <param name="password">Password dell'utente</param>
        /// <param name="iTipoContesto">Contesto per stabilire se si tratta di un'autenticazione effettuata da un tecnico (1), da un operatore del comune (2), oppure da un utente esterno (4)</param>
        /// <returns>Token assegnato all'utente o null se l'autenticazione è fallita</returns>
        [WebMethod(Description = "Metodo usato per effettuare l'autenticazione di un utente del comune, di un tecnico o di un utente esterno (ad es. FO)", EnableSession = false)]
        public AuthenticationInfo AuthenticateContext(string idComune, string userName, string password, string tipoContesto)
        {
            this._log.InfoFormat("Login.asmx->AuthenticateContext\r\nParametri: idcomune={0},userName={1}, password={2}, tipoContesto={3}", idComune, userName, password, tipoContesto);

            AuthenticationInfo authInfo = null;
            try
            {
                authInfo = this._authenticationManager.Login(idComune, userName, password, (ContextType)Enum.Parse(typeof(ContextType), tipoContesto, true));
            }
            catch (Exception ex)
            {
                var log = LogManager.GetLogger(this.GetType());

                log.ErrorFormat("Login.asmx->AuthenticateContext->{0}\r\nParametri: idcomune={1},userName={2}, password={3}, tipoContesto={4}", ex.ToString(),
                    idComune, userName, password, tipoContesto);

                return null;
            }

            return authInfo;
        }

        /// <summary>
        /// 
        /// </summary>
        /// <returns></returns>
		[WebMethod(Description = "Metodo usato per ottenere la lista dei parametri dell'installazione", EnableSession = false)]
        public ApplicationInfoType[] GetApplicationInfo(string param)
        {
            this._log.InfoFormat("Login.asmx->GetApplicationInfo\r\nParametri: param={0}", param);

            try
            {
                if (String.IsNullOrEmpty(param))
                    return this._authenticationManager.GetApplicationInfo();

                return new ApplicationInfoType[] { new ApplicationInfoType { param = param, value = this._configurazioneGenerale.GetApplicationInfoValue(param) } };
            }
            catch (Exception)
            {
                return null;
            }
        }

        public struct AuthenticatioErrorCodes
        {
            public const int NO_ERR_CODE = 0;
            public const int ERR_DATABASE = 58000;
            public const int ERR_USER_NOT_EXIST = 58001;
            public const int ERR_PASSWORD_NOT_VALID = 58002;
            public const int ERR_INSERT_USER = 58003;
            public const int ERR_UPDATE_PASSWORD = 58004;
            public const int ERR_INVALID_TOKEN = 58005;
            public const int ERR_UTENTI_MULTIPLI = 58006;
            public const int ERR_CFPIVA_NONCORRETTO = 58007;
            public const int ERR_EXTRACT_USER = 58008;
        }

        #region SEZIONE INTRODOTTA PER INTEGRAZIONE CON ITALSOFT
        [WebMethod(Description = "Metodo usato per effettuare la registrazione di un tecnico", EnableSession = false)]
        public AnagrafeResponse InsertUser(string token, Anagrafe user)
        {
            this._log.InfoFormat("Login.asmx->InsertUser\r\nParametri: token={0}, user={1}", token, user.ToString());

            var retVal = new AnagrafeResponse();
            AuthenticationInfo authInfo = null;

            try
            {
                authInfo = this._authenticationManager.CheckToken(token);

                if (authInfo == null)
                    throw new InvalidTokenException(token);
            }
            catch (Exception ex)
            {
                retVal.ErrorCode = AuthenticatioErrorCodes.ERR_INVALID_TOKEN;
                retVal.ErrorMessage = ex.Message;
            }

            try
            {
                var anagMgr = new AnagrafeMgr(authInfo.CreateDatabase());

                user.IDCOMUNE = authInfo.IdComune;
                Anagrafe userIns = anagMgr.Insert(user);
                retVal.CodiceAnagrafe = userIns.CODICEANAGRAFE;
                retVal.ErrorCode = AuthenticatioErrorCodes.NO_ERR_CODE;
            }
            catch (Exception ex)
            {
                retVal.ErrorCode = AuthenticatioErrorCodes.ERR_INSERT_USER;
                retVal.ErrorMessage = ex.Message;
            }

            return retVal;

        }

        [WebMethod(Description = "Metodo usato per effettuare l'aggiornamento della password di un tecnico", EnableSession = false)]
        public AnagrafeResponse UpdatePassword(string token, string user, string newPassword)
        {
            this._log.InfoFormat("Login.asmx->UpdatePassword\r\nParametri: token={0}, user={1}, newPassword={2}", token, user, newPassword);

            var retVal = new AnagrafeResponse();
            AuthenticationInfo authInfo = null;

            try
            {
                authInfo = this._authenticationManager.CheckToken(token);

                if (authInfo == null)
                    throw new InvalidTokenException(token);
            }
            catch (Exception ex)
            {
                retVal.ErrorCode = AuthenticatioErrorCodes.ERR_INVALID_TOKEN;
                retVal.ErrorMessage = ex.Message;
            }

            var anagMgr = new AnagrafeMgr(authInfo.CreateDatabase());

            try
            {
                var userUpd = new Anagrafe();
                userUpd.IDCOMUNE = authInfo.IdComune;
                userUpd.FLAG_DISABILITATO = "0";

                switch (user.Length)
                {
                    case 16:
                        userUpd.CODICEFISCALE = user;
                        break;
                    case 11:
                        userUpd.PARTITAIVA = user;
                        break;
                }
                List<Anagrafe> list = anagMgr.GetList(userUpd);
                if (list.Count != 0)
                {
                    if (list.Count > 1)
                    {
                        retVal.ErrorCode = AuthenticatioErrorCodes.ERR_UPDATE_PASSWORD;
                        retVal.ErrorMessage = "Esistono " + list.Count + " anagrafiche con il codice fiscale/PIVA " + user;
                    }
                    else
                    {
                        userUpd = list[0];
                        userUpd.PASSWORD = newPassword;
                        userUpd = anagMgr.Update(userUpd);
                        retVal.ErrorCode = AuthenticatioErrorCodes.NO_ERR_CODE;
                        retVal.CodiceAnagrafe = userUpd.CODICEANAGRAFE;
                    }
                }
                else
                {
                    retVal.ErrorCode = AuthenticatioErrorCodes.ERR_UPDATE_PASSWORD;
                    retVal.ErrorMessage = "Non esistono anagrafiche con il codice fiscale/PIVA " + user;
                }
            }
            catch (Exception ex)
            {
                retVal.ErrorCode = AuthenticatioErrorCodes.ERR_UPDATE_PASSWORD;
                retVal.ErrorMessage = ex.Message;
            }

            return retVal;
        }
        #endregion
    }
}