using Init.Sigepro.FrontEnd.AppLogic.Autenticazione.Vbg.AutenticazioneUtente;
using Init.Sigepro.FrontEnd.AppLogic.Common;
using Init.Sigepro.FrontEnd.AppLogic.Configurazione.ServiceReference;
using Init.Sigepro.FrontEnd.AppLogic.GestioneOggetti;
using Init.Sigepro.FrontEnd.AppLogic.PresentazioneIstanze.Workflow;
using Init.Sigepro.FrontEnd.AppLogic.PresentazioneIstanze.Workflow.Loaders;
using Init.Sigepro.FrontEnd.Infrastructure.Server;
using log4net;
using System;

namespace Init.Sigepro.FrontEnd.AppLogic.Configurazione.V2.Builders
{
    internal class ParametriWorkflowBuilder : AreaRiservataWsConfigBuilder, IConfigurazioneBuilder<ParametriWorkflow>
    {
        private static class Constants
        {
            public const string NomeComuneDefault = "default";
        }

        private readonly ILog _log = LogManager.GetLogger(typeof(ParametriWorkflowBuilder));
        private readonly IAliasSoftwareResolver _aliasResolver;
        private readonly IOggettiService _oggettiService;
        private readonly IPathMapper _pathMapper;
        private readonly IAppConfigurationReader _appConfigurationReader;
        private readonly IFrameworkToCoreWorkflowUrlMapper _urlMapper;
        private readonly IAuthenticationDataResolver _authenticationDataResolver;

        public ParametriWorkflowBuilder(IAliasSoftwareResolver aliasResolver, IConfigurazioneAreaRiservataRepository configurazioneAreaRiservataRepository,
            IOggettiService oggettiService, IPathMapper pathMapper, IAppConfigurationReader appConfigurationReader, IFrameworkToCoreWorkflowUrlMapper urlMapper, IAuthenticationDataResolver authenticationDataResolver)
            : base(aliasResolver, configurazioneAreaRiservataRepository)
        {
            this._aliasResolver = aliasResolver ?? throw new ArgumentNullException(nameof(aliasResolver));
            this._oggettiService = oggettiService ?? throw new ArgumentNullException(nameof(oggettiService));
            this._pathMapper = pathMapper ?? throw new ArgumentNullException(nameof(pathMapper));
            this._appConfigurationReader = appConfigurationReader ?? throw new ArgumentNullException(nameof(appConfigurationReader));
            this._urlMapper = urlMapper ?? throw new ArgumentNullException(nameof(urlMapper));
            this._authenticationDataResolver = authenticationDataResolver ?? throw new ArgumentNullException(nameof(authenticationDataResolver));
        }


        #region IBuilder<ParametriWorkflow> Members

        public ParametriWorkflow Build()
        {
            try
            {

                var parametriWs = this.GetConfig();

                var workflowReader = new CompositeWorkflowReader(new IWorkflowReader[] {
                    new WorkflowFromCodiceOggettoReader(parametriWs.CodiceOggettoWorkflow , this._oggettiService),
                    new WorkflowFromFilesystemReader( this.GetProcessFileName())
                });

                var workflowLoader = new WorkflowLoader(workflowReader, this._urlMapper, this._authenticationDataResolver);

                var steps = workflowLoader.Load();

                // TODO: cosa fare se gli steps sono null???
                var workflow = new WorkflowDomandaOnline(steps);

                var verificaHash = parametriWs.VerificaHashFilesFirmati;
                var impostaAutomaticamenteAnagraficaUtenteCorrente = parametriWs.ImpostaAutomaticamenteRichiedente;

                return new ParametriWorkflow(workflow, verificaHash, parametriWs.IdCampoDinamicoAttivitaAtecoPrevalente, impostaAutomaticamenteAnagraficaUtenteCorrente, parametriWs.CodiceOggettoWorkflow);
            }
            catch (Exception ex)
            {
                this._log.ErrorFormat("Errore durante il caricamento dei parametri del workflow: {0}", ex.ToString());

                throw;
            }
        }

        #endregion

        public string GetProcessFileName()
        {
            var config = this._appConfigurationReader.GetParametriPerComune();
            var processFile = config.ProcessFile;

            if (!String.IsNullOrEmpty(processFile))
                return this._pathMapper.MapPath(processFile);

            return string.Empty;
        }

        //private string GetProcessFileNameFromConfiguration()
        //{
        //    var cfg = this.GetParametriLocalizzati();

        //    if (String.IsNullOrEmpty(cfg.ProcessFile.Default))
        //        cfg = this._appConfigurationReader.GetParametriLocalizzatiDefault();

        //    var spec = cfg.ProcessFile.Specializzazioni[this._aliasResolver.Software];

        //    if (spec == null)
        //        return cfg.ProcessFile.Default;

        //    var processFile = spec.File;

        //    if (String.IsNullOrEmpty(processFile))
        //        return cfg.ProcessFile.Default;

        //    return processFile;
        //}
    }
}
