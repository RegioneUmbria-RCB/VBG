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
using PersonalLib2.Data;
using System.Collections.Generic;
using System.Text;

namespace Sigepro.net
{
    public class BaseHandler
    {
        protected string Token
        {
            get { return HttpContext.Current.Request.QueryString["Token"]; }
        }

        private AuthenticationInfo m_authenticationInfo = null;
        public AuthenticationInfo AuthenticationInfo
        {
            get
            {
                if (m_authenticationInfo == null)
                {
                    m_authenticationInfo = AuthenticationManager.CheckToken(Token);

                    if (m_authenticationInfo == null)
                    {
                        //NavigationManager.RedirectToSigeproPage("sessionescaduta.asp", "");
                    }
                }

                return m_authenticationInfo;
            }
        }

        private DataBase m_db = null;
        public DataBase Database
        {
            get
            {
                if (m_db == null)
                    m_db = AuthenticationInfo.CreateDatabase();

                return m_db;
            }
        }

        protected string CreaResponse(Dictionary<string, object> dicValori)
        {
            StringBuilder sb = new StringBuilder();

            bool isFirst = true;

            foreach( string key in dicValori.Keys )
            {
                if (!isFirst)
                    sb.Append(Environment.NewLine);
             

                sb.Append(key).Append("=").Append(dicValori[key]);
                isFirst = false;
            }

            return sb.ToString();
        }

        public string IdComune
        {
            get
            {
                return AuthenticationInfo.IdComune;
            }
        }

        public string IdComuneAlias
        {
            get
            {
                return AuthenticationInfo.Alias;
            }
        }
    }
}
