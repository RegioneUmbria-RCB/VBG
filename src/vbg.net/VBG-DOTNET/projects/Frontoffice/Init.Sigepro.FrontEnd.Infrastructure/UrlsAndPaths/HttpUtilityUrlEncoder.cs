using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Web;

namespace Init.Sigepro.FrontEnd.Infrastructure.UrlsAndPaths
{
    public class HttpUtilityUrlEncoder : IUrlEncoder
    {
        public string UrlEncode(string value)
        {
            return HttpUtility.UrlEncode(value);
        }
    }
}
