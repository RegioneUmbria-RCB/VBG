using Init.SIGePro.Authentication;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Web;

namespace Init.SIGePro.Manager.Authentication
{
    public class CurrentRequestFromHttpContext : ICurrentRequestContext
    {
        public static class Constants
        {
            public const string ContextKeyName = "CurrentRequestFromHttpContext:CurrentRequestAuthenticationInfo";
        }

        HttpContext _context;

        public CurrentRequestFromHttpContext(HttpContext context)
        {
            this._context = context;
        }

        public AuthenticationInfo GetCurrentUser()
        {
            var authInfo = (AuthenticationInfo)this._context.Items[Constants.ContextKeyName];

            return authInfo;
        }
    }
}
