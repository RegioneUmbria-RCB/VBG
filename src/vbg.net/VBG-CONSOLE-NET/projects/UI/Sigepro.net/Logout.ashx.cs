using System;
using System.Collections.Generic;
using System.Web;
using Init.Utils;
using System.IO;

namespace Sigepro.net
{
    /// <summary>
    /// Summary description for $codebehindclassname$
    /// </summary>
    public class Logout : IHttpHandler
    {

        public void ProcessRequest(HttpContext context)
        {
            // TODO: svuotare tutte le sessioni 
			if (context.Session != null)
				context.Session.Abandon();

            var buff = StreamUtils.StreamToBytes( StreamUtils.FileToStream(context.Server.MapPath("~/Images/logout_box.gif")));
            context.Response.Clear();
            context.Response.ContentType = "image/gif";
            context.Response.BinaryWrite(buff);

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
