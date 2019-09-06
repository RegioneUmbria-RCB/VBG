namespace Init.Sigepro.FrontEnd.AppLogic.Services.Navigation
{
	using System;
	using System.Text;
	using System.Web;
	using CuttingEdge.Conditions;
    using System.Configuration;
    using Init.Sigepro.FrontEnd.AppLogic.Common;
    using Init.Sigepro.FrontEnd.Infrastructure.UrlsAndPaths;

    public class NavigationService
	{
		private static class Constants
		{
			public const string DefaultPageAreaRiservata = "Reserved/default.aspx";
			public const string ParametroIdComune = "idComune";
			public const string ParametroToken = "token";
			public const string ParametroSoftware = "software";
			public const string ParametroReturnTo = "returnTo";
		}

		private HttpContext _httpContext;
        IAliasSoftwareResolver _aliasSoftwareResolver;


        public NavigationService(HttpContext httpContext, IAliasSoftwareResolver aliasSoftwareResolver)
		{
			this._httpContext = httpContext;
            this._aliasSoftwareResolver = aliasSoftwareResolver;
		}

		/// <summary>
		/// Restituisce l'url completo della root dell'applicazione nel formato schema://host:porta/applicationPath
		/// </summary>
		/// <returns></returns>
		public string GetPathCompletoRootApplicazione()
		{
            var req = this._httpContext.Request;
			//var urlAssoluto = req.Url.Scheme + "://" + req.Url.Host + ":" + req.Url.Port;
            var urlAssoluto = "//" + req.Url.Host;

            if (!String.IsNullOrEmpty(ConfigurationManager.AppSettings["NavigationService.UsaPortaSuUrlRedirect"]))
            {
                urlAssoluto = "//" + req.Url.Host + ":" + req.Url.Port;
            }
            
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

		public string GetPathCompletoReservedDefaultPage(string returnTo = "")
		{
            var url = this.GetPathCompletoRootApplicazione() + UrlBuilder.Url(Constants.DefaultPageAreaRiservata, x =>
            {
                x.Add(Constants.ParametroIdComune, this._aliasSoftwareResolver.AliasComune);
                x.Add(Constants.ParametroSoftware, this._aliasSoftwareResolver.Software);

                if (!string.IsNullOrEmpty(returnTo))
                {
                    x.Add(Constants.ParametroReturnTo, this._httpContext.Server.UrlEncode(returnTo));
                }
            });

			return url;
		}
	}
}
