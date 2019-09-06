using System;
using System.Web;
using System.Web.Security;
using System.Web.Services;
using System.Web.SessionState;
using Init.Sigepro.FrontEnd.AppLogic.Services.Navigation;
using Ninject;
using Init.Sigepro.FrontEnd.AppLogic.Autenticazione.Vbg.AutenticazioneUtente;
using Init.Sigepro.FrontEnd.AppLogic.IoC;
using Init.Sigepro.FrontEnd.Infrastructure.IOC;
using Init.Sigepro.FrontEnd.AppLogic.Common;

namespace Init.Sigepro.FrontEnd.Endpoints
{
	/// <summary>
	/// Summary description for $codebehindclassname$
	/// </summary>
	[WebService(Namespace = "http://tempuri.org/")]
	[WebServiceBinding(ConformsTo = WsiProfiles.BasicProfile1_1)]
	public class Logout : IHttpHandler, IRequiresSessionState
	{
        [Inject]
        protected VbgAuthenticationService _authService { get; set; }
        [Inject]
        protected ITokenResolver _tokenResolver { get; set; }
        [Inject]
        protected IAliasSoftwareResolver _aliasSoftwareResolver { get; set; }
        [Inject]
        protected RedirectService _redirectService { get; set; }

        public Logout()
        {
            FoKernelContainer.Inject(this);
        }

		public void ProcessRequest(HttpContext context)
		{
			// Calcolo l'url di uscita

			var returnUrl = String.Empty;
			var idComune = this._aliasSoftwareResolver.AliasComune;
			var software = this._aliasSoftwareResolver.Software;
            var token = this._tokenResolver.Token;

            if (String.IsNullOrEmpty(token))
            {
                throw new Exception("Token non valido: " + token);
            }

			if (context.Request.Cookies["ReturnTo"] != null)
				returnUrl = context.Request.Cookies["ReturnTo"].Value;

            FormsAuthentication.SignOut();

            if (context.Session != null)
				context.Session.Abandon();

			context.Request.Cookies.Clear();
			
            this._authService.Logout(token);

            if (string.IsNullOrEmpty(returnUrl))
            { 
                this._redirectService.RedirectToLogoutUrl();
            }

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
