using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Web;

namespace Init.Sigepro.FrontEnd.Infrastructure.UrlsAndPaths
{
    public class HttpContextResolveUrl : IResolveUrl
    {
        private string GetPathCompletoRootApplicazione()
        {
            var req = HttpContext.Current.Request;
            var urlAssoluto = req.Url.Scheme + "://" + req.Url.Host + ":" + req.Url.Port;

            if (!string.IsNullOrEmpty(req.ApplicationPath))
            {
                urlAssoluto += req.ApplicationPath;
            }

            if (!urlAssoluto.EndsWith("/"))
            {
                urlAssoluto += "/";
            }

            return urlAssoluto;
        }

        public string ToAbsoluteUrl(string url)
        {
            if (!url.StartsWith("~/"))
            {
                return url;
            }

            return GetPathCompletoRootApplicazione() + url.Substring(2);
        }
    }
}
