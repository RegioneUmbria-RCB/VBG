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
	}


	public class WorkflowFromCodiceIntervento : IWorkflowService
	{
		private static class Constants
		{
			public const string WorkflowSessionKey  = "WorkflowDomandaSessionKey";
			public const string IdDomandaSessionKey = "IdDomandaWorkflowSessionKey";
		}

		ILog _log = LogManager.GetLogger(typeof(WorkflowFromCodiceIntervento));

		IWorkflowDomandaOnline		_defaultWorkflow;
		IReadFacade					_readFacade;
		IInterventiRepository		_interventiRepository;
		ISearchWorkflowMergePoint	_searchWorkflowMergePoint;
		IOggettiService			_oggettiService;
		IAliasSoftwareResolver		_aliasResolver;
		
		string	_idDomandainUso;

		public WorkflowFromCodiceIntervento(IConfigurazione<ParametriWorkflow> configurazioneWorkflow, IReadFacade readFacade,
											IInterventiRepository interventiRepository, IOggettiService oggettiService, 
											IAliasSoftwareResolver aliasResolver)
		{
			Condition.Requires(configurazioneWorkflow, "configurazioneWorkflow").IsNotNull();
			Condition.Requires(readFacade, "readFacade").IsNotNull();
			Condition.Requires(interventiRepository, "interventiRepository").IsNotNull();
			Condition.Requires(oggettiService, "oggettiService").IsNotNull();
			Condition.Requires(aliasResolver, "aliasResolver").IsNotNull();

			this._defaultWorkflow			= configurazioneWorkflow.Parametri.DefaultWorkflow;
			this._readFacade				= readFacade;
			this._interventiRepository		= interventiRepository;
			this._searchWorkflowMergePoint	= new SearchWorkflowMergePointDaWebConfig(this._defaultWorkflow);
			this._oggettiService			= oggettiService;
			this._aliasResolver				= aliasResolver;

			// Ricavo l'id della domanda in uso (idcomune_iddomanda)
			var idcomune  = _readFacade.DomandaDataKey.IdComune;
			var idDomanda = _readFacade.DomandaDataKey.IdPresentazione;

			this._idDomandainUso = String.Format("{0}_{1}", idcomune, idDomanda);
		}

		private IWorkflowDomandaOnline CurrentWorkflow
		{
			get
			{
				var idDomandaAttuale = HttpContext.Current.Session[Constants.IdDomandaSessionKey];

				if (idDomandaAttuale == null)
					return null;

				if (!idDomandaAttuale.Equals(this._idDomandainUso))
					return null;

				var wfObj = HttpContext.Current.Session[Constants.WorkflowSessionKey];

				if (wfObj == null)
					return null;

				return (IWorkflowDomandaOnline)wfObj;
			}
			set
			{
				HttpContext.Current.Session[Constants.IdDomandaSessionKey] = this._idDomandainUso;
				HttpContext.Current.Session[Constants.WorkflowSessionKey] = value;
			}
		}

		#region IWorkflowService Members

		public IWorkflowDomandaOnline GetCurrentWorkflow()
		{
			// Non esiste un workflow per la domanda, devo ricrearlo oppure 
			// utilizzare il wf di default
			if (CurrentWorkflow == null)
			{
				_log.DebugFormat("Non è stato trovato un file di workflow in cache per la domanda con id {0}", this._idDomandainUso);
								
				var defaultWorkflow = _defaultWorkflow;

				if (_readFacade.Domanda.AltriDati.Intervento == null )
				{
					_log.Debug("La domanda non ha un intervento definito, verrà utilizzato il workflow di default");

					return defaultWorkflow;
				}

				var idIntervento = _readFacade.Domanda.AltriDati.Intervento.Codice;

				// Se la domanda ha un intervento il file di workflow potrebbe essere l'unione degli
				// steps di default con gli steps definiti dall'intervento
				var codiceOggettoWorkflow = _interventiRepository.GetCodiceOggettoWorkflow(idIntervento);

				// Se l'albero non definisce un codice oggetto workflow allora posso utilizzare 
				// il workflow di default
				if (!codiceOggettoWorkflow.HasValue)
				{
					_log.DebugFormat("L'intervento della domanda ({0}) non è associato ad un codice oggetto di workflow, verrà utilizzato il workflow di default", idIntervento);

					CurrentWorkflow = defaultWorkflow;

					return CurrentWorkflow;
				}

				// Nell'albero è definito un nuovo file di workflow, carico i nuovi steps e li unisco a quelli precedenti
				// allo step di selezione interventi
				_log.DebugFormat("E'stato trovato un nuovo file di workflow: codiceoggetto={0},  ", codiceOggettoWorkflow );				
				var workflowLoader	= new LoadWorkflowFromCodiceOggetto( codiceOggettoWorkflow.Value , this._oggettiService , this._aliasResolver );
				var mergePoint		= this._searchWorkflowMergePoint.GetMergePoint();

				var nuoviSteps		= workflowLoader.Load();

				if (nuoviSteps == null)
				{
					_log.ErrorFormat("Dal nuovo file di workflow non è stato possibile caricare nessuno step: codiceoggetto={0},  ", codiceOggettoWorkflow);				

					throw new Exception("Non è stato possibile caricare il file di workflow corrispondente al codice oggetto " + codiceOggettoWorkflow);
				}

				var nuovoWorkflow	= defaultWorkflow.MergeWith(nuoviSteps, mergePoint);

				this.CurrentWorkflow = nuovoWorkflow;
			}

			return this.CurrentWorkflow;
		}

		public void ResetWorkflow()
		{
			HttpContext.Current.Session[Constants.IdDomandaSessionKey] = null;
			HttpContext.Current.Session[Constants.WorkflowSessionKey] = null;
		}

		#endregion
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

		#endregion
	}

}
