#if NET48
using Init.Sigepro.FrontEnd.Infrastructure.UrlsAndPaths;
using System.Web;

namespace Init.Sigepro.FrontEnd.Infrastructure.UrlsAndPaths.Framework
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

            return this.GetPathCompletoRootApplicazione() + url.Substring(2);
        }
    }
}
#endif
