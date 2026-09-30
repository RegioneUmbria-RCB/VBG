// -----------------------------------------------------------------------
// <copyright file="GestioneMovimentiHttpDataContext.cs" company="">
// TODO: Update copyright text.
// </copyright>
// -----------------------------------------------------------------------

using Init.Sigepro.FrontEnd.GestioneMovimenti.ExternalServices;
using VBG.Shared.Infrastructure.Caching;
using Init.Sigepro.FrontEnd.Infrastructure.Repositories;
using log4net;


namespace Init.Sigepro.FrontEnd.GestioneMovimenti.Persistence
{
    /// <summary>
    /// TODO: Update summary.
    /// </summary>
    public class GestioneMovimentiHttpDataContext : IGestioneMovimentiDataContext, IUnitOfWork<GestioneMovimentiDataStore>
    {
        private static class Constants
        {
            public const string HttpContextKeyName = "GestioneMovimentiHttpDataContext";
        }

        private readonly IMovimentiBackofficeService _movimentiBOService;
        private readonly IContextCache _cache;
        private readonly IIdMovimentoResolver _idMovimentoResolver;
        private readonly ILog _log = LogManager.GetLogger(typeof(GestioneMovimentiHttpDataContext));

        public GestioneMovimentiHttpDataContext(IIdMovimentoResolver idMovimentoResolver, IMovimentiBackofficeService movimentiBOService, IContextCache cache)
        {
            this._idMovimentoResolver = idMovimentoResolver;
            this._movimentiBOService = movimentiBOService;
            this._cache = cache;
        }

        #region IGestioneMovimentiDataContext Members

        public GestioneMovimentiDataStore GetDataStore()
        {
            // Se il datastore non esiste tra gli items dell'httpcontext attuale provo a caricarlo dal web service 
            // di gestione movimenti

            return this._cache.GetOrAdd(Constants.HttpContextKeyName, () =>
            {
                this._log.DebugFormat("Il datastore per l'id movimento {0} non è stato trovato nel contesto http e verrà letto tramite il ws", this._idMovimentoResolver.IdMovimento);

                var dataStore = this._movimentiBOService.GetDataStore(this._idMovimentoResolver.IdMovimento);

                return dataStore == null ? new GestioneMovimentiDataStore() : dataStore;
            });
        }


        #endregion

        #region IUnitOfWork<GestioneMovimentiDataStore> Members

        public void Begin()
        {
            this._log.Debug("Begin della unit of work");

            var dataStore = this.GetDataStore();
        }

        public void Commit()
        {
            this._log.Debug("Commit della unit of work");

            this._movimentiBOService.Save(this._idMovimentoResolver.IdMovimento, this.GetDataStore());

            this._cache.Set<GestioneMovimentiDataStore>(Constants.HttpContextKeyName, null);
        }

        #endregion
    }
}
