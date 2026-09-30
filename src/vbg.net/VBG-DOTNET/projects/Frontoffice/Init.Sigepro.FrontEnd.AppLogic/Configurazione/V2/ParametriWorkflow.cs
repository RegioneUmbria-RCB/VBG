using Init.Sigepro.FrontEnd.AppLogic.PresentazioneIstanze.Workflow;
using System;

namespace Init.Sigepro.FrontEnd.AppLogic.Configurazione.V2
{
    public class ParametriWorkflow : IParametriConfigurazione
    {

        public readonly IWorkflowDomandaOnline DefaultWorkflow;
        public readonly bool VerificaHashFilesFirmati;
        public readonly int? IdCampoDinamicoPerAttivitaAtecoPrevalente;
        public readonly bool ImpostaAutomaticamenteAnagraficaUtenteCorrente;
        public readonly int? DefaultWorkflowCodiceOggetto;

        internal ParametriWorkflow(WorkflowDomandaOnline workflowDomanda, bool verificaHashFilesFirmati, int? idCampoDinamicoPerAttivitaAtecoPrevalente, bool impostaAutomaticamenteAnagraficaUtenteCorrente, int? defaultWorkflowXmlCodiceOggetto)
        {
            this.DefaultWorkflow = workflowDomanda ?? throw new ArgumentNullException(nameof(workflowDomanda));
            this.VerificaHashFilesFirmati = verificaHashFilesFirmati;
            this.IdCampoDinamicoPerAttivitaAtecoPrevalente = idCampoDinamicoPerAttivitaAtecoPrevalente;
            this.ImpostaAutomaticamenteAnagraficaUtenteCorrente = impostaAutomaticamenteAnagraficaUtenteCorrente;
            this.DefaultWorkflowCodiceOggetto = defaultWorkflowXmlCodiceOggetto;
        }
    }
}
