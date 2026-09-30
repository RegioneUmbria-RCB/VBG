using Init.SIGePro.Manager.Authentication;
using Ninject;
using PersonalLib2.Data;
using System;
using System.Collections.Generic;
using System.Text;
using System.Web;

namespace Sigepro.net
{
    public abstract class BaseHandler : Ninject.Web.HttpHandlerBase
    {
        [Inject]
        public IAuthenticationManager _authenticationManager { get; set; }

        protected string Token
        {
            get { return HttpContext.Current.Request.QueryString["Token"]; }
        }

        private AuthenticationInfo m_authenticationInfo = null;
        public AuthenticationInfo AuthenticationInfo
        {
            get
            {
                if (this.m_authenticationInfo == null)
                {
                    this.m_authenticationInfo = this._authenticationManager.CheckToken(this.Token);

                    if (this.m_authenticationInfo == null)
                    {
                        //NavigationManager.RedirectToSigeproPage("sessionescaduta.asp", "");
                    }
                }

                return this.m_authenticationInfo;
            }
        }

        private DataBase m_db = null;
        public DataBase Database
        {
            get
            {
                if (this.m_db == null)
                    this.m_db = this.AuthenticationInfo.CreateDatabase();

                return this.m_db;
            }
        }

        protected string CreaResponse(Dictionary<string, object> dicValori)
        {
            StringBuilder sb = new StringBuilder();

            bool isFirst = true;

            foreach (string key in dicValori.Keys)
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
                return this.AuthenticationInfo.IdComune;
            }
        }

        public string IdComuneAlias
        {
            get
            {
                return this.AuthenticationInfo.Alias;
            }
        }

        public override bool IsReusable => throw new NotImplementedException();
    }
}
