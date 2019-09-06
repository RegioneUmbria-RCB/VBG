using System;
using System.Collections.Generic;
using System.Configuration;
using System.Linq;
using System.Web;

namespace Init.Sigepro.FrontEnd
{
    /// <summary>
    /// Summary description for extra_css_handler
    /// </summary>
    public class extra_css_handler : IHttpHandler
    {

        public void ProcessRequest(HttpContext context)
        {
            context.Response.ContentType = "text/css";

            var extraCss = ConfigurationManager.AppSettings["Frontend.ExtraCss"];

            if (String.IsNullOrEmpty(extraCss))
            {
                context.Response.TransmitFile("~/styles/extras/blank.css");
            }
            else
            {
                context.Response.TransmitFile(extraCss);

            }
        }

        public bool IsReusable
        {
            get
            {
                return false;
            }
        }
    }
}