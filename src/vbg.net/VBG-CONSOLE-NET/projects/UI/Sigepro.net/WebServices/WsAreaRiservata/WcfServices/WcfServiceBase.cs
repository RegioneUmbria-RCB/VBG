using Init.SIGePro.Authentication;
using Init.SIGePro.Exceptions.Token;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace Sigepro.net.WebServices.WsAreaRiservata.WcfServices
{
    public class WcfServiceBase
    {
        protected AuthenticationInfo CheckToken(string token)
        {
            var authInfo = AuthenticationManager.CheckToken(token);

            if (authInfo == null)
                throw new InvalidTokenException(token);

            return authInfo;
        }
    }
}