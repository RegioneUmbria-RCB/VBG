using Init.Sigepro.FrontEnd.Infrastructure.IOC;
using Init.Sigepro.UrlSecurity.Runtime;
using Ninject;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace Init.Sigepro.FrontEnd.HttpModules
{
    public class UrlSecurityModule : IHttpModule
    {
        HttpApplication _context;
        [Inject]
        public IUrlSecurityRuntime _urlSecurityRuntime { get; set; }

        public void Dispose()
        {
        // throw new NotImplementedException();
        }

        public void Init(HttpApplication context)
        {
            context.AuthorizeRequest += Context_AuthorizeRequest;
            this._context = context;
        }

        private void Context_AuthorizeRequest(object sender, EventArgs e)
        {
            if (FoKernelContainer.Kernel != null)
            {
                FoKernelContainer.Kernel.Inject(this);
                _urlSecurityRuntime.CheckUrl(this._context.Request.Url, this._context.Request.QueryString);
            }
        }
    }
}