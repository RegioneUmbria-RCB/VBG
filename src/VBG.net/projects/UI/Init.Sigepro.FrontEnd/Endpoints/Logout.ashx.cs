using System;
using System.Web;
using System.Web.Security;
using System.Web.Services;
using System.Web.SessionState;
using Init.Sigepro.FrontEnd.AppLogic.Services.Navigation;

namespace Init.Sigepro.FrontEnd.Endpoints
{
	/// <summary>
	/// Summary description for $codebehindclassname$
	/// </summary>
	[WebService(Namespace = "http://tempuri.org/")]
	[WebServiceBinding(ConformsTo = WsiProfiles.BasicProfile1_1)]
	public class Logout : IHttpHandler, IRequiresSessionState
	{

		public void ProcessRequest(HttpContext context)
		{
			// Calcolo l'url di uscita

			string returnUrl = String.Empty;
			string idComune = context.Request.QueryString["idComune"];
			string software = context.Request.QueryString["software"];

			if (context.Request.Cookies["ReturnTo"] != null)
				returnUrl = context.Request.Cookies["ReturnTo"].Value;

			if (context.Session != null)
				context.Session.Abandon();

			context.Request.Cookies.Clear();
			FormsAuthentication.SignOut();


			if (string.IsNullOrEmpty(returnUrl))
				new RedirectService(context).RedirectToLogoutUrl( idComune, software);

			

			context.Response.Redirect(returnUrl);
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
