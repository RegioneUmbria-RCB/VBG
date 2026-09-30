using Init.Sigepro.FrontEnd.AppLogic.Configurazione;
using Init.Sigepro.FrontEnd.Infrastructure.Server;
using log4net;
using System;
using System.IO;

namespace Init.Sigepro.FrontEnd.AppLogic.PresentazioneIstanze.Workflow.Loaders
{
    internal class WorkflowFromWebConfigReader : IWorkflowReader
    {
        private readonly IPathMapper _pathMapper;
        private readonly IAppConfigurationReader _appConfigurationReader;
        private readonly ILog _log = LogManager.GetLogger(typeof(WorkflowFromWebConfigReader));

        public WorkflowFromWebConfigReader(IPathMapper pathMapper, IAppConfigurationReader appConfigurationReader)
        {
            this._pathMapper = pathMapper;
            this._appConfigurationReader = appConfigurationReader;
        }
        public Stream? OpenReadStream()
        {
            var configSetting = this._appConfigurationReader.GetSetting("WorkflowDomanda.overrideWith");

            if (String.IsNullOrEmpty(configSetting))
            {
                return null;
            }

            var absolutePath = this._pathMapper.MapPath(configSetting);

            this._log.Debug($"Caricamento del file di workflow dal parametro WorkflowDomanda.overrideWith del web.config. Percorso: {configSetting} ({absolutePath})");

            if (!File.Exists(absolutePath))
            {
                throw new FileNotFoundException($"Il file di workflow {absolutePath} non esiste. Verificare la configurazione.", absolutePath);
            }

            return File.OpenRead(absolutePath);
        }
    }
}
