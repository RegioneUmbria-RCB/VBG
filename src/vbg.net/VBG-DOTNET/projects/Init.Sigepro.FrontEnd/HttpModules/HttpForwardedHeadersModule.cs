using System;
using System.Web;

namespace Init.Sigepro.FrontEnd.HttpModules
{
    public class HttpForwardedHeadersModule : IHttpModule
    {
        public static class Constants
        {
            public const string XForwardedHost = "X-Forwarded-Host";
            public const string XForwardedProto = " X-Forwarded-Proto";
            public const string HttpHost = "HTTP_HOST";
            public const string HttpsFlag = "HTTPS";
        }


        public void Dispose()
        {
        }

        public void Init(HttpApplication context)
        {
            context.BeginRequest += this.Context_BeginRequest;
        }

        private void Context_BeginRequest(object sender, EventArgs e)
        {
            var ctxt = (sender as HttpApplication).Context;
            var serverVars = ctxt?.Request?.ServerVariables;

            if (serverVars == null)
            {
                return;
            }

            var xForwardedHost = serverVars[Constants.XForwardedHost] ?? "";
            var xForwardedProto = serverVars[Constants.XForwardedProto] ?? "";

            if (!String.IsNullOrEmpty(xForwardedHost))
            {
                serverVars["HTTP_HOST"] = xForwardedHost;
            }

            if (xForwardedProto == "https")
            {
                serverVars["HTTPS"] = "on";
            }

        }
    }
}