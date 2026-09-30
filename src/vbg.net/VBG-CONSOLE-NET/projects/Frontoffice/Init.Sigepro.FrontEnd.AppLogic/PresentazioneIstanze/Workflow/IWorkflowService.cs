using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.Sigepro.FrontEnd.AppLogic.Configurazione.V2;
using CuttingEdge.Conditions;
using Init.Sigepro.FrontEnd.AppLogic.ReadInterface;
using System.Web;
using Init.Sigepro.FrontEnd.AppLogic.Repositories.Interfaces;
using Init.Sigepro.FrontEnd.AppLogic.PresentazioneIstanze.Workflow.Loaders;
using Init.Sigepro.FrontEnd.AppLogic.Common;
using log4net;
using Init.Sigepro.FrontEnd.AppLogic.GestioneOggetti;
using Init.Sigepro.FrontEnd.AppLogic.GestioneBookmarks;
using System.Configuration;
using Init.Sigepro.FrontEnd.AppLogic.GestioneInterventi;
using Init.Sigepro.FrontEnd.AppLogic.BookmarksWebService;
using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda;

namespace Init.Sigepro.FrontEnd.AppLogic.PresentazioneIstanze.Workflow
{

	/// <summary>
	/// Espone le funzionalità per accedere al workflow della domanda corrente
	/// </summary>
	public interface IWorkflowService
	{
		/// <summary>
		/// Ottiene un riferimento al workflow corrente
		/// </summary>
		/// <returns></returns>
		IWorkflowDomandaOnline GetCurrentWorkflow();

		/// <summary>
		/// Resetta il workflow corrente. 
		/// Il workflow dovrà essere ricaricato in base alle logiche dell'aplicazione
		/// </summary>
		void ResetWorkflow();

        /// <summary>
        /// Resetta il workflow corrente e ne carica un altro sulla base del bookmark selezionato
        /// </summary>
        /// <param name="datiBookmark"></param>
        void ResetWorkflowDaBookmark(BookmarkInterventoDto datiBookmark);
    }


	/// <summary>
	/// Il workflow corrente viene letto dai parametri di configurazione dell'area riservata
	/// </summary>
	public class WorkflowFromConfigurazioneService : IWorkflowService
	{
		IConfigurazione<ParametriWorkflow> _configurazioneWorkflow;

		public WorkflowFromConfigurazioneService(IConfigurazione<ParametriWorkflow> configurazioneWorkflow)
		{
			Condition.Requires(configurazioneWorkflow, "configurazioneWorkflow").IsNotNull();

			this._configurazioneWorkflow = configurazioneWorkflow;
		}


		#region IWorkflowService Members

		public IWorkflowDomandaOnline GetCurrentWorkflow()
		{
			return this._configurazioneWorkflow.Parametri.DefaultWorkflow;
		}


		public void ResetWorkflow()
		{
			// non fa niente
		}

		#endregion


        public void ResetWorkflowDaBookmark(BookmarkInterventoDto datiBookmark)
        {
            ResetWorkflow();
        }
    }


	public class WorkflowFromCodiceIntervento : IWorkflowService
	{
		private static class Constants
		{
			public const string WorkflowSessionKey  = "WorkflowDomandaSessionKey";
			public const string IdDomandaSessionKey = "IdDomandaWorkflowSessionKey";
		}

		ILog _log = LogManager.GetLogger(typeof(WorkflowFromCodiceIntervento));

        IReadDatiDomanda            _readDatiDomanda;
		IInterventiRepository		_interventiRepository;
		IOggettiService			    _oggettiService;
		IAliasSoftwareResolver		_aliasResolver;
        IConfigurazione<ParametriWorkflow> _configurazioneWorkflow;

        IWorkflowDomandaOnline GetDefaultWorkflow()
        {
            return this._configurazioneWorkflow.Parametri.DefaultWorkflow;
        }

        ISearchWorkflowMergePoint GetWorkflowMergePoint()
        {
            return new SearchWorkflowMergePointDaWebConfig(this.GetDefaultWorkflow());
        }



        public WorkflowFromCodiceIntervento(IConfigurazione<ParametriWorkflow> configurazioneWorkflow, IReadDatiDomanda readFacade,
											IInterventiRepository interventiRepository, IOggettiService oggettiService,
                                            IAliasSoftwareResolver aliasResolver)
		{
			Condition.Requires(configurazioneWorkflow, "configurazioneWorkflow").IsNotNull();
			Condition.Requires(readFacade, "readFacade").IsNotNull();
			Condition.Requires(interventiRepository, "interventiRepository").IsNotNull();
			Condition.Requires(oggettiService, "oggettiService").IsNotNull();
			Condition.Requires(aliasResolver, "aliasResolver").IsNotNull();

            this._configurazioneWorkflow    = configurazioneWorkflow;
			this._readDatiDomanda				= readFacade;
			this._interventiRepository		= interventiRepository;
			//this._searchWorkflowMergePoint	= new SearchWorkflowMergePointDaWebConfig(this._defaultWorkflow);
			this._oggettiService			= oggettiService;
			this._aliasResolver				= aliasResolver;

		}

        public void ResetReadDatiDomanda(IReadDatiDomanda readDatiDomanda)
        {
            this._readDatiDomanda = readDatiDomanda;
        }

