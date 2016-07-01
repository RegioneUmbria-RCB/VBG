//using System;
//using System.Web;
//using Init.Sigepro.FrontEnd.AppLogic.Adapters;
//using Init.Sigepro.FrontEnd.AppLogic.Configurazione.V2;
//using Init.Sigepro.FrontEnd.AppLogic.Repositories.Interfaces;
//using log4net;

//namespace Init.Sigepro.FrontEnd.AppLogic.Autenticazione.Vbg
//{
//    public class VbgAuthenticationService
//    {
//        IAnagrafeRepository _anagrafeRepository;
//        IConfigurazione<ParametriLogin> _configurazione;
//        SigeproSecurityProxy _sigeproSecurityProxy;

//        ILog m_logger = LogManager.GetLogger(typeof(VbgAuthenticationService));


//        public VbgAuthenticationService(IAnagrafeRepository anagrafeRepository, IConfigurazione<ParametriLogin> configurazione, SigeproSecurityProxy sigeproSecurityProxy)
//        {
//            this._anagrafeRepository = anagrafeRepository;
//            this._configurazione = configurazione;
//            this._sigeproSecurityProxy = sigeproSecurityProxy;
//        }


//        public void RedirectToAuthenticationPage(string idComune , string software, string returnTo)
//        {
//            string authUrl =  this._configurazione.Parametri.UrlLogin;

//            var queryStringArgs = "?idcomunealias={0}&contesto=UTE&return_to={1}";

//            authUrl += String.Format( queryStringArgs , idComune , HttpContext.Current.Server.UrlEncode( returnTo ) );

//            m_logger.DebugFormat("Redirect alla pagina di login {0} per idcomune={1} e software={2}", authUrl , idComune , software);

//            HttpContext.Current.Response.Redirect(authUrl);
//        }

//        public UserAuthenticationResult CheckToken(string token)
//        {
//            var result = this._sigeproSecurityProxy.CheckToken(token);

//            if (!result.valid)
//                return null;

//            var datiAnagrafici = _anagrafeRepository.GetByUserId(result.tokenInfo.alias, result.tokenInfo.userid);

//            if (datiAnagrafici == null)
//            {
//                m_logger.ErrorFormat("Non è stato possibile leggere i dati dell'anagrafica con codice {0}", result.tokenInfo.userid);

//                return null;
//            }

//            var anagraficaAdattata = new AnagrafeAdapter(datiAnagrafici).ToAnagraficaUtente();

//            return new UserAuthenticationResult(token, result.tokenInfo.alias , anagraficaAdattata );
//        }
//    }
//}
