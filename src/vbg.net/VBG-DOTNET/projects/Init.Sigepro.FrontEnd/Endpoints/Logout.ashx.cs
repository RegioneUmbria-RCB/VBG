using Init.Sigepro.FrontEnd.AppLogic.Autenticazione.Vbg.AutenticazioneUtente;
using Init.Sigepro.FrontEnd.AppLogic.Common;
using Init.Sigepro.FrontEnd.AppLogic.Services.Navigation;
using Init.Sigepro.FrontEnd.Infrastructure.IOC;
using Ninject;
using System;
using System.Web;
using System.Web.Services;
using System.Web.SessionState;

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
        protected IVbgAuthenticationService _authService { get; set; }

        [Inject]
        protected ITokenResolver _tokenResolver { get; set; }

        [Inject]
        protected ISoftwareResolver _aliasSoftwareResolver { get; set; }

        [Inject]
        protected IRedirectService _redirectService { get; set; }

        [Inject]
        protected IAreaRiservataAuthenticationService _areaRiservataAuthenticationService { get; set; }

        public Logout()
        {
            FoKernelContainer.Inject(this);
        }

        public void ProcessRequest(HttpContext context)
        {
            var returnUrl = String.Empty;
            var token = this._tokenResolver.Token;
            if (String.IsNullOrEmpty(token))
            {
                throw new Exception("Token non valido: " + token);
            }

            if (!String.IsNullOrEmpty(context.Request.QueryString["f"]))
            {
                returnUrl = context.Request.QueryString["f"];
            }
            else
            {
                if (context.Request.Cookies["ReturnTo"] != null)
                    returnUrl = context.Request.Cookies["ReturnTo"].Value;
            }

            this._authService.Logout(token);
            if (context.Session != null)
                context.Session.Abandon();
            context.Request.Cookies.Clear();
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