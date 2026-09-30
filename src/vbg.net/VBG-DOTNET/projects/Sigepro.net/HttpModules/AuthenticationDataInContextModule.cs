using Init.SIGePro.Manager.Authentication;
using Init.SIGePro.Manager.IOC;
using System;
using System.Web;

namespace Sigepro.net.HttpModules
{
    public class AuthenticationDataInContextModule : IHttpModule
    {
        private HttpApplication _application;

        public IAuthenticationManager _authenticationManager { get; set; }
        public HttpContextAuthenticationInfoResolver _httpContextAuthenticationInfoResolver { get; set; }

        public void Dispose()
        {
        }

        public void Init(HttpApplication context)
        {
            this._application = context;

            context.BeginRequest += this.context_BeginRequest;

            this._authenticationManager = StaticKernelContainer.GetService<IAuthenticationManager>();
            this._httpContextAuthenticationInfoResolver = StaticKernelContainer.GetService<HttpContextAuthenticationInfoResolver>();
        }

        private void context_BeginRequest(object sender, EventArgs e)
        {
            var token = this._application.Request.QueryString["Token"];

            if (String.IsNullOrEmpty(token))
            {
                return;
            }

            var authenticationInfo = this._authenticationManager.CheckToken(token);

            if (authenticationInfo != null)
            {
                this._httpContextAuthenticationInfoResolver.SetTransientAuthInfo(authenticationInfo);
                this._application.Context.Items["Token"] = token;
                this._application.Context.Items["IdComune"] = authenticationInfo.IdComune;
                this._application.Context.Items["Software"] = this._application.Request.QueryString["Software"];
            }
        }
    }
}