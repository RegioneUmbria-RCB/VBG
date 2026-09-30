using Init.Sigepro.FrontEnd.QsParameters;
using log4net;
using System;
using System.Collections.Specialized;
using System.Web;

namespace Init.Sigepro.FrontEnd.HttpModules
{
    public class UrlParametersValidationModule : IHttpModule
    {
        ILog _log = LogManager.GetLogger(typeof(UrlParametersValidationModule));
        public void Dispose()
        {
        }

        public void Init(HttpApplication context)
        {
            context.PostAuthorizeRequest += Context_PostAuthorizeRequest;
        }

        private void Context_PostAuthorizeRequest(object sender, EventArgs e)
        {
            try
            {
                ValidateIfExist(QsAliasComune.QuerystringParameterName, (qs) => new QsAliasComune(qs));
                ValidateIfExist(QsSoftware.QuerystringParameterName, (qs) => new QsSoftware(qs));
            }
            catch (Exception ex)
            {
                var guid = Guid.NewGuid().ToString();
                this._log.Error($"[{guid}]Context_PostAuthorizeRequest->{ex}");
                throw new Exception($"Si è verificato un errore inatteso, verificare i log per ulteriori informazioni (rif.errore: {guid})");
            }
        }

        private static void ValidateIfExist(string keyName, Action<NameValueCollection> validationCallback)
        {
            if (!String.IsNullOrEmpty(HttpContext.Current.Request.QueryString[keyName]))
            {
                validationCallback(HttpContext.Current.Request.QueryString);
            }
        }
    }
}