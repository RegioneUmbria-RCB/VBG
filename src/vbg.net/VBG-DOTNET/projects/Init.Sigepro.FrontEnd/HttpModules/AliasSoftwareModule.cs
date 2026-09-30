using Init.Sigepro.FrontEnd.QsParameters;
using System;
using System.Web;

namespace Init.Sigepro.FrontEnd.HttpModules
{
    public class AliasSoftwareModule : IHttpModule
    {
        public void Dispose()
        {
            throw new NotImplementedException();
        }

        public void Init(HttpApplication context)
        {
            context.BeginRequest += new EventHandler(context_BeginRequest);
            context.PreRequestHandlerExecute += new EventHandler(context_PreRequestHandlerExecute);
            context.AuthorizeRequest += Context_AuthorizeRequest;
        }

        private void Context_AuthorizeRequest(object sender, EventArgs e)
        {
            var ctxt = (sender as HttpApplication).Context;
            ctxt.Items["IdComune"] = new QsAliasComune(ctxt.Request.QueryString);
            if (!String.IsNullOrEmpty(ctxt.Request.QueryString["software"]))
            {
                ctxt.Items["Software"] = new QsSoftware(ctxt.Request.QueryString);
            }
        }

        void context_PreRequestHandlerExecute(object sender, EventArgs e)
        {
        }

        void context_BeginRequest(object sender, EventArgs e)
        {
        }
    }
}