        private string GetIdDomandaInUso()
        {
            var idcomune = this._readDatiDomanda.DomandaDataKey.IdComune;
            var idDomanda = this._readDatiDomanda.DomandaDataKey.IdPresentazione;

            return String.Format("{0}_{1}", idcomune, idDomanda);
        }

		private IWorkflowDomandaOnline CurrentWorkflow
		{
			get
			{
				var idDomandaAttuale = HttpContext.Current.Session[Constants.IdDomandaSessionKey];

				if (idDomandaAttuale == null)
					return null;

				if (!idDomandaAttuale.Equals(GetIdDomandaInUso()))
					return null;

				var wfObj = HttpContext.Current.Session[Constants.WorkflowSessionKey];

				if (wfObj == null)
					return null;

				return (IWorkflowDomandaOnline)wfObj;
			}
			set
			{
                HttpContext.Current.Session[Constants.IdDomandaSessionKey] = GetIdDomandaInUso();
				HttpContext.Current.Session[Constants.WorkflowSessionKey] = value;
			}
		}

		#region IWorkflowService Members

        private StepCollectionType GetStepsWorkflowDaCodiceIntervento(int codiceIntervento)
        {
            // Se la domanda ha un intervento il file di workflow potrebbe essere l'unione degli
            // steps di default con gli steps definiti dall'intervento
            var codiceOggettoWorkflow = _interventiRepository.GetCodiceOggettoWorkflow(codiceIntervento);

            // Se l'albero non definisce un codice oggetto workflow allora posso utilizzare 
            // il workflow di default
            if (!codiceOggettoWorkflow.HasValue)
            {
                return null;
            }

            var workflowLoader = new LoadWorkflowFromCodiceOggetto(codiceOggettoWorkflow.Value, this._oggettiService, this._aliasResolver);
            var stepsWorkflow = workflowLoader.Load();

            if (stepsWorkflow == null)
            {
                throw new Exception("Non è stato possibile caricare il file di workflow corrispondente al codice oggetto " + codiceOggettoWorkflow);
            }

            return stepsWorkflow;
        }

		public IWorkflowDomandaOnline GetCurrentWorkflow()
		{
			// Non esiste un workflow per la domanda, devo ricrearlo oppure 
			// utilizzare il wf di default
			if (CurrentWorkflow == null)
			{
                var bookmark = _readDatiDomanda.Domanda.Bookmarks.Bookmark;

                if (!String.IsNullOrEmpty(bookmark))
                {
                    _log.DebugFormat("La domanda corrente proviene da un bookmark ({0}) ripristino il workflow dal bookmark", bookmark);

                    SostituisciWorkflowCorrenteDaIdIntervento(bookmark, _readDatiDomanda.Domanda.AltriDati.Intervento.Codice);

                    return this.CurrentWorkflow;
                }


                _log.DebugFormat("Non è stato trovato un file di workflow in cache per la domanda con id {0}", GetIdDomandaInUso());
								
				var defaultWorkflow = this.GetDefaultWorkflow();

				if (_readDatiDomanda.Domanda.AltriDati.Intervento == null )
				{
					_log.Debug("La domanda non ha un intervento definito, verrà utilizzato il workflow di default");

					return defaultWorkflow;
				}

				var idIntervento = _readDatiDomanda.Domanda.AltriDati.Intervento.Codice;
                var stepsWorkflow = GetStepsWorkflowDaCodiceIntervento(idIntervento);

                if (stepsWorkflow == null)
                {
                    _log.DebugFormat("L'intervento della domanda ({0}) non è associato ad un codice oggetto di workflow, verrà utilizzato il workflow di default", idIntervento);

                    CurrentWorkflow = defaultWorkflow;

                    return CurrentWorkflow;
                }

				// Nell'albero è definito un nuovo file di workflow, carico i nuovi steps e li unisco a quelli precedenti
				// allo step di selezione interventi
				var mergePoint		= this.GetWorkflowMergePoint().GetMergePoint();

                var nuovoWorkflow = defaultWorkflow.MergeWith(stepsWorkflow, mergePoint);

				this.CurrentWorkflow = nuovoWorkflow;
			}

			return this.CurrentWorkflow;
		}

		public void ResetWorkflow()
		{
			HttpContext.Current.Session[Constants.IdDomandaSessionKey] = null;
			HttpContext.Current.Session[Constants.WorkflowSessionKey] = null;
		}


        public void ResetWorkflowDaBookmark(BookmarkInterventoDto datiBookmark)
        {
            SostituisciWorkflowCorrenteDaIdIntervento(datiBookmark.Url, datiBookmark.IdIntervento);
        }

        #endregion

        private void SostituisciWorkflowCorrenteDaIdIntervento(string nomeBookmark, int idIntervento)
        {
            var steps = GetStepsWorkflowDaCodiceIntervento(idIntervento);

            if (steps == null)
            {
                throw new ConfigurationException(String.Format("L'intervento associato al bookmark {0} ({1}) non possiede un workflow", nomeBookmark, idIntervento));
            }

            this.CurrentWorkflow = new WorkflowDomandaOnline(steps);
        }
    }

	public class NullWorkflowService : IWorkflowService
	{
		#region IWorkflowService Members

		public IWorkflowDomandaOnline GetCurrentWorkflow()
		{
			return new WorkflowDomandaOnline(new StepCollectionType());
		}

		public void ResetWorkflow()
		{
		}

        public void ResetWorkflowDaBookmark(BookmarkInterventoDto datiBookmark)
        {

        }

        #endregion
    }

}
