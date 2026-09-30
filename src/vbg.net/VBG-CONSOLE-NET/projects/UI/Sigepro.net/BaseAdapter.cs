using System;
using System.Data;
using System.Configuration;
using System.Web;
using System.Web.Security;
using System.Web.UI;
using System.Web.UI.WebControls;
using System.Web.UI.WebControls.WebParts;
using System.Web.UI.HtmlControls;
using Init.SIGePro.Authentication;
using Init.SIGePro.Exceptions.Token;

namespace Sigepro.net
{
    public class BaseAdapter
    {
        public static string IdComune
        {
            get 
            {
                string token = HttpContext.Current.Request.QueryString["Token"];

                if (String.IsNullOrEmpty(token))
                    throw new ArgumentException("Token");

                AuthenticationInfo authInfo = AuthenticationManager.CheckToken(token);

                if (authInfo == null)
                    throw new InvalidTokenException(token);

                return authInfo.IdComune;
            }
        }
    }
}
