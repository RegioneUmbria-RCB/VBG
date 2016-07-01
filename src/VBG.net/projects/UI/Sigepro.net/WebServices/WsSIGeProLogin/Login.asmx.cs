using System;
using System.ComponentModel;
using System.Data;
using System.Web.Services;
using Init.SIGePro.Data;
using Init.SIGePro.Authentication;
using Init.SIGePro.Utils;
using Init.SIGePro.Collection;
using Init.SIGePro.Exceptions.Token;
using Init.SIGePro.Manager;
using System.Collections.Generic;
using Sigepro.net.WebServices.WsSIGeProLogin;
using Init.SIGePro.Manager.Authentication;
using Init.SIGePro.Manager.WsSigeproSecurity;
using System.Web;
using log4net;

namespace SIGePro.Net.WebServices.WsSIGeProLogin
{
    /// <summary>
    /// Gestisce l'autenticazione basata su token utilizzata per accedere agli web services di SiGEPro.
    /// </summary>
    [WebService(Namespace = "http://init.sigepro.it")]
    public class Login : WebService
    {
        public Login()
        {
            //CODEGEN: chiamata richiesta da Progettazione servizi Web ASP.NET.
            InitializeComponent();
        }

        #region Codice generato da Progettazione componenti

        //Richiesto da Progettazione servizi Web 
        private IContainer components = null;

        /// <summary>
        /// Metodo necessario per il supporto della finestra di progettazione. Non modificare
        /// il contenuto del metodo con l'editor di codice.
        /// </summary>
        private void InitializeComponent()
        {
        }

        /// <summary>
        /// Pulire le risorse in uso.
        /// </summary>
        protected override void Dispose(bool disposing)
        {
            if (disposing && components != null)
            {
                components.Dispose();
            }
            base.Dispose(disposing);
        }

        #endregion


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
            try
            {
                AuthenticationInfo authInfo = null;
                if (adminOption)
                    authInfo = AuthenticationManager.Login(idComune, userName, password, ContextType.ExternalUsers);
                else
                    authInfo = AuthenticationManager.Login(idComune, userName, password, ContextType.Operatore);

                return authInfo.Token;
            }
            catch (Exception ex)
            {
				var log = LogManager.GetLogger(this.GetType());

				log.ErrorFormat("Login.asmx->Authenticate->{0}\r\nParametri: idcomune={1},userName={2}, password={3}, adminOption={4}", ex.ToString(),
					idComune,  userName,  password, adminOption);

                return null;
            }
        }

        [WebMethod(Description = "Metodo utilizzato per autenticare un richiedente/tecnico nel front-office ", EnableSession = false)]
        public string SSOAuthenticate(string idComune, Anagrafe anagrafica)
        {
            #region Controllo dei parametri passati
            if (string.IsNullOrEmpty(idComune))
                throw new Exception("Non è stato specificato il parametro [idComune]");

            if (anagrafica == null)
                throw new Exception("Non è stato specificato il parametro [anagrafica]");

            if (string.IsNullOrEmpty(anagrafica.CODICEFISCALE) && string.IsNullOrEmpty(anagrafica.PARTITAIVA))
                throw new Exception("E' stata passata un'anagrafica senza Codice Fiscale e Partita IVA. Uno di questi due parametri è obbligatorio per eseguire l'autenticazione");
            #endregion

            return AuthenticationManager.LoginSSO(idComune, anagrafica);
        }

        /// <summary>
        /// Verifica la validità di un token di autenticazione
        /// </summary>
        /// <param name="token">Token da verificare</param>
        /// <returns></returns>
        [WebMethod(Description = "Metodo usato per verificare la validità di un token di autenticazione", EnableSession = false)]
        public string CheckToken(string token)
        {
            try
            {
                return AuthenticationManager.CheckToken(token).Token;
            }
            catch (Exception ex)
            {
				var log = LogManager.GetLogger(this.GetType());

				log.ErrorFormat("Login.asmx->CheckToken->{0}\r\nParametri: token={1}", ex.ToString(),token);

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
            try
            {
                var ta = (TipiAmbiente)Enum.Parse(typeof(TipiAmbiente), ambiente, true);
                return AuthenticationManager.CheckToken(token, ta);
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
            try
            {
				return SecurityInfoMgr.GetSecurityList();
            }
            catch (Exception ex)
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
            AuthenticationInfo authInfo = null;
            try
            {
                authInfo = AuthenticationManager.Login(idComune, userName, password, (ContextType)Enum.Parse(typeof(ContextType), tipoContesto, true));
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
			try
			{
				if (String.IsNullOrEmpty(param))
					return AuthenticationManager.GetApplicationInfo();

				return new ApplicationInfoType[]{ new ApplicationInfoType{ param = param, value= AuthenticationManager.GetApplicationInfoValue(param)}};
			}
			catch (Exception)
			{
				return null;
			}
		}

        #region SEZIONE INTRODOTTA PER INTEGRAZIONE CON ITALSOFT
        [WebMethod(Description = "Metodo usato per effettuare la registrazione di un tecnico", EnableSession = false)]
        public AnagrafeResponse InsertUser(string token, Anagrafe user)
        {
            AnagrafeResponse retVal = new AnagrafeResponse();
            AuthenticationInfo authInfo = null;

            try
            {
                authInfo = AuthenticationManager.CheckToken(token);

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
                AnagrafeMgr anagMgr = new AnagrafeMgr(authInfo.CreateDatabase());

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
            AnagrafeResponse retVal = new AnagrafeResponse();
            AuthenticationInfo authInfo = null;

            try
            {
                authInfo = AuthenticationManager.CheckToken(token);

                if (authInfo == null)
                    throw new InvalidTokenException(token);
            }
            catch (Exception ex)
            {
                retVal.ErrorCode = AuthenticatioErrorCodes.ERR_INVALID_TOKEN;
                retVal.ErrorMessage = ex.Message;
            }

            AnagrafeMgr anagMgr = new AnagrafeMgr(authInfo.CreateDatabase());

            try
            {
                Anagrafe userUpd = new Anagrafe();
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
                        userUpd = ((Anagrafe)list[0]);
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