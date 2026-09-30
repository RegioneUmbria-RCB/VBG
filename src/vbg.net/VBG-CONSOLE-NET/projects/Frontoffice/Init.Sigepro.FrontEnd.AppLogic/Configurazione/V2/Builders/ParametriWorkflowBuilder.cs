using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.Sigepro.FrontEnd.AppLogic.Configurazione.WebConfig;
using Init.Sigepro.FrontEnd.AppLogic.Common;
using Init.Sigepro.FrontEnd.AppLogic.Repositories.Interfaces;
using Init.Sigepro.FrontEnd.AppLogic.AreaRiservataService;
using log4net;
using Init.Utils;
using System.Xml.Serialization;
using Init.Sigepro.FrontEnd.AppLogic.PresentazioneIstanze.Workflow;
using System.Web;
using System.IO;
using Init.Sigepro.FrontEnd.AppLogic.PresentazioneIstanze.Workflow.Loaders;
using Init.Sigepro.FrontEnd.AppLogic.GestioneOggetti;

namespace Init.Sigepro.FrontEnd.AppLogic.Configurazione.V2.Builders
{
	internal class ParametriWorkflowBuilder: AreaRiservataWebConfigBuilder, IConfigurazioneBuilder<ParametriWorkflow>
	{
		private static class Constants
		{
			public const string NomeComuneDefault = "default";
		}


		ILog _log = LogManager.GetLogger(typeof(ParametriWorkflowBuilder));
		IAliasSoftwareResolver _aliasResolver;
		IConfigurazioneAreaRiservataRepository _configurazioneAreaRiservataRepository;
		IOggettiService _oggettiService;


		public ParametriWorkflowBuilder(IAliasSoftwareResolver aliasResolver, IConfigurazioneAreaRiservataRepository configurazioneAreaRiservataRepository, IOggettiService oggettiService)
		{
			if (aliasResolver == null)
				throw new ArgumentNullException("aliasResolver");

			if (configurazioneAreaRiservataRepository == null)
				throw new ArgumentNullException("configurazioneAreaRiservataRepository");

			if (oggettiService == null)
				throw new ArgumentNullException("oggettiRepository");

			_aliasResolver = aliasResolver;
			_configurazioneAreaRiservataRepository = configurazioneAreaRiservataRepository;
			_oggettiService = oggettiService;
		}


		#region IBuilder<ParametriWorkflow> Members

		public ParametriWorkflow Build()
		{
			try
			{
				var parametriLocalizzati = GetParametriLocalizzati();
				var parametriWs = GetWsConfig();

				var workflowLoader = new CompositeLoadWorkflow(new ILoadWorkflow[] { 
					new LoadWorkflowFromWebConfig(),
					new LoadWorkflowFromCodiceOggetto( parametriWs.CodiceOggettoWorkflow , _oggettiService , _aliasResolver),
					new LoadWorkflowFromFilesystem( GetProcessFileName() )
				});

				var workflow = workflowLoader.Load();

				var paginaIniziale = parametriWs.UrlPaginaIniziale;
				
				if(String.IsNullOrEmpty(paginaIniziale))
					paginaIniziale = parametriLocalizzati.PaginaIniziale;

				var verificaHash = parametriWs.VerificaHashFilesFirmati;
				var impostaAutomaticamenteAnagraficaUtenteCorrente = parametriWs.ImpostaAutomaticamenteRichiedente;

				return new ParametriWorkflow(paginaIniziale, workflow, verificaHash, parametriWs.IdCampoDinamicoAttivitaAtecoPrevalente, impostaAutomaticamenteAnagraficaUtenteCorrente);
			}
			catch (Exception ex)
			{
				_log.ErrorFormat("Errore durante il caricamento dei parametri del workflow: {0}", ex.ToString());

				throw;
			}
		}

		#endregion

		private ConfigurazioneLocalizzata GetParametriLocalizzati()
		{
			string idComune = _aliasResolver.AliasComune;

			var cfg = GetConfigurazione();

			if (cfg.ParametriPerIdComune[idComune] != null)
				return cfg.ParametriPerIdComune[idComune];

			return cfg.ParametriPerIdComune[Constants.NomeComuneDefault];
		}

		public string GetProcessFileName()
		{
			var processFile = GetProcessFileNameFromConfiguration();

			if (!String.IsNullOrEmpty(processFile))
				return HttpContext.Current.Server.MapPath( processFile );

			return string.Empty;
		}

		private string GetProcessFileNameFromConfiguration()
		{
			var cfg = GetParametriLocalizzati();

			if (String.IsNullOrEmpty(cfg.ProcessFile.Default))
				cfg = GetConfigurazione().ParametriPerIdComune[Constants.NomeComuneDefault];

			var spec = cfg.ProcessFile.Specializzazioni[_aliasResolver.Software];

			if (spec == null)
				return cfg.ProcessFile.Default;

			var processFile = spec.File;

			if (String.IsNullOrEmpty(processFile))
				return cfg.ProcessFile.Default;

			return processFile;
		}

		private ConfigurazioneAreaRiservataDto GetWsConfig()
		{
			return this._configurazioneAreaRiservataRepository.DatiConfigurazione(this._aliasResolver.AliasComune, this._aliasResolver.Software);
		}
	}
}
