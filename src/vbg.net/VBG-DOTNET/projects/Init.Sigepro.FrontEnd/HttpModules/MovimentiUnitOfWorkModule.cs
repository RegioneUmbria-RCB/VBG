using Init.Sigepro.FrontEnd.GestioneMovimenti.Persistence;
using VBG.Shared.Infrastructure.Caching;
using Init.Sigepro.FrontEnd.Infrastructure.IOC;
using Init.Sigepro.FrontEnd.Infrastructure.Repositories;
using System;
using System.Web;

namespace Init.Sigepro.FrontEnd.HttpModules
{
    public class MovimentiUnitOfWorkModule : IHttpModule
    {
        private class MovimentiUnitOfWork
        {
            protected IUnitOfWork<GestioneMovimentiDataStore> _unitOfWork { get; set; }

            public MovimentiUnitOfWork(IUnitOfWork<GestioneMovimentiDataStore> unitOfWork)
            {
                this._unitOfWork = unitOfWork;
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

        public IContextCache _cache { get; set; }

        // public IUnitOfWork<GestioneMovimentiDataStore> _unitOfWork { get; set; }

        private static class Constants
        {
            public const string ParametroIdMovimento = "IdMovimento";
            public const string ContextKey = "MovimentiUnitOfWorkModule.UnitOfWork";
        }

        private bool IsPaginaMovimento
        {
            get
            {
                return !String.IsNullOrEmpty(this._context.Request.QueryString[Constants.ParametroIdMovimento]);
            }
        }

        private HttpApplication _context;
        public void Dispose()
        {
        }

        public void Init(HttpApplication context)
        {
            this._context = context;
            this._cache = FoKernelContainer.GetService<IContextCache>();

            context.BeginRequest += new EventHandler(this.context_BeginRequest);
            context.EndRequest += new EventHandler(this.context_EndRequest);
        }

        private void context_EndRequest(object sender, EventArgs e)
        {
            if (!this.IsPaginaMovimento)
                return;
            var uowObj = this._cache.Get<MovimentiUnitOfWork>(Constants.ContextKey);
            if (uowObj != null)
            {
                uowObj.End();
                this._cache.Set<MovimentiUnitOfWork>(Constants.ContextKey, null);
            }
        }

        private void context_BeginRequest(object sender, EventArgs e)
        {
            if (!this.IsPaginaMovimento)
                return;
            var unitOfWork = FoKernelContainer.GetService<IUnitOfWork<GestioneMovimentiDataStore>>();
            var uow = new MovimentiUnitOfWork(unitOfWork);
            uow.Begin();
            this._cache.Set(Constants.ContextKey, uow);
        }
    }
}