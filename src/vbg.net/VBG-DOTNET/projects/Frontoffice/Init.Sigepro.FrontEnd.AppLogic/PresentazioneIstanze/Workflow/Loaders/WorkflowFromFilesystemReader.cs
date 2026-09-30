using log4net;
using System.IO;

namespace Init.Sigepro.FrontEnd.AppLogic.PresentazioneIstanze.Workflow.Loaders
{
    internal class WorkflowFromFilesystemReader : IWorkflowReader
    {
        private readonly string _workflowFilePath;
        private readonly ILog _log = LogManager.GetLogger(typeof(WorkflowFromFilesystemReader));
        public WorkflowFromFilesystemReader(string workflowFilePath)
        {
            this._workflowFilePath = workflowFilePath;
        }
        public Stream? OpenReadStream()
        {
            var processFileName = this._workflowFilePath;

            this._log.DebugFormat("Lettura del Process Workflow dal percorso {0}", processFileName);

            if (!File.Exists(processFileName))
            {
                throw new FileNotFoundException($"Il file di workflow {processFileName} non esiste. Verificare la configurazione.", processFileName);
            }

            return File.OpenRead(processFileName);
        }
    }
}
