namespace Init.Sigepro.FrontEnd.AppLogic.Services.Navigation
{
	using System;
	using System.Text;
	using System.Web;
	using CuttingEdge.Conditions;
		
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

		public NavigationService(HttpContext httpContext)
		{
			this._httpContext = httpContext;
		}

		/// <summary>
		/// Restituisce l'url completo della root dell'applicazione nel formato schema://host:porta/applicationPath
		/// </summary>
		/// <returns></returns>
		public string GetPathCompletoRootApplicazione()
		{
			var req = this._httpContext.Request;
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

		public string GetPathCompletoReservedDefaultPage(string idComune, string software, string token = "", string returnTo = "")
		{
			Condition.Requires(idComune, "idComune").IsNotNullOrEmpty();
			Condition.Requires(software, "software").IsNotNullOrEmpty();

			var sb = new StringBuilder(this.GetPathCompletoRootApplicazione());

			sb.Append(Constants.DefaultPageAreaRiservata)
			  .Append("?")
			  .Append(Constants.ParametroIdComune)
			  .Append("=")
			  .Append(idComune)
			  .Append("&")
			  .Append(Constants.ParametroSoftware)
			  .Append("=")
			  .Append(software);

			if (!string.IsNullOrEmpty(token))
			{
				sb.Append("&")
				  .Append(Constants.ParametroToken)
				  .Append("=")
				  .Append(token);
			}

			if (!string.IsNullOrEmpty(returnTo))
			{
				sb.Append("&")
				  .Append(Constants.ParametroReturnTo)
				  .Append("=")
				  .Append(this._httpContext.Server.UrlEncode(returnTo));
			}

			return sb.ToString();
		}
	}
}
