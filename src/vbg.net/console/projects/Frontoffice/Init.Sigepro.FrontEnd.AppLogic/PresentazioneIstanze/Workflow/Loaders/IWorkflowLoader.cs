using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using log4net;
using Init.Sigepro.FrontEnd.AppLogic.Repositories.Interfaces;
using Init.Sigepro.FrontEnd.AppLogic.Common;
using Init.Utils;
using System.Xml.Serialization;
using System.Web;
using System.IO;
using Init.Sigepro.FrontEnd.AppLogic.Configurazione.WebConfig;
using Init.Sigepro.FrontEnd.AppLogic.GestioneOggetti;
using System.Configuration;

namespace Init.Sigepro.FrontEnd.AppLogic.PresentazioneIstanze.Workflow.Loaders
{
	internal interface ILoadWorkflow
	{
		StepCollectionType Load();
	}

	internal abstract class LoadWorkflowBase
	{
		protected static void CorreggiDatiStep(StepCollectionType ret)
		{
			foreach (var step in ret.Step)
			{
				if (step.Control.ToUpper() == "~/RESERVED/INSERIMENTOISTANZA/GESTIONEENDO.ASPX")
					step.Control = "~/Reserved/InserimentoIstanza/GestioneEndoV2.aspx";
			}
		}

	}

	internal class LoadWorkflowFromCodiceOggetto : LoadWorkflowBase, ILoadWorkflow
	{
		ILog _log = LogManager.GetLogger(typeof(LoadWorkflowFromCodiceOggetto));
		int? _codiceOggetto;
		IOggettiService _oggettiService;
		IAliasSoftwareResolver _aliasResolver;

		public LoadWorkflowFromCodiceOggetto(int? codiceOggetto,IOggettiService oggettiService, IAliasSoftwareResolver aliasResolver)
		{
			this._codiceOggetto = codiceOggetto;
			this._oggettiService = oggettiService;
			this._aliasResolver = aliasResolver;
		}

		#region ILoadWorkflow Members

		public StepCollectionType Load()
		{
			if (!this._codiceOggetto.HasValue)
				return null;

			try
			{
				var codiceOggetto = this._codiceOggetto.Value;

				_log.DebugFormat("Process Workflow trovato nella tabella FO_ARCONFIGURAZIONE, codiceoggetto: ", codiceOggetto);

				// La configurazione del workflow esiste nel db
				// Leggo il file dal db e lo deserializzo
				// HACK: FIXME: rendere il caricamento degli steps non statico
				var obj = _oggettiService.GetById(codiceOggetto);

				using (var ms = StreamUtils.FileToStream(obj.FileContent))
				{
					_log.Debug("Deserializzazione del Process Workflow");

					var xs = new XmlSerializer(typeof(StepCollectionType));
					var ret = (StepCollectionType)xs.Deserialize(ms);

					CorreggiDatiStep(ret);

					return ret;
				}
			}
			catch (Exception ex)
			{
				_log.ErrorFormat("Errore durante il caricamento del file di workflow dal database oggetti: {0}", ex.ToString());

				throw;
			}
		}

		#endregion
	}

	internal class LoadWorkflowFromFilesystem: LoadWorkflowBase, ILoadWorkflow
	{
		ILog _log = LogManager.GetLogger(typeof(LoadWorkflowFromFilesystem));
		string _workflowFilePath;


		public LoadWorkflowFromFilesystem(string workflowFilePath)
		{
			this._workflowFilePath = workflowFilePath;
		}

		#region ILoadWorkflow Members

		public StepCollectionType Load()
		{
			try
			{
				var processFileName = this._workflowFilePath;

				_log.DebugFormat("Lettura del Process Workflow dal percorso {0}", processFileName);

				using (var fs = File.OpenRead(processFileName))
				{
					XmlSerializer xs = new XmlSerializer(typeof(StepCollectionType));
					var ret = (StepCollectionType)xs.Deserialize(fs);

					CorreggiDatiStep(ret);

					return ret;
				}
			}
			catch (Exception ex)
			{
				_log.ErrorFormat("Errore durante il caricamento del file di workflow da filesystem: {0}", ex.ToString());

				throw;
			}
		}

		#endregion
	}

	internal class LoadWorkflowFromWebConfig : LoadWorkflowBase, ILoadWorkflow
	{
		public LoadWorkflowFromWebConfig()
		{
		}

		public StepCollectionType Load()
		{
			var configSetting = ConfigurationManager.AppSettings["WorkflowDomanda.overrideWith"];
			if (!String.IsNullOrEmpty(configSetting))
			{
				configSetting = HttpContext.Current.Server.MapPath(configSetting);

				return new LoadWorkflowFromFilesystem(configSetting).Load();
			}

			return null;
		}
	}

	internal class CompositeLoadWorkflow : ILoadWorkflow
	{
		IEnumerable<ILoadWorkflow> _loaders;

		public CompositeLoadWorkflow(IEnumerable<ILoadWorkflow> loaders)
		{
			this._loaders = loaders;
		}

		#region ILoadWorkflow Members

		public StepCollectionType Load()
		{
			foreach (var loader in this._loaders)
			{
				var wf = loader.Load();

				if (wf != null)
					return wf;
			}

			return null;
		}

		#endregion
	}


}
