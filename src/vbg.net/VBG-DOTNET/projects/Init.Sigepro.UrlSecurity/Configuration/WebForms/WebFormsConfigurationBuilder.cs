using System;
using System.Collections.Generic;
using System.Configuration;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Web;

namespace Init.Sigepro.UrlSecurity.Configuration.WebForms
{
    internal class WebFormsConfigurationBuilder
    {
        private static class Constants
        {
            public const string EnabledConfigKey = "UrlSecurity.IsEnabled";
            public const string BlacklistSourceConfigKey = "UrlSecurity.BlacklistSource";
            public const string EndpointsSourceConfigKey = "UrlSecurity.ExcludedEndpointsSource";
        }

        public IUrlSecurityConfiguration BuildConfiguration()
        {
            var isEnabled = ConfigurationManager.AppSettings[Constants.EnabledConfigKey] == "true";
            var blacklistSrc = ConfigurationManager.AppSettings[Constants.BlacklistSourceConfigKey];
            var endpointsSrc = ConfigurationManager.AppSettings[Constants.EndpointsSourceConfigKey];

            if(isEnabled && String.IsNullOrEmpty(blacklistSrc))
            {
                isEnabled = false;
            }

            if(isEnabled)
            {
                blacklistSrc = HttpContext.Current.Server.MapPath(blacklistSrc);

                if(!String.IsNullOrEmpty(endpointsSrc))
                {
                    endpointsSrc = HttpContext.Current.Server.MapPath(endpointsSrc);
                }
            }

            return new FileBasedSecurityConfiguration(isEnabled, blacklistSrc, endpointsSrc);
        }
    }
}
