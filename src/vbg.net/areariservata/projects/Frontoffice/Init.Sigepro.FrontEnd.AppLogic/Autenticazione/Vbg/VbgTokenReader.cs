////using System;
////using System.Web;
////using log4net;

////namespace Init.Sigepro.FrontEnd.AppLogic.Autenticazione.Vbg
////{
////    /// <summary>
////    /// Descrizione di riepilogo per WsProxy.
////    /// </summary>
////    public class VbgTokenReader
////    {
////        const string SESSION_KEY_TOKEN = "SESSION_KEY_TOKEN";
////        const string SESSION_KEY_LAST_CHECK = "SESSION_KEY_LAST_CHECK";

////        ILog _log = LogManager.GetLogger(typeof(VbgTokenReader));

////        string _idComune;
////        string _token;

////        protected string Token
////        {
////            get
////            {
////                if (String.IsNullOrEmpty(_token))
////                    _token = GetToken();

////                return _token;
////            }
////        }

////        protected VbgTokenReader(string idComune)
////        {
////            _idComune = idComune;
////        }

////        private string GetToken()
////        {
////            const string TOKEN_KEY = "TOKEN_KEY";

////            if (HttpContext.Current == null)
////                return LeggiToken();

////            if (!HttpContext.Current.Items.Contains(TOKEN_KEY))
////            {
////                var token = LeggiToken();

////                HttpContext.Current.Items.Add(TOKEN_KEY, token);
////            }

////            return (string)HttpContext.Current.Items[TOKEN_KEY];
////        }

////        private string LeggiToken()
////        {
////            _log.Debug("Richiesta di un token per operazioni di sistema");

////            string cachedToken = GetTokenInCache(_idComune);

////            if (!string.IsNullOrEmpty(cachedToken))
////                return cachedToken;

////            return LeggiNuovoToken();
////        }

////        private string LeggiNuovoToken()
////        {
////            var ssProxy = new SigeproSecurityProxy();

////            _log.Debug("Lettura di un nuovo token");

////            string idComune = _idComune;
			
////            var request = ssProxy.GetApplicationToken(idComune);

////            var newToken = request;

////            _log.DebugFormat("Il web serivice di login di {0} ha restituito il token {1}", _idComune, newToken);

////            SetTokenInCache(_idComune, newToken);

////            return newToken;

////        }

////        protected static string GetToken(string idComune)
////        {
////            return new VbgTokenReader(idComune).GetToken();
////        }

////        private string GetTokenInCache(string idComune)
////        {
////            if (HttpContext.Current != null)
////            {
////                if (HttpContext.Current.Items.Contains("Token"))
////                {
////                    _log.Debug("Token trovato tra gli items dell'httpcontext");
////                    return HttpContext.Current.Items["Token"].ToString();
////                }
////            }

////            var requestPath = String.Empty;
////            if (HttpContext.Current != null)
////                requestPath = HttpContext.Current.Request.Url.ToString();

////            _log.DebugFormat("Verifica dell'esistenza di un token di sistema nella cache per la richiesta a {0}", requestPath);

////            if (HttpContext.Current == null || HttpContext.Current.Session == null)
////                return String.Empty;

////            object o = HttpContext.Current.Session[SESSION_KEY_TOKEN + "_" + idComune];

////            _log.DebugFormat("Token presente in cache: {0}", o);

////            if (o == null || o.ToString() == String.Empty)
////                return String.Empty;

////            var ssProxy = new SigeproSecurityProxy();

////            var result = ssProxy.CheckToken(o.ToString(), false);

////            _log.DebugFormat("Il token in cache è {0}", result.valid ? "valido" : "non valido");

////            if (!result.valid)
////                return string.Empty;

////            return o.ToString();
////        }

////        private void SetTokenInCache(string idComune, string token)
////        {
////            if (HttpContext.Current != null && HttpContext.Current.Session != null)
////                HttpContext.Current.Session[SESSION_KEY_TOKEN + "_" + idComune] = token;
////        }
////    }
////}
