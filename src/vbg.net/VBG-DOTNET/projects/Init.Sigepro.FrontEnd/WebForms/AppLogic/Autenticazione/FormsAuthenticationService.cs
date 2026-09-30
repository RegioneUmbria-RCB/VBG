using Init.Sigepro.FrontEnd.AppLogic.Autenticazione.Vbg.AutenticazioneUtente;
using Init.Sigepro.FrontEnd.AppLogic.Common;
using System;
using System.Web;
using System.Web.Security;

namespace Init.Sigepro.FrontEnd.WebForms.AppLogic.Autenticazione
{
    public class FormsAuthenticationService : IAreaRiservataAuthenticationService, ITokenResolver
    {
        public string Token => this.GetCurrentUserIdentity();

        public FormsAuthenticationService()
        {
        }

        public void AuthenticateUser(string token)
        {
            FormsAuthentication.SetAuthCookie(token, false);
        }

        public string GetCurrentUserIdentity()
        {
            if (HttpContext.Current.User == null)
            {
                return String.Empty;
            }

            return HttpContext.Current.User.Identity.Name;
        }

        public void SignOut()
        {
            FormsAuthentication.SignOut();
        }
    }
}