using Init.SIGePro.Authentication;
using Init.SIGePro.Manager.Authentication;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace Sigepro.net.HttpModules
{
    public class AuthenticationDataInContextModule : IHttpModule
    {
        HttpApplication _application;

        public void Dispose()
        {
        }

        public void Init(HttpApplication context)
        {
            this._application = context;

            context.BeginRequest += context_BeginRequest;
        }

        void context_BeginRequest(object sender, EventArgs e)
        {
            var token = this._application.Request.QueryString["Token"];

            if (String.IsNullOrEmpty(token))
            {
                return;
            }

            var authenticationInfo = AuthenticationManager.CheckToken(token);

            if (authenticationInfo != null)
            {
                this._application.Context.Items[CurrentRequestFromHttpContext.Constants.ContextKeyName] = authenticationInfo;
            }
        }
    }
}