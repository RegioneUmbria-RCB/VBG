using System;
using System.ComponentModel;
using System.Net;
using System.Web;

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
        private readonly IContainer components = null;

        public Global()
        {
            this.InitializeComponent();
        }

        private void InitializeComponent()
        {
        }

        protected void Application_Start(Object sender, EventArgs e)
        {
            ServicePointManager.SecurityProtocol = SecurityProtocolType.Tls12;
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