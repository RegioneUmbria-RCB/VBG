using Init.Sigepro.FrontEnd.AppLogic.Autenticazione.Vbg.AutenticazioneUtente;
using Init.Sigepro.FrontEnd.AppLogic.Configurazione.V2;
using Init.Sigepro.FrontEnd.AppLogic.GestioneInterventi;
using Init.Sigepro.FrontEnd.AppLogic.GestioneOggetti;
using Init.Sigepro.FrontEnd.AppLogic.PresentazioneIstanze.Workflow.Loaders;
using Init.Sigepro.FrontEnd.AppLogic.PresentazioneIstanze.Workflow.MergepointFinder;
using System.Configuration;

namespace Init.Sigepro.FrontEnd.AppLogic.PresentazioneIstanze.Workflow
{
    public class WorkflowLoaderService : IWorkflowLoaderService
    {
        private readonly IConfigurazione<ParametriWorkflow> _configurazioneWorkflow;
        private readonly IOggettiService _oggettiService;
        private readonly IAuthenticationDataResolver _authenticationDataResolver;
        private readonly IFrameworkToCoreWorkflowUrlMapper _urlMapper;
        private readonly IInterventiRepository _interventiRepository;
        private readonly IMergepointFinder _mergepointFinder;

        public WorkflowLoaderService(IConfigurazione<ParametriWorkflow> configurazioneWorkflow, IOggettiService oggettiService, IAuthenticationDataResolver authenticationDataResolver,
            IFrameworkToCoreWorkflowUrlMapper urlMapper, IInterventiRepository interventiRepository, IMergepointFinder mergepointFinder)
        {
            this._configurazioneWorkflow = configurazioneWorkflow;
            this._oggettiService = oggettiService;
            this._authenticationDataResolver = authenticationDataResolver;
            this._urlMapper = urlMapper;
            this._interventiRepository = interventiRepository;
            this._mergepointFinder = mergepointFinder;
        }

        public IWorkflowDomandaOnline GetMergedWorkflowByIdIntervento(int? idIntervento, bool useCachedDefaultWorkflow = false)
        {
            var workflowBase = useCachedDefaultWorkflow ? this._configurazioneWorkflow.Parametri.DefaultWorkflow : this.GetBaseWorkflow();

            if (workflowBase == null)
            {
                throw new ConfigurationErrorsException("Il workflow di default non è stato configurato correttamente. Verificare la configurazione.");
            }

            var wfIntervento = this.GetWorkflowIntervento(idIntervento);

            if (wfIntervento == null)
            {
                return workflowBase;
            }

            return workflowBase.MergeWith(wfIntervento, this._mergepointFinder);
        }

        public IWorkflowDomandaOnline? GetBaseWorkflow()
        {
            if (this._configurazioneWorkflow.Parametri.DefaultWorkflowCodiceOggetto == null)
                return null;

            var reader = new WorkflowFromCodiceOggettoReader(this._configurazioneWorkflow.Parametri.DefaultWorkflowCodiceOggetto, this._oggettiService);
            var loader = new WorkflowLoader(reader, this._urlMapper, this._authenticationDataResolver);

            return new WorkflowDomandaOnline(loader.Load());
        }

        public IWorkflowDomandaOnline? GetWorkflowIntervento(int? idIntervento)
        {
            if (idIntervento == null)
            {
                return null;
            }

            var codiceOggetto = this._interventiRepository.GetCodiceOggettoWorkflow(idIntervento.Value);

            if (codiceOggetto == null)
            {
                return null;
            }

            var reader = new WorkflowFromCodiceOggettoReader(codiceOggetto, this._oggettiService);
            var loader = new WorkflowLoader(reader, this._urlMapper, this._authenticationDataResolver);

            var steps = loader.Load();

            return steps == null ? null : new WorkflowDomandaOnline(steps);
        }

        public NodoConWorkflowXml GetNodoInterventoWorkflowXml(int codiceIntervento)
        {
            var nodo = this._interventiRepository.GetNodoConWorkflowDaIdIntervento(codiceIntervento);

            if (nodo?.CodiceOggettoWorkflow == null)
                return new NodoConWorkflowXml();

            var wfFile = this._oggettiService.GetById(nodo.CodiceOggettoWorkflow);

            if (wfFile == null)
                return new NodoConWorkflowXml();

            var wfXml = System.Text.Encoding.UTF8.GetString(wfFile.FileContent);

            return new NodoConWorkflowXml()
            {
                Descrizione = nodo.Descrizione,
                Id = nodo.Id,
                XmlWorkflow = wfXml
            };
        }
    }
}
