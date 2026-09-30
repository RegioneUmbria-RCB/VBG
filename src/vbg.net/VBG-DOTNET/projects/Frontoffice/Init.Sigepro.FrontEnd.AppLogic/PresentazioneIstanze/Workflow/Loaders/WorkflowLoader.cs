using Init.Sigepro.FrontEnd.AppLogic.Autenticazione.Vbg.AutenticazioneUtente;
using log4net;
using System;
using System.IO;
using System.Text;
using System.Xml.Serialization;

namespace Init.Sigepro.FrontEnd.AppLogic.PresentazioneIstanze.Workflow.Loaders
{
    internal class WorkflowLoader
    {
        protected readonly ILog _log;
        private readonly IWorkflowReader _reader;
        private readonly IFrameworkToCoreWorkflowUrlMapper _frameworkToCoreWorkflowUrlMapper;
        private readonly IAuthenticationDataResolver _authenticationDataResolver;

        public WorkflowLoader(IWorkflowReader reader, IFrameworkToCoreWorkflowUrlMapper frameworkToCoreWorkflowUrlMapper, IAuthenticationDataResolver authenticationDataResolver)
        {
            this._log = LogManager.GetLogger(this.GetType());
            this._reader = reader;
            this._frameworkToCoreWorkflowUrlMapper = frameworkToCoreWorkflowUrlMapper;
            this._authenticationDataResolver = authenticationDataResolver;
        }

        private void CorreggiDatiStep(StepCollectionType ret)
        {
            if (!this._authenticationDataResolver.DatiAutenticazione.DatiUtente.UtenteTester)
            {
                ret.RimuoviStepsDisabilitati();
            }

            if (ret.Steps is null)
            {
                ret.Steps = Array.Empty<StepType>();
            }

            foreach (var step in ret.Steps)
            {
                if (step.Control.ToUpper() == "~/RESERVED/INSERIMENTOISTANZA/GESTIONEENDO.ASPX")
                    step.Control = "~/Reserved/InserimentoIstanza/GestioneEndoV2.aspx";

                this._frameworkToCoreWorkflowUrlMapper.AssicuraEsistenzaStepId(step);
            }

            // ret.RemapStepUrls(this._frameworkToCoreWorkflowUrlMapper);
        }

        public StepCollectionType? Load()
        {
            try
            {
                using (var ms = this._reader.OpenReadStream())
                {
                    if (ms == null)
                    {
                        this._log.Debug("OpenReadStream null");
                        return null;
                    }

                    this._log.Debug("Deserializzazione del Process Workflow");

                    System.Text.EncodingProvider ppp = System.Text.CodePagesEncodingProvider.Instance;
                    Encoding.RegisterProvider(ppp);

                    ms.Position = 0;
                    var xs = new XmlSerializer(typeof(StepCollectionType));
                    var ret = (StepCollectionType)xs.Deserialize(ms);

                    this.CorreggiDatiStep(ret);

                    return ret;
                }
            }
            catch (Exception ex)
            {
                this._log.ErrorFormat("Errore durante il caricamento del file di workflow: {0}", ex.ToString());

                throw;
            }
        }
    }
}
