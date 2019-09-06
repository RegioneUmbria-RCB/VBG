using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Reflection;

namespace Sigepro.net
{
	/// <summary>
	/// Summary description for Version
	/// </summary>
	public class Version : IHttpHandler
	{

		public void ProcessRequest(HttpContext context)
		{
			var asmNamesList = new List<string>();

			var currentAssembly = Assembly.GetExecutingAssembly();

			context.Response.Write("<html><body>");

			asmNamesList.Add(string.Format("<b>{0} v{1}</b>", currentAssembly.GetName().Name, currentAssembly.GetName().Version));


			foreach (var asm in currentAssembly.GetReferencedAssemblies().Where(x => !x.Name.StartsWith("System") && !x.Name.StartsWith("mscorlib") && !x.Name.StartsWith("Microsoft")))
			{
				asmNamesList.Add(string.Format("{0} v{1}", asm.Name, asm.Version));

			}

			foreach (var name in asmNamesList.OrderBy(x => x).Select(x => x))
				context.Response.Write(String.Format("{0}<br />", name));

            context.Response.Write("<br /><br /><h1>Server variables</h1></br>");

            int loop1, loop2;

            // Load ServerVariable collection into NameValueCollection object.
            var coll = context.Request.ServerVariables;
            // Get names of all keys into a string array. 
            String[] arr1 = coll.AllKeys;
            for (loop1 = 0; loop1 < arr1.Length; loop1++)
            {
                context.Response.Write("<b>" + arr1[loop1] + "</b>=");
                String[] arr2 = coll.GetValues(arr1[loop1]);
                for (loop2 = 0; loop2 < arr2.Length; loop2++)
                {
                    context.Response.Write(loop2 + " [" + context.Server.HtmlEncode(arr2[loop2]) + "]<br>");
                }
            }


			context.Response.ContentType = "text/html";
			context.Response.Write("</body></html>");
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