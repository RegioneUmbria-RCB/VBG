using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Diagnostics;
using Ninject;
using Init.Sigepro.FrontEnd.Infrastructure.Repositories;
using Init.Sigepro.FrontEnd.GestioneMovimenti.Persistence;
using Init.Sigepro.FrontEnd.AppLogic.IoC;
using Init.Sigepro.FrontEnd.Infrastructure.IOC;

namespace Init.Sigepro.FrontEnd.HttpModules
{
	public class MovimentiUnitOfWorkModule : IHttpModule
	{
        private class MovimentiUnitOfWork
        {
            [Inject]
            protected IUnitOfWork<GestioneMovimentiDataStore> _unitOfWork { get; set; }

            public MovimentiUnitOfWork(IKernel kernel)
            {
                kernel.Inject(this);
            }

            public void Begin()
            {
                this._unitOfWork.Begin();
            }

            public void End()
            {
                this._unitOfWork.Commit();
            }
        }


		private static class Constants
		{
			public const string ParametroIdMovimento = "IdMovimento";
            public const string ContextKey = "MovimentiUnitOfWorkModule.UnitOfWork";
		}

		private bool IsPaginaMovimento
		{
			get
			{
				return !String.IsNullOrEmpty( this._context.Request.QueryString[Constants.ParametroIdMovimento]);
			}
		}

		HttpApplication _context;

		public void Dispose()
		{

		}

        

		public void Init(HttpApplication context)
		{
			this._context = context;

			context.BeginRequest += new EventHandler(context_BeginRequest);
			context.EndRequest += new EventHandler(context_EndRequest);
		}

		void context_EndRequest(object sender, EventArgs e)
		{
			if (!IsPaginaMovimento)
				return;

            var uowObj = HttpContext.Current.Items[Constants.ContextKey];

            if (uowObj != null)
            {
                var uow = uowObj as MovimentiUnitOfWork;

                uow.End();
                HttpContext.Current.Items[Constants.ContextKey] = null;
            }
			
		}

		void context_BeginRequest(object sender, EventArgs e)
		{
			if (!IsPaginaMovimento)
				return;

            var uow = new MovimentiUnitOfWork(FoKernelContainer.Kernel);

			uow.Begin();

            HttpContext.Current.Items[Constants.ContextKey] = uow;
		}
	}
}