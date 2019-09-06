using System;
using System.Collections.Generic;
using System.Collections.Specialized;
using System.Linq;
using System.Web;

namespace Init.Sigepro.FrontEnd.HttpModules
{
    public class ApplicationAuthenticationType
    {
        private static class Constants
        {
            public const string AuthenticationType = "AUTH_TYPE";
            public const string AuthenticationTypeLogin = "AUTH_TYPE_LOGIN";
            public const string AuthenticationTypeLoginSmartCard = "AUTH_TYPE_LOGIN_SC";
            public const string AuthenticationTypeLoginSmartSso = "AUTH_TYPE_LOGIN_SSO";
            public const string AuthenticationTypeReg = "AUTH_TYPE_REG";
            public const string AuthenticationTypeRegSc = "AUTH_TYPE_REG_SC";
            public const string AuthenticationTypeRegSso = "AUTH_TYPE_REG_SSO";
        }

        readonly string _authenticationType;

        protected ApplicationAuthenticationType(string authenticationType)
        {
            this._authenticationType = authenticationType;
        }

        public bool IsRegistrazione
        {
            get { return this._authenticationType == Constants.AuthenticationTypeReg; }
        }

        public bool IsRegistrazioneSmartCard
        {
            get { return this._authenticationType == Constants.AuthenticationTypeRegSc; }
        }

        public bool IsRegistrazioneSSO
        {
            get { return this._authenticationType == Constants.AuthenticationTypeRegSso; }
        }

        public bool IsLogin
        {
            get { return this._authenticationType == Constants.AuthenticationTypeLogin ||
                         this._authenticationType == Constants.AuthenticationTypeLoginSmartCard ||
                         this._authenticationType == Constants.AuthenticationTypeLoginSmartSso; }
        }
        /*
        public static ApplicationAuthenticationType FromQuerystring(NameValueCollection querystringParams)
        {
            return new ApplicationAuthenticationType(querystringParams[Constants.AuthenticationType]);
        }
        */


        public static ApplicationAuthenticationType FromRequest(HttpRequest request)
        {
            var param = request.QueryString[Constants.AuthenticationType];

            if (String.IsNullOrEmpty(param))
            {
                param = request.Form[Constants.AuthenticationType];
            }

            return new ApplicationAuthenticationType(param);
        }

        public override string ToString()
        {
            return this._authenticationType;
        }
    }
}