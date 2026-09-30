using Init.SIGePro.Manager.Configuration;
using System;
using System.Web;

namespace SIGePro.Net.Navigation
{
    /// <summary>
    /// Descrizione di riepilogo per NavigationManager.
    /// </summary>
    public class NavigationManager
    {
        public NavigationManager(string baseUrl, string returnTo)
        {
            this.BaseUrl = baseUrl;
            this.ReturnTo = returnTo;
        }

        protected string ReturnTo
        {
            get
            {
                var o = HttpContext.Current.Session["ReturnTo"];

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
                var o = HttpContext.Current.Session["BaseUrl"];
                if (o == null)
                    o = "";
                var baseUrl = o.ToString();

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
                    var url = HttpContext.Current.Request.Url.ToString();
                    var index = url.ToUpper().IndexOf("/" + this.AppAspnet.ToUpper() + "/");

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

        public void RedirectToCallingPage(IConfigurazioneGenerale configurazioneGenerale)
        {
            var returnUrl = this.ReturnTo;

            if (this.ReturnTo.StartsWith("/"))
            {
                HttpContext.Current.Response.Redirect(returnUrl, true);

                return;
            }
            if (!returnUrl.ToUpper().StartsWith("HTTP"))
            {
                var redirectAsp = false;
                var tmpBaseUrl = this.BaseUrl;
                var tmpReturnTo = this.ReturnTo;

                if (!tmpBaseUrl.EndsWith("/"))
                    tmpBaseUrl = tmpBaseUrl + "/";

                if (!tmpReturnTo.StartsWith("/"))
                    tmpReturnTo = "/" + tmpReturnTo;

                if (tmpReturnTo.IndexOf("?") != -1)
                    redirectAsp = tmpReturnTo.ToUpper().IndexOf(".ASP?") != -1;
                else
                    redirectAsp = tmpReturnTo.ToUpper().EndsWith(".ASP");

                if (redirectAsp)
                    tmpBaseUrl += configurazioneGenerale.GetApplicationInfoValue("APP_ASP");

                if (tmpBaseUrl.EndsWith("/") && tmpReturnTo.StartsWith("/"))
                    returnUrl = tmpBaseUrl + tmpReturnTo.Substring(1);
                else
                    returnUrl = tmpBaseUrl + tmpReturnTo;

                this.BaseUrl = "";
                this.ReturnTo = "";
            }

            this.RedirectToSigeproPage(returnUrl, "");
        }



        public void RedirectToSigeproPage(string page, string queryString)
        {
            HttpContext.Current.Response.Redirect(this.BuildSigeproPath(page, queryString), true);
        }


        public string BuildSigeproPath(string page, string queryString)
        {
            var redirUrl = "";

            if (page.ToUpper().IndexOf("HTTP") == 0)
            {
                redirUrl = page;
            }
            else
            {
                redirUrl = this.BaseUrl + page;
            }

            redirUrl += (String.IsNullOrEmpty(queryString) ? "" : "?") + queryString;

            return redirUrl;
        }
    }
}
