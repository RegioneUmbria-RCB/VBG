using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Web;

namespace Init.Sigepro.FrontEnd.Infrastructure.UrlsAndPaths
{
    public class HttpContextUrlEncoder : IUrlEncoder
    {
        public string UrlEncode(string value)
        {
            if (HttpContext.Current == null)
            {
                throw new InvalidOperationException("Non esiste un contesto http nella chiamata corrente");
            }

            return HttpContext.Current.Server.UrlEncode(value);
        }
    }
}
