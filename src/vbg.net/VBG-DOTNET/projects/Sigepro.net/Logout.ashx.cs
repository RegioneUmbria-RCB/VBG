using System.IO;
using System.Web;

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

            context.Response.Clear();
            context.Response.ContentType = "image/gif";
            context.Response.BinaryWrite(File.ReadAllBytes(context.Server.MapPath("~/Images/logout_box.gif")));
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
