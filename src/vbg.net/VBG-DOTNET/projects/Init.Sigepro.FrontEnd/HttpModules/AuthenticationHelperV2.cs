//using log4net;
//using System;
//using System.Collections.Generic;
//using System.Configuration;
//using System.Linq;
//using System.Web;

//namespace Init.Sigepro.FrontEnd.HttpModules
//{
//    public class AuthenticationHelperV2
//    {
//        bool _usaPathRelativo = false;
//        bool _usaNginx = false;
//        HttpContext _context;
//        ILog logger = LogManager.GetLogger(typeof(AuthenticationModule));

//        public AuthenticationHelperV2(HttpContext context)
//        {
//            this._usaPathRelativo = GetConfigValue("UsaUrlRelativiPerRedirect");
//            this._usaNginx = GetConfigValue("Nginx.Enabled");

//            this._context = context;
//        }

//        private bool GetConfigValue(string configKey)
//        {
//            var val = ConfigurationManager.AppSettings[configKey];

//            if (!String.IsNullOrEmpty(val) && val.ToUpper() == "TRUE")
//            {
//                return true;
//            }

//            return false;
//        }

//        public void CheckAuthentication()
//        {
//            var completePath = this._context.Request.Url.ToString();
//            var path = this._context.Request.Url.LocalPath;

//        }
//    }
//}