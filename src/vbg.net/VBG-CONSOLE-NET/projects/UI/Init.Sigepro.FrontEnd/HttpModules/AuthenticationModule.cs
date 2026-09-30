using System;
using System.Collections.Generic;
using System.Collections.Specialized;
using System.Text;
using System.Web;
using Init.Sigepro.FrontEnd.AppLogic.AreaRiservataService;
using Init.Sigepro.FrontEnd.AppLogic.Autenticazione.Vbg.AutenticazioneUtente;
using Init.Sigepro.FrontEnd.AppLogic.Autenticazione.Vbg.TokenApplicazione;
using Init.Sigepro.FrontEnd.AppLogic.GestioneAnagrafiche;
using Init.Sigepro.FrontEnd.AppLogic.IoC;
using Init.Sigepro.FrontEnd.AppLogic.Services.Navigation;
using Init.Utils;
using log4net;
using Ninject;

namespace Init.Sigepro.FrontEnd.HttpModules
{
	public class AuthenticationModule : IHttpModule
	{
		#region IHttpModule Members
		public void Dispose()
		{
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

            new AuthenticationHelper(ctxt).CheckAuthentication();
        }

        void context_PreRequestHandlerExecute(object sender, EventArgs e)
		{
			// var ctxt = (sender as HttpApplication).Context;
			// new AuthenticationHelper(ctxt).CheckAuthentication();
		}

		void context_BeginRequest(object sender, EventArgs e)
		{

		}

		#endregion
	}
}
