using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace Sigepro.net.Istanze.SIT.silverbrowser
{
    /// <summary>
    /// Summary description for silverbrowser_bridge
    /// </summary>
    public class silverbrowser_bridge : IHttpHandler
    {

        public void ProcessRequest(HttpContext context)
        {
            context.Response.ContentType = "text/plain";
            context.Response.Write("Hello World");
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