using Init.Sigepro.FrontEnd.AppLogic.Utils;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Web.Security;

namespace Init.Sigepro.FrontEnd.AppLogic.Common
{

    // Il token viene fatto persistere tra nel chiamate tramite il cookie di autenticazione di asp.net
    public class IdentityTokenResolver : ITokenResolver
    {
        IResolveHttpContext _httpContext;

        public IdentityTokenResolver(IResolveHttpContext httpContext)
        {
            this._httpContext = httpContext;
        }


        public string Token
        {
            get
            {
                if(this._httpContext.Get.User == null)
                {
                    return String.Empty;
                }

                return this._httpContext.Get.User.Identity.Name;
            }
        }
    }
}
