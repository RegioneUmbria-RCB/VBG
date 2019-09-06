// -----------------------------------------------------------------------
// <copyright file="GestioneMovimentiHttpDataContext.cs" company="">
// TODO: Update copyright text.
// </copyright>
// -----------------------------------------------------------------------

namespace Init.Sigepro.FrontEnd.GestioneMovimenti.Persistence
{
    using System;
    using System.Collections.Generic;
    using System.Linq;
    using System.Text;
    using System.Web;
    using Init.Sigepro.FrontEnd.GestioneMovimenti.ExternalServices;
    using Init.Sigepro.FrontEnd.Infrastructure.Repositories;
    using log4net;

    public interface IIdMovimentoResolver
    {
        int IdMovimento { get; }
    }

    public class IdMovimentoQuerystringResolver : IIdMovimentoResolver
    {
        private static class Constants
        {
            public const string QuerystringParameter = "idMovimento";
        }

        public int IdMovimento
        {
            get
            {
                var idMovimento = HttpContext.Current.Request.QueryString[Constants.QuerystringParameter];

                if (String.IsNullOrEmpty(idMovimento))
                    throw new ArgumentException("Identificativo movimento non passato");

                return Convert.ToInt32(idMovimento);
            }
        }
    }

    /// <summary>
    /// TODO: Update summary.
    /// </summary>
    public class GestioneMovimentiHttpDataContext : IGestioneMovimentiDataContext, IUnitOfWork<GestioneMovimentiDataStore>
    {
        private static class Constants
        {
            public const string HttpContextKeyName = "GestioneMovimentiHttpDataContext";
        }

        IMovimentiBackofficeService _movimentiBOService;
        IIdMovimentoResolver _idMovimentoResolver;
        ILog _log = LogManager.GetLogger(typeof(GestioneMovimentiHttpDataContext));

        public GestioneMovimentiHttpDataContext(IIdMovimentoResolver idMovimentoResolver, IMovimentiBackofficeService movimentiBOService)
        {
            if (HttpContext.Current == null)
                throw new Exception("Non esiste un HttpContext o il contesto passato nonè valido. Potrebbe essere possibile che il componente di gestione movimenti sia usato in un'applicazione non web based");

            this._idMovimentoResolver = idMovimentoResolver;
            this._movimentiBOService = movimentiBOService;

        }

        #region IGestioneMovimentiDataContext Members

        public GestioneMovimentiDataStore GetDataStore()
        {
            // Se il datastore non esiste tra gli items dell'httpcontext attuale provo a caricarlo dal web service 
            // di gestione movimenti
            if (HttpContext.Current.Items[Constants.HttpContextKeyName] == null)
            {
                _log.DebugFormat("Il datastore per l'id movimento {0} non è stato trovato nel contesto http e verrà letto tramite il ws", this._idMovimentoResolver.IdMovimento);

                var dataStore = this._movimentiBOService.GetDataStore(this._idMovimentoResolver.IdMovimento);

                HttpContext.Current.Items[Constants.HttpContextKeyName] = dataStore == null ? new GestioneMovimentiDataStore() : dataStore;
            }

            return (GestioneMovimentiDataStore)HttpContext.Current.Items[Constants.HttpContextKeyName];
        }


        #endregion

        #region IUnitOfWork<GestioneMovimentiDataStore> Members

        public void Begin()
        {
            _log.Debug("Begin della unit of work");

            var dataStore = GetDataStore();
        }

        public void Commit()
        {
            _log.Debug("Commit della unit of work");

            this._movimentiBOService.Save(this._idMovimentoResolver.IdMovimento, GetDataStore());
        }

        #endregion
    }
}
