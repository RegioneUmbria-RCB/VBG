using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Diagnostics;
using Ninject;
using Init.Sigepro.FrontEnd.Infrastructure.Repositories;
using Init.Sigepro.FrontEnd.GestioneMovimenti.Persistence;
using Init.Sigepro.FrontEnd.AppLogic.IoC;

namespace Init.Sigepro.FrontEnd.HttpModules
{
	public class MovimentiUnitOfWorkModule : IHttpModule
	{
		private static class Constants
		{
			public const string ParametroIdMovimento = "IdMovimento";
		}

		private bool IsPaginaMovimento
		{
			get
			{
				return !String.IsNullOrEmpty( this._context.Request.QueryString[Constants.ParametroIdMovimento]);
			}
		}

		[Inject]
		protected IUnitOfWork<GestioneMovimentiDataStore> _unitOfWork { get; set; }



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

			_unitOfWork.Commit();
		}

		void context_BeginRequest(object sender, EventArgs e)
		{
			if (!IsPaginaMovimento)
				return;


			FoKernelContainer.Inject(this);

			_unitOfWork.Begin();
		}
	}
}