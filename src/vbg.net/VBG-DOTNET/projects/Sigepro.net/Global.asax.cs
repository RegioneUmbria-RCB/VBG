using Sigepro.net.Api;
using System;
using System.ComponentModel;
using System.Web;
using System.Web.Http;

namespace SIGePro.Net
{
    /// <summary>
    /// Descrizione di riepilogo per Global.
    /// </summary>
    public class Global : HttpApplication
    {
        /// <summary>
        /// Variabile di progettazione necessaria.
        /// </summary>
        private IContainer components = null;

        public Global()
        {
            InitializeComponent();
        }

        private void InitializeComponent()
        {
        }

        protected void Application_Start(Object sender, EventArgs e)
        {
            GlobalConfiguration.Configure(ApiRegister.Register);
        }

        protected void Session_Start(Object sender, EventArgs e)
        {
        }

        protected void Application_BeginRequest(Object sender, EventArgs e)
        {
        }

        protected void Application_EndRequest(Object sender, EventArgs e)
        {
        }

        protected void Application_AuthenticateRequest(Object sender, EventArgs e)
        {
        }

        protected void Application_Error(Object sender, EventArgs e)
        {
        }

        protected void Session_End(Object sender, EventArgs e)
        {
        }

        protected void Application_End(Object sender, EventArgs e)
        {
        }


    }
}