using Init.Sigepro.FrontEnd.AppLogic.GestioneOggetti;
using log4net;
using System.IO;

namespace Init.Sigepro.FrontEnd.AppLogic.PresentazioneIstanze.Workflow.Loaders
{
    internal class WorkflowFromCodiceOggettoReader : IWorkflowReader
    {
        private readonly int? _codiceOggetto;
        private readonly IOggettiService _oggettiService;
        private readonly ILog _log = LogManager.GetLogger(typeof(WorkflowFromCodiceOggettoReader));

        public WorkflowFromCodiceOggettoReader(int? codiceOggetto, IOggettiService oggettiService)
        {
            this._codiceOggetto = codiceOggetto;
            this._oggettiService = oggettiService;
        }

        public Stream? OpenReadStream()
        {
            this._log.Debug($"CodiceOggetto valorizzato? {this._codiceOggetto.HasValue}");

            if (!this._codiceOggetto.HasValue)
            {
                return null;
            }

            this._log.DebugFormat("Inizio caricamento del process workflow con codice oggetto: ", this._codiceOggetto);

            var obj = this._oggettiService.GetById(this._codiceOggetto.Value);

            if (obj == null)
            {
                return null;
            }

            return new MemoryStream(obj.FileContent);
        }
    }

}
