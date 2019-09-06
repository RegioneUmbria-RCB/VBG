using System;
using System.Web;
using Init.Utils;
using System.Configuration;
using Init.SIGePro.Verticalizzazioni;
using PersonalLib2.Data;
using Init.SIGePro.Authentication;
using Init.SIGePro.Utils;
using System.Security.Policy;

namespace SIGePro.Net.Navigation
{
	/// <summary>
	/// Descrizione di riepilogo per NavigationManager.
	/// </summary>
	public class NavigationManager
	{
		public NavigationManager(string baseUrl, string returnTo)
		{
			BaseUrl = baseUrl;
			ReturnTo = returnTo;
		}

		protected string ReturnTo
		{
			get
			{
				object o = HttpContext.Current.Session["ReturnTo"];

				if (o == null)
					return "cl_start.asp?noscad=1";

				return o.ToString();
			}
			private set
			{
				if (!String.IsNullOrEmpty(value))
					HttpContext.Current.Session["ReturnTo"] = value;
			}
		}

		public string AppAspnet
		{
			get { return "ASPNET"; }
		}



		public string BaseUrl
		{
			get
			{
				//questo primo pezzo viene mantenuto per compatibilità finchè sigepro continuerà ad essere avviato dal container ASP e non JAVA
				//dopo di che andranno tolte le righe fino al commento del punto 1 escluso ( che va scommentato e implementato )
				object o = HttpContext.Current.Session["BaseUrl"];
				if (o == null)
					o = "";
				string baseUrl = o.ToString();

				if (!string.IsNullOrEmpty(baseUrl))
				{
					if (!baseUrl.EndsWith("/"))
						baseUrl += "/";
				}

				//1. La base url va prima riletta dalla configurazione dal parametro BASE_URL
				//baseUrl = 

				//2. Se non impostato va calcolato
				if (string.IsNullOrEmpty(baseUrl))
				{
					string url = HttpContext.Current.Request.Url.ToString();
					int index = url.ToUpper().IndexOf("/" + AppAspnet.ToUpper() + "/");

					if (index >= 0)
						baseUrl = url.Substring(0, index);
				}

				if (!baseUrl.EndsWith("/"))
					baseUrl += "/";

				return baseUrl;
			}

			private set
			{
				if (!String.IsNullOrEmpty(value))
					HttpContext.Current.Session["BaseUrl"] = value;
			}
		}


		//public string BaseUrl
		//{
		//    get
		//    {
		//        //1. La base url va prima riletta dalla configurazione dal parametro BASE_URL
		//        //2. Se non impostato va calcolato
		//        object o = HttpContext.Current.Session["BaseUrl"];

		//        if (o == null)
		//            o = "";

		//        string baseUrl = o.ToString();

		//        if (!baseUrl.EndsWith("/"))
		//            baseUrl += "/";

		//        return baseUrl;
		//    }

		//    private set
		//    {
		//        if (!String.IsNullOrEmpty(value))
		//            HttpContext.Current.Session["BaseUrl"] = value;
		//    }
		//}

		public void RedirectToCallingPage()
		{
            string returnUrl = ReturnTo;

            if (!returnUrl.ToUpper().StartsWith("HTTP"))
			{
				var redirectAsp = false;
				var tmpBaseUrl = BaseUrl;
				var tmpReturnTo = ReturnTo;

				if (!tmpBaseUrl.EndsWith("/"))
					tmpBaseUrl = tmpBaseUrl + "/";

				if (!tmpReturnTo.StartsWith("/"))
					tmpReturnTo = "/" + tmpReturnTo;

				if (tmpReturnTo.IndexOf("?") != -1)
					redirectAsp = tmpReturnTo.ToUpper().IndexOf(".ASP?") != -1;
				else
					redirectAsp = tmpReturnTo.ToUpper().EndsWith(".ASP");

				if (redirectAsp)
					tmpBaseUrl += AuthenticationManager.GetApplicationInfoValue("APP_ASP");

				if (tmpBaseUrl.EndsWith("/") && tmpReturnTo.StartsWith("/"))
					returnUrl = tmpBaseUrl + tmpReturnTo.Substring(1);
                else
					returnUrl = tmpBaseUrl + tmpReturnTo;

                BaseUrl = "";
                ReturnTo = "";
            }

			RedirectToSigeproPage(returnUrl, "");
		}



		public void RedirectToSigeproPage(string page, string queryString)
		{
			HttpContext.Current.Response.Redirect(BuildSigeproPath(page, queryString) , true );
		}


		public string BuildSigeproPath(string page, string queryString)
		{
			string redirUrl = "";

			if (page.ToUpper().IndexOf("HTTP") == 0)
			{
				redirUrl = page;
			}
			else
			{
				redirUrl = BaseUrl + page;
			}

			redirUrl += (StringChecker.IsStringEmpty(queryString) ? "" : "?") + queryString;

			return redirUrl;
		}
	}
}
