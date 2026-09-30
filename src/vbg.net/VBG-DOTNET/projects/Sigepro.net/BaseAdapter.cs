using Init.SIGePro.Manager.Authentication;
using Init.SIGePro.Exceptions.Token;
using System;
using System.Web;

namespace Sigepro.net
{
    public class BaseAdapter
    {
        private readonly IAuthenticationManager _authenticationManager;

        public BaseAdapter(IAuthenticationManager authenticationManager)
        {
            this._authenticationManager = authenticationManager;
        }

        public string IdComune
        {
            get
            {
                string token = HttpContext.Current.Request.QueryString["Token"];

                if (String.IsNullOrEmpty(token))
                    throw new ArgumentException("Token");

                AuthenticationInfo authInfo = this._authenticationManager.CheckToken(token);

                if (authInfo == null)
                    throw new InvalidTokenException(token);

                return authInfo.IdComune;
            }
        }
    }
}
