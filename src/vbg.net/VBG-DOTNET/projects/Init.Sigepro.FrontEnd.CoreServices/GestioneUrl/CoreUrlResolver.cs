using Init.Sigepro.FrontEnd.Infrastructure.UrlsAndPaths.Standard;
using log4net;
using Microsoft.AspNetCore.Components;
using Microsoft.AspNetCore.Http;
using System.Diagnostics;

namespace Init.Sigepro.FrontEnd.CoreServices.GestioneUrl
{
    internal class CoreUrlResolver : ICoreUrlResolver
    {
        private readonly IHttpContextAccessor _httpContextAccessor;
        private readonly NavigationManager _navigationManager;
        private readonly ILog _log = LogManager.GetLogger(typeof(CoreUrlResolver));

        public CoreUrlResolver(IHttpContextAccessor httpContextAccessor, NavigationManager navigationManager)
        {
            this._httpContextAccessor = httpContextAccessor;
            this._navigationManager = navigationManager;
        }
        public string ToAbsoluteUrl(string url)
        {
            if (!url.StartsWith("~"))
            {
                return url;
            }

            var baseUrl = "";

            // Nelle applicazioni blazor l'httpcontext potrebbe non essere disponibile 
            // vd alcuni casi sotto IIS
            if (this._httpContextAccessor?.HttpContext == null)
            {
                this._log.Info($"Non è stato possibile recuperare l'httpcontext per la richiesta corrente. Verrà utilizzato il NavigationManager. BaseUri={this._navigationManager.BaseUri}");
                baseUrl = this._navigationManager.BaseUri;
            }
            else
            {
                this._log.Info($"Per recuperare il path base verrà utilizzato l'httpcontext");

                var req = this._httpContextAccessor.HttpContext.Request;

                if (req == null)
                {
                    this._log.Info($"Request corrente non recuperata");
                    throw new ArgumentException("ToAbsoluteUrl:Impossibile recuperare la request corrente");
                }

                Debug.WriteLine($"{this._navigationManager.BaseUri}");

                var uriBuilder = new UriBuilder(req.Scheme, req.Host.Host, req.Host.Port ?? -1, req.PathBase);

                if (uriBuilder.Uri.IsDefaultPort)
                {
                    uriBuilder.Port = -1;
                }

                baseUrl = uriBuilder.Uri.AbsoluteUri;
            }

            if (!baseUrl.EndsWith("/"))
            {
                baseUrl += "/";
            }

            this._log.Info($"baseUrl={baseUrl}");

            var absolute = baseUrl + url.Substring(2);

            this._log.Debug($"Url \"{url}\" reso assoluto in \"{absolute}\"");

            return absolute;
        }
    }
}